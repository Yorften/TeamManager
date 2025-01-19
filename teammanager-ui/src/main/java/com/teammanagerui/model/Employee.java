package com.teammanagerui.model;

import java.time.LocalDate;

import com.teammanagerui.model.enums.Department;
import com.teammanagerui.model.enums.EmploymentStatus;

import lombok.Data;

@Data
public class Employee {
    String fullName;
    String jobTitle;
    Department department;
    LocalDate hireDate;
    EmploymentStatus employmentStatus;
    String contactInformation;
    String address;
    Long userId;
}
