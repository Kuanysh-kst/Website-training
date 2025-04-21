package com.example.demo.validation;

import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.exceptions.SignUpException;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final MyUserRepository repository;

    public void signUpValidate(SignUpRequest request) {
        Map<String, List<String>> errors = new HashMap<>();

        if (isBlank(request.getFirstname())) {
            ErrorUtils.addError(errors, "firstname", "The first name field is required.");
        }

        if (isBlank(request.getLastname())) {
            ErrorUtils.addError(errors, "lastname", "The last name field is required.");
        }

        if (isBlank(request.getEmail())) {
            ErrorUtils.addError(errors, "email", "The email field is required.");
        } else {
            if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                ErrorUtils.addError(errors, "email", "The email must be a valid email address.");
            }

            if (repository.findByEmail(request.getEmail()).isPresent()) {
                ErrorUtils.addError(errors, "email", "Email is already in use.");
            }
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            ErrorUtils.addError(errors, "password", "The password must be at least 8 characters.");
        }

        if (!errors.isEmpty()) {
            throw new SignUpException(errors);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
