package com.example.demo.validation;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.repositories.MyUserRepository;
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

    public void signUpValidate(RegisterRequest request) {
        Map<String, List<String>> errors = new HashMap<>();

        if (isBlank(request.getFirstname())) {
            addError(errors, "firstname", "The first name field is required.");
        }

        if (isBlank(request.getLastname())) {
            addError(errors, "lastname", "The last name field is required.");
        }

        if (isBlank(request.getEmail())) {
            addError(errors, "email", "The email field is required.");
        } else {
            if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                addError(errors, "email", "The email must be a valid email address.");
            }

            if (repository.findByEmail(request.getEmail()).isPresent()) {
                addError(errors, "email", "Email is already in use.");
            }
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            addError(errors, "password", "The password must be at least 8 characters.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private void addError(Map<String, List<String>> errors, String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
    }

}
