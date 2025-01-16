package com.teammanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teammanager.model.User;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private User user = new User(); 

    @GetMapping()
    public String getMethodName() {
        user.setUsername("test");
        return new String(user.getUsername());
    }

}
