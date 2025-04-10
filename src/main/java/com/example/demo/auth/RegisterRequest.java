package com.example.demo.auth;

import com.example.demo.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "The first name field is required.")
    private String firstname;

    @NotBlank(message = "The last name field is required.")
    private String lastname;

    @Email(message = "The email must be a valid email address.")
    @NotBlank(message = "The email field is required.")
    private String email;

    @NotBlank(message = "The password field is required.")
    @Size(min = 8, message = "The password must be at least 8 characters.")
    private String password;

    @NotNull(message = "The role field is required.")
    private Role role;
}
