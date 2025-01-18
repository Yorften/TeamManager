package com.teammanager.controller.employee;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.model.enums.EmploymentStatus;
import com.teammanager.service.interfaces.EmployeeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing users actions history.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search")
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
        criteria.setEmploymentStatus(EmploymentStatus.valueOf(employmentStatus));
        criteria.setHireDateFrom(hireDateFrom);
        criteria.setHireDateTo(hireDateTo);

        return employeeService.searchEmployees(pageable, criteria);
    }

}
