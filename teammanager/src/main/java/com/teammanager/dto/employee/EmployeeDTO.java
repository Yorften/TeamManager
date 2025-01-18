package com.teammanager.dto.employee;

import com.teammanager.dto.user.UserDTO;
import com.teammanager.model.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private String fullName;

    private String jobTitle;

    private String department;

    private LocalDate hireDate;

    private EmploymentStatus employmentStatus;

    private String contactInformation;

    private String address;

    private UserDTO user;

}
