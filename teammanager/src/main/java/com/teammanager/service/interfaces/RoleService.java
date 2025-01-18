package com.teammanager.service.interfaces;

import com.teammanager.model.Role;

/**
 * Service interface for Role entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface RoleService {
    Role getRoleById(long id);

    Role getRoleByName(String name);
}
