package com.teammanager.dto.employee;

import java.time.LocalDate;

import com.teammanager.model.enums.EmploymentStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateEmployeeDTO {

    @NotNull(message = "Full name is required")
    @Size(min = 5, max = 50, message = "Employee name must be between 5 and 50 characters")
    private String fullName;

    @NotNull(message = "Job title is required")
    @Size(min = 5, max = 100, message = "Job title must be between 5 and 100 characters")
    private String jobTitle;

    @NotNull(message = "Department is required")
    @Size(min = 5, max = 100, message = "Employee name must be between 5 and 100 characters")
    private String department;

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    @NotNull(message = "Employment status is required")
    private EmploymentStatus employmentStatus;

    @NotNull(message = "Contact information is required")
    @Pattern(regexp = "^[+]?[0-9]*$", message = "Contact information must be a valid phone number")
    private String contactInformation;

    @NotNull(message = "Address is required")
    @Size(min = 5, max = 100, message = "Employee address must be between 5 and 100 characters")
    private String address;

    private Long userId;

}
