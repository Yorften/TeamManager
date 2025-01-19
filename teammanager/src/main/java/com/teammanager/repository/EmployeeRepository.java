package com.teammanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.teammanager.model.Employee;

/**
 * Repository interface for Employee entity.
 * Provides CRUD operations and custom query methods through JpaRepository.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByFullName(String fullName);

    Optional<Employee> findByUserId(Long userId);

    @Query("SELECT e FROM employees e WHERE e.user IS NULL AND e.removedAt IS NULL")
    List<Employee> findAllEmployees();

}
