package com.teammanager.controller.user;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teammanager.dto.user.UpdateUserDTO;
import com.teammanager.dto.user.UserDTO;
import com.teammanager.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing User entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers("role");
    }

    @GetMapping("/@me")
    public UserDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByUserName(username, "role");
    }

    @PutMapping("/{id}/role")
    public UserDTO updateUserRole(@RequestBody @Valid UpdateUserDTO updateUserDTO, @PathVariable("id") Long userId) {
        return userService.updateUser(userId, updateUserDTO, "role");
    }

}
