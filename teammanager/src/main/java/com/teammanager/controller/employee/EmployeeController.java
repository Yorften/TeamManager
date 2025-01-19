package com.teammanager.controller.employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammanager.dto.employee.CreateEmployeeDTO;
import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.dto.employee.UpdateEmployeeDTO;
import com.teammanager.model.enums.EmploymentStatus;
import com.teammanager.service.interfaces.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Employee entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
@Tag(name = "Employees", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }



    @Operation(summary = "Create a new employee", description = "Add a new employee to the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public EmployeeDTO saveEmployee(
            @Parameter(description = "Employee creation details", required = true) @RequestBody @Valid CreateEmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }




    @Operation(summary = "Update an employee", description = "Update details of an existing employee.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public EmployeeDTO updateEmployeeDTO(
            @Parameter(description = "Employee update details", required = true) @RequestBody @Valid UpdateEmployeeDTO updateEmployeeDTO,
            @Parameter(description = "ID of the employee to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") Long employeeId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return employeeService.updateEmployee(employeeId, updateEmployeeDTO, authentication);
    }




    @Operation(summary = "Delete an employee", description = "Remove an employee from the system by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<Void> deleteEmployeeDTO(
            @Parameter(description = "ID of the employee to be deleted", required = true, in = ParameterIn.PATH)
            @PathVariable("id") long employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity.noContent().build();
    }


    
    @Operation(summary = "Search employees", description = "Search employees by various criteria.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public List<EmployeeDTO> searchEmployees(
            @Parameter(description = "Employee ID", in = ParameterIn.QUERY) @RequestParam(required = false) Long id,
            @Parameter(description = "Employee full name", in = ParameterIn.QUERY) @RequestParam(required = false) String fullName,
            @Parameter(description = "Job title", in = ParameterIn.QUERY) @RequestParam(required = false) String jobTitle,
            @Parameter(description = "Department", in = ParameterIn.QUERY) @RequestParam(required = false) String department,
            @Parameter(description = "Employment status (e.g., FULL_TIME, PART_TIME)", in = ParameterIn.QUERY)
            @RequestParam(required = false) String employmentStatus,
            @Parameter(description = "Hire date from (YYYY-MM-DD)", in = ParameterIn.QUERY) @RequestParam(required = false) LocalDate hireDateFrom,
            @Parameter(description = "Hire date to (YYYY-MM-DD)", in = ParameterIn.QUERY) @RequestParam(required = false) LocalDate hireDateTo) {

        EmployeeCriteria criteria = new EmployeeCriteria();
        criteria.setId(id);
        criteria.setFullName(fullName);
        criteria.setJobTitle(jobTitle);
        criteria.setDepartment(department);
        criteria.setEmploymentStatus(employmentStatus != null ? EmploymentStatus.valueOf(employmentStatus) : null);
        criteria.setHireDateFrom(hireDateFrom);
        criteria.setHireDateTo(hireDateTo);

        return employeeService.searchEmployees(criteria);
    }

}
