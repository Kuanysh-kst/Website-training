package com.example.demo.exceptions.unauthorized;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final Map<String, List<String>> errors;

    public UnauthorizedException(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
