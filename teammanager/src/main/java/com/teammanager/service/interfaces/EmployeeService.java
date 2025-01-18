package com.teammanager.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.teammanager.dto.employee.CreateEmployeeDTO;
import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;

/**
 * Service interface for Employee entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface EmployeeService {

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO getEmployeeById(Long id, String... with);

    EmployeeDTO getByEmployeeName(String userName);

    Page<EmployeeDTO> searchEmployees(Pageable pageable, EmployeeCriteria employeeCriteria);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable, String... with);

    EmployeeDTO addEmployee(CreateEmployeeDTO Employee);

    EmployeeDTO updateEmployee(Long EmployeeId, UpdateEmployeeDTO Employee, String... with);

    void deleteEmployeeById(Long EmployeeId);

}
