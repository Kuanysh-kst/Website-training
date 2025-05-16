package com.example.demo.exceptions.duplicate;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DuplicateException extends RuntimeException{
    private final Map<String, List<String>> errors;

    public DuplicateException(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
