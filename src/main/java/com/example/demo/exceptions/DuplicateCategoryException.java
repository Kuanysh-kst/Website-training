package com.example.demo.exceptions;

import com.example.demo.util.ErrorUtils;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DuplicateCategoryException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public DuplicateCategoryException(Map<String, List<String>> errors) {
        super(ErrorUtils.buildErrorMessage(errors));
        this.errors = errors;
    }
}
