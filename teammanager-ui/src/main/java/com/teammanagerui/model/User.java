package com.teammanagerui.model;

import lombok.Data;

@Data
public class User {
    String username;
    String email;
    Role role;
}
