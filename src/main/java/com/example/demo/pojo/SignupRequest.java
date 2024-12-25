package com.example.demo.pojo;

import com.example.demo.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private Set<Role> roles;
    private String password;
}
