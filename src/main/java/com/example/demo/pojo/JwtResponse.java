package com.example.demo.pojo;

import com.example.demo.models.Role;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;

    public JwtResponse(String token, Long id, String name, String email, List<Role> roles) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
