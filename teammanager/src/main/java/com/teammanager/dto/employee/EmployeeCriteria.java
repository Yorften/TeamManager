package com.teammanager.dto.employee;

import java.time.LocalDate;

import com.teammanager.model.enums.EmploymentStatus;

import lombok.Data;

@Data
public class EmployeeCriteria {
    private Long id;
    private String fullName;
    private String jobTitle;
    private String department;
    private EmploymentStatus employmentStatus;
    private LocalDate hireDateFrom;
    private LocalDate hireDateTo;
}
