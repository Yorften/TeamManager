package com.teammanager.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.teammanager.dto.user.UserDTO;
import com.teammanager.dto.employee.CreateEmployeeDTO;
import com.teammanager.dto.employee.EmployeeDTO;
import com.teammanager.exception.InvalidDataException;
import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.model.User;
import com.teammanager.repository.UserRepository;
import com.teammanager.model.Employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeMapper {
    private final List<String> VALID_INCLUDES = Arrays.asList("user");

    private final UserRepository userRepository;

    public void verifyIncludes(String... with)
            throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
    }

    public Employee convertToEntity(CreateEmployeeDTO employeeDTO) {
        User user = null;

        if (employeeDTO.getUserId() != null) {
            user = userRepository.findById(employeeDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        }

        return Employee.builder()
                .fullName(employeeDTO.getFullName())
                .jobTitle(employeeDTO.getJobTitle())
                .department(employeeDTO.getDepartment())
                .hireDate(employeeDTO.getHireDate())
                .employmentStatus(employeeDTO.getEmploymentStatus())
                .contactInformation(employeeDTO.getContactInformation())
                .address(employeeDTO.getAddress())
                .user(user)
                .build();
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .jobTitle(employee.getJobTitle())
                .department(employee.getDepartment())
                .hireDate(employee.getHireDate())
                .employmentStatus(employee.getEmploymentStatus())
                .contactInformation(employee.getContactInformation())
                .address(employee.getAddress())
                .build();
    }

    public List<EmployeeDTO> convertToDTOList(List<Employee> employees) {
        return employees.stream()
                .map(employee -> convertToDTO(employee))
                .collect(Collectors.toList());
    }

    public EmployeeDTO convertToDTO(Employee employee, String... with) {
        List<String> includesList = Arrays.asList(with);

        UserDTO userDTO = null;

        if (includesList.contains("user")) {
            User user = employee.getUser();
            userDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
        }

        return EmployeeDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .jobTitle(employee.getJobTitle())
                .department(employee.getDepartment())
                .hireDate(employee.getHireDate())
                .employmentStatus(employee.getEmploymentStatus())
                .contactInformation(employee.getContactInformation())
                .address(employee.getAddress())
                .user(userDTO)
                .build();
    }

    public List<EmployeeDTO> convertToDTOList(List<Employee> employees, String... with) {
        verifyIncludes(with);
        return employees.stream()
                .map(employee -> convertToDTO(employee, with))
                .collect(Collectors.toList());
    }
}
