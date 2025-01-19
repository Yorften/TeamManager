package com.teammanagerui.controller;

import com.teammanagerui.model.LoginModel;
import com.teammanagerui.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthController {
    private final LoginModel model;
    private final AuthService authService;

    public AuthController(LoginModel model, AuthService authService) {
        this.model = model;
        this.authService = authService;
    }

    public LoginModel getModel() {
        return model;
    }

    public boolean login() {
        try {
            if (model.getUsername() == null || model.getUsername().isEmpty()) {
                throw new IllegalArgumentException("Username is required.");
            }
            if (model.getPassword() == null || model.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password is required.");
            }

            authService.login(model.getUsername(), model.getPassword());
            log.info("Login successful!");
            return true;
        } catch (Exception e) {
            log.error("Error authenticating", e);
            return false;
        }
    }

    public boolean logout() {
        try {
            authService.logout();
            log.info("Logout successful!");
            return true;
        } catch (Exception e) {
            log.error("Error authenticating", e);
            return false;
        }
    }
    
}
