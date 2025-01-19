package com.teammanagerui.model;

import lombok.Data;

@Data
public class RegisterModel {
    String username;
    String password;
    String repeatPassword;
    String email;
    Role role;
}
