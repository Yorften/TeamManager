package com.teammanager.dto.employee;

import java.time.LocalDate;

import com.teammanager.model.enums.EmploymentStatus;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {

    @Size(min = 5, max = 50, message = "Employee name must be between 5 and 50 characters")
    private String fullName;

    @Size(min = 5, max = 100, message = "Job title must be between 5 and 100 characters")
    private String jobTitle;

    @Size(min = 5, max = 100, message = "Employee name must be between 5 and 100 characters")
    private String department;

    private LocalDate hireDate;

    private EmploymentStatus employmentStatus;

    @Pattern(regexp = "^[+]?[0-9]*$", message = "Contact information must be a valid phone number")
    private String contactInformation;

    @Size(min = 5, max = 100, message = "Employee address must be between 5 and 100 characters")
    private String address;


}
