package com.teammanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teammanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

}
