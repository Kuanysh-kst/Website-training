package com.example.demo.exceptions.notfound;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public abstract class NotFoundException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public NotFoundException(Map<String, List<String>> errors) {
        this.errors = errors;
    }

}
