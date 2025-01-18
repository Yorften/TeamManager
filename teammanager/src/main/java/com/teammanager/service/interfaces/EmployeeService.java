package com.teammanager.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;

/**
 * Service interface for Employee entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface EmployeeService {

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO getEmployeeById(Long id, String... with);

    public EmployeeDTO getByEmployeeName(String userName);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable, String search);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable, String search, String... with);

    EmployeeDTO addEmployee(EmployeeDTO Employee);

    public EmployeeDTO updateEmployee(Long EmployeeId, UpdateEmployeeDTO Employee, String... with);

    public void deleteEmployeeById(Long EmployeeId);

}
