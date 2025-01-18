package com.teammanager.service.interfaces;

import java.util.Set;

import com.teammanager.dto.role.RoleDTO;
import com.teammanager.model.Role;

/**
 * Service interface for Role entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface RoleService {

    Role getRoleById(long id);

    Set<Role> getAllRolesByName(Set<RoleDTO> roles);

    Role getRoleByName(String name);
    
}
