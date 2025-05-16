package com.example.demo.exceptions;

import com.example.demo.util.ErrorUtils;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ProductCreationException extends RuntimeException {
    private final Map<String, List<String>> errors;
    public ProductCreationException(Map<String, List<String>> errors) {
        super(ErrorUtils.buildErrorMessage(errors));
        this.errors = errors;
    }
}
