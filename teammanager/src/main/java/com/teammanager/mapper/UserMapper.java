package com.teammanager.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.teammanager.dto.role.RoleDTO;
import com.teammanager.dto.user.UserDTO;
import com.teammanager.exception.InvalidDataException;
import com.teammanager.model.Role;
import com.teammanager.model.User;
import com.teammanager.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {
    private final List<String> VALID_INCLUDES = Arrays.asList("roles", "employee");

    private final RoleService roleService;

    public void verifyIncludes(String... with)
            throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
    }

    public User convertToEntity(UserDTO userDTO) {
        Set<Role> roles = roleService.getAllRolesByName(userDTO.getRoles());

        return User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(roles)
                .build();
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public List<UserDTO> convertToDTOList(List<User> users) {
        return users.stream()
                .map(user -> convertToDTO(user))
                .collect(Collectors.toList());
    }

    public UserDTO convertToDTO(User user, String... with) {
        List<String> includesList = Arrays.asList(with);

        Set<RoleDTO> roleDTOs = null;

        if (includesList.contains("roles")) {
            Set<Role> roles = user.getRoles();
            roleDTOs = roles.stream().map(role -> RoleDTO.builder().name(role.getName()).build())
                    .collect(Collectors.toSet());
        }

        if (includesList.contains("employee")) {

        }

        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(roleDTOs)
                .build();
    }

    public List<UserDTO> convertToDTOList(List<User> users, String... with) {
        verifyIncludes(with);
        return users.stream()
                .map(user -> convertToDTO(user, with))
                .collect(Collectors.toList());
    }
}
