package com.example.demo.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public CustomValidationException(Map<String, List<String>> errors) {
        super(buildErrorMessage(errors));
        this.errors = errors;
    }

    private static String buildErrorMessage(Map<String, List<String>> errors) {
        StringBuilder messageBuilder = new StringBuilder("Validation failed:");

        errors.forEach((field, fieldErrors) ->
                fieldErrors.forEach(error ->
                        messageBuilder.append("\n").append(field).append(": ").append(error)
                )
        );

        return messageBuilder.toString();
    }
}
