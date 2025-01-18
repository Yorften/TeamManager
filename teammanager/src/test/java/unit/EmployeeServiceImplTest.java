package unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import com.teammanager.dto.employee.CreateEmployeeDTO;
import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;
import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.mapper.EmployeeMapper;
import com.teammanager.model.Employee;
import com.teammanager.repository.EmployeeRepository;
import com.teammanager.repository.UserRepository;
import com.teammanager.service.helper.AuditEventPublisher;
import com.teammanager.service.implementation.EmployeeServiceImpl;
import com.teammanager.specification.EmployeeSpecification;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditEventPublisher auditEventPublisher;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private EmployeeDTO testEmployeeDTO;
    private CreateEmployeeDTO testCreateEmployeeDTO;
    private UpdateEmployeeDTO testUpdateEmployeeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setFullName("John Doe");

        testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setId(1L);
        testEmployeeDTO.setFullName("John Doe");

        testCreateEmployeeDTO = new CreateEmployeeDTO();
        testCreateEmployeeDTO.setFullName("John Doe");

        testUpdateEmployeeDTO = new UpdateEmployeeDTO();
        testUpdateEmployeeDTO.setFullName("John Updated");

        // Mocking behavior of EmployeeMapper
        when(employeeMapper.convertToDTO(testEmployee)).thenReturn(testEmployeeDTO);
        when(employeeMapper.convertToEntity(testCreateEmployeeDTO)).thenReturn(testEmployee);
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeDTO_WhenEmployeeExists() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(testEmployee));

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getEmployeeById_ShouldThrowResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void addEmployee_ShouldCreateEmployeeAndReturnEmployeeDTO() {
        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        EmployeeDTO result = employeeService.addEmployee(testCreateEmployeeDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        verify(employeeRepository).save(testEmployee);
        verify(auditEventPublisher).publishEmployeeCreateEvent(testEmployee);
    }

    @Test
    void updateEmployee_ShouldUpdateEmployeeAndReturnUpdatedEmployeeDTO() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFullName("John Updated");

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setId(1L);
        updatedEmployeeDTO.setFullName("John Updated");
    
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(testEmployee));
        when(employeeRepository.save(testEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.convertToDTO(updatedEmployee)).thenReturn(updatedEmployeeDTO);

        // Create mock Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("manager");
    
        EmployeeDTO result = employeeService.updateEmployee(1L, testUpdateEmployeeDTO, authentication);
    
        assertNotNull(result);
        assertEquals("John Updated", result.getFullName());
        verify(employeeRepository).save(testEmployee);
        verify(auditEventPublisher).publishEmployeeUpdateEvent(testEmployee);
    }

    @Test
    void deleteEmployeeById_ShouldDeleteEmployee_WhenEmployeeExists() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(testEmployee));

        employeeService.deleteEmployeeById(1L);

        verify(employeeRepository).delete(testEmployee);
    }

    @Test
    void deleteEmployeeById_ShouldThrowResourceNotFoundException_WhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployeeById(1L));
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeDTOPage() {
        Page<Employee> employeePage = new PageImpl<>(List.of(testEmployee));
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getFullName());
    }

    @Test
    void searchEmployees_ShouldReturnEmployeeDTOPage_WhenCriteriaMatched() {
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();

        @SuppressWarnings("unchecked")
        Specification<Employee> spec = mock(Specification.class);
        mockStatic(EmployeeSpecification.class);
        when(EmployeeSpecification.withFilters(any(EmployeeCriteria.class))).thenReturn(spec);

        Page<Employee> employeePage = new PageImpl<>(List.of(testEmployee));
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.findAll(spec, pageable)).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.searchEmployees(pageable, employeeCriteria);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getFullName());
    }

}
