package com.teammanagerui.controller;

import com.teammanagerui.model.LoginModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginController {
    private final LoginModel model;

    public LoginController() {
        this.model = new LoginModel();
    }

    public boolean authenticate(String username, String password) {
        log.info("Attempting to authenticate user: {}", username);
        model.setUsername(username);
        model.setPassword(password);
        return true; // Temporary return for testing
    }
}
