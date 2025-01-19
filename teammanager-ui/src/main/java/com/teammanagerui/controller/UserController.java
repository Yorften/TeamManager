package com.teammanagerui.controller;

import com.teammanagerui.model.User;
import com.teammanagerui.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User getUserInfo() {
        try {
            log.info("Logout successful!");
            return userService.fetchUserInfo();
        } catch (Exception e) {
            log.error("Error authenticating", e);
            return null;
        }
    }
}
