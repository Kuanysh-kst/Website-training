package com.example.demo.exceptions.unauthorized;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class AuthenticationFailedException extends UnauthorizedException {
    public AuthenticationFailedException(Map<String, List<String>> errors) {
        super(errors);
    }
}
