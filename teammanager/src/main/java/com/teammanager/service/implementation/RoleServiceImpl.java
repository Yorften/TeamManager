package com.teammanager.service.implementation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.teammanager.dto.role.RoleDTO;
import com.teammanager.model.Role;
import com.teammanager.repository.RoleRepository;
import com.teammanager.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Service implementation for Role entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    @Override
    public Set<Role> getAllRolesByName(Set<RoleDTO> roles) {
        roles.forEach(role -> log.info("Role : " + role.toString()));
        Set<Role> roleEntities = new HashSet<>();
        try {
            Iterable<String> roleNames = roles
                    .stream()
                    .map(RoleDTO::getName)
                    .collect(Collectors.toList());

            roleRepository.findAllByNameIn(roleNames).forEach(roleEntities::add);
            roleEntities.forEach(role -> log.info("Role : " + role.toString()));
        } catch (Exception e) {
            log.error("Error fetching roles", e);
        }

        return roleEntities;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

}
