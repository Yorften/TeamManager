package com.teammanager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;
import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.mapper.EmployeeMapper;
import com.teammanager.model.Employee;
import com.teammanager.repository.EmployeeRepository;
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
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(employee -> employeeMapper.convertToDTO(employee));
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable, String... with) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(employee -> employeeMapper.convertToDTO(employee, with));
    }

    @Override
    public Page<EmployeeDTO> searchEmployees(Pageable pageable, EmployeeCriteria employeeCriteria) {
        Specification<Employee> spec = EmployeeSpecification.withFilters(employeeCriteria);
        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);

        return employeePage.map(employee -> employeeMapper.convertToDTO(employee));
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.convertToEntity(employeeDTO);
        return employeeMapper.convertToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeId, UpdateEmployeeDTO employeeDTO, String... with) {
        Employee employeeDB = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

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

        return employeeMapper.convertToDTO(employeeRepository.save(employeeDB));
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

}
