package com.teammanager.controller.employee;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public Page<EmployeeDTO> getAllEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.by("id").ascending());
        return employeeService.getAllEmployees(pageable);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public EmployeeDTO saveEmployee(
            @RequestBody @Valid CreateEmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public EmployeeDTO updateEmployeeDTO(
            @RequestBody @Valid UpdateEmployeeDTO updateEmployeeDTO,
            @PathVariable("id") Long employeeId) {
        return employeeService.updateEmployee(employeeId, updateEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<Void> deleteEmployeeDTO(@PathVariable("id") long employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public Page<EmployeeDTO> searchEmployees(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String employmentStatus,
            @RequestParam(required = false) LocalDate hireDateFrom,
            @RequestParam(required = false) LocalDate hireDateTo,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of((page - 1), size, Sort.by("id").ascending());

        EmployeeCriteria criteria = new EmployeeCriteria();
        criteria.setId(id);
        criteria.setFullName(fullName);
        criteria.setJobTitle(jobTitle);
        criteria.setDepartment(department);
        criteria.setEmploymentStatus(employmentStatus != null ? EmploymentStatus.valueOf(employmentStatus) : null);
        criteria.setHireDateFrom(hireDateFrom);
        criteria.setHireDateTo(hireDateTo);

        return employeeService.searchEmployees(pageable, criteria);
    }

}
