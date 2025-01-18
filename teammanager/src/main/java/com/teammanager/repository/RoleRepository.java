package com.teammanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teammanager.model.Role;

/**
 * Repository interface for Role entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);
    
}
