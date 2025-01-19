package com.teammanager.service.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

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

    List<EmployeeDTO> searchEmployees(EmployeeCriteria employeeCriteria);

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> getAllEmployees(String... with);

    EmployeeDTO addEmployee(CreateEmployeeDTO Employee);

    EmployeeDTO updateEmployee(Long EmployeeId, UpdateEmployeeDTO Employee, Authentication authentication);

    void deleteEmployeeById(Long EmployeeId);

}
