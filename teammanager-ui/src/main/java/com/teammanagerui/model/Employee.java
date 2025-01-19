package com.teammanagerui.model;

import java.time.LocalDate;

import com.teammanagerui.model.enums.EmploymentStatus;

import lombok.Data;

@Data
public class Employee {
    Long id;
    String fullName;
    String jobTitle;
    String department;
    LocalDate hireDate;
    EmploymentStatus employmentStatus;
    String contactInformation;
    String address;
    Long userId;
}
