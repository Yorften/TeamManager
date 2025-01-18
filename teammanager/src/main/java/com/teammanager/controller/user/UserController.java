package com.teammanager.controller.user;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<UserDTO> getAllUsers(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.by("id").ascending());
        return userService.getAllUsers(pageable, "role");
    }

    @PutMapping("/{id}/role")
    public UserDTO updateUserRole(@RequestBody @Valid UpdateUserDTO updateUserDTO, @PathVariable("id") Long userId) {
        return userService.updateUser(userId, updateUserDTO, "role");
    }

}
