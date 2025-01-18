package com.teammanager.service.implementation;

import org.springframework.stereotype.Service;

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
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

}
