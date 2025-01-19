package com.teammanager.service.implementation;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.teammanager.dto.employee.CreateEmployeeDTO;
import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;
import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.mapper.EmployeeMapper;
import com.teammanager.model.Employee;
import com.teammanager.model.User;
import com.teammanager.repository.EmployeeRepository;
import com.teammanager.repository.UserRepository;
import com.teammanager.service.helper.AuditEventPublisher;
import com.teammanager.service.interfaces.EmployeeService;
import com.teammanager.specification.EmployeeSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for User entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final AuditEventPublisher auditEventPublisher;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.convertToDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id, String... with) {
        employeeMapper.verifyIncludes(with);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.convertToDTO(employee);
    }

    @Override
    public EmployeeDTO getByEmployeeName(String employeeName) {
        Employee employee = employeeRepository.findByFullName(employeeName)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAllEmployees();

        return employeeMapper.convertToDTOList(employees);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees(String... with) {
        List<Employee> employees = employeeRepository.findAllEmployees();

        return employeeMapper.convertToDTOList(employees, with);
    }

    @Override
    public List<EmployeeDTO> searchEmployees(EmployeeCriteria employeeCriteria) {
        Specification<Employee> spec = EmployeeSpecification.withFilters(employeeCriteria);
        List<Employee> employees = employeeRepository.findAll(spec);

        return employeeMapper.convertToDTOList(employees);
    }

    @Override
    public EmployeeDTO addEmployee(CreateEmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.convertToEntity(employeeDTO);
        employee = employeeRepository.save(employee);

        auditEventPublisher.publishEmployeeCreateEvent(employee);

        return employeeMapper.convertToDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeId, UpdateEmployeeDTO employeeDTO, Authentication authentication) {

        Employee employeeDB = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Employee managerEmployee = null;

        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_MANAGER"))) {

            User authUser = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));

            log.info("auth id : " + authUser.getId());
            managerEmployee = employeeRepository.findByUserId(authUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager's employee details not found"));

            if (!managerEmployee.getDepartment().equals(employeeDB.getDepartment())) {
                throw new AccessDeniedException("Managers can only update employees within their department.");
            }

            if (employeeDTO.getContactInformation() != null && !employeeDTO.getContactInformation().isEmpty()) {
                employeeDB.setContactInformation(employeeDTO.getContactInformation());
            }
            if (employeeDTO.getAddress() != null && !employeeDTO.getAddress().isEmpty()) {
                employeeDB.setAddress(employeeDTO.getAddress());
            }
            if (employeeDTO.getHireDate() != null) {
                employeeDB.setHireDate(employeeDTO.getHireDate());
            }
            if (employeeDTO.getEmploymentStatus() != null) {
                employeeDB.setEmploymentStatus(employeeDTO.getEmploymentStatus());
            }
        } else {

            if (employeeDTO.getFullName() != null && !employeeDTO.getFullName().isEmpty()) {
                employeeDB.setFullName(employeeDTO.getFullName());
            }
            if (employeeDTO.getJobTitle() != null && !employeeDTO.getJobTitle().isEmpty()) {
                employeeDB.setJobTitle(employeeDTO.getJobTitle());
            }
            if (employeeDTO.getDepartment() != null && !employeeDTO.getDepartment().isEmpty()) {
                employeeDB.setDepartment(employeeDTO.getDepartment());
            }
            if (employeeDTO.getContactInformation() != null && !employeeDTO.getContactInformation().isEmpty()) {
                employeeDB.setContactInformation(employeeDTO.getContactInformation());
            }
            if (employeeDTO.getAddress() != null && !employeeDTO.getAddress().isEmpty()) {
                employeeDB.setAddress(employeeDTO.getAddress());
            }
            if (employeeDTO.getHireDate() != null) {
                employeeDB.setHireDate(employeeDTO.getHireDate());
            }
            if (employeeDTO.getEmploymentStatus() != null) {
                employeeDB.setEmploymentStatus(employeeDTO.getEmploymentStatus());
            }

        }

        employeeDB = employeeRepository.save(employeeDB);

        auditEventPublisher.publishEmployeeUpdateEvent(employeeDB);

        return employeeMapper.convertToDTO(employeeDB);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

}
