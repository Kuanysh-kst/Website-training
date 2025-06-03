package com.example.courses.kargopolov.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestingUser {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;
}
