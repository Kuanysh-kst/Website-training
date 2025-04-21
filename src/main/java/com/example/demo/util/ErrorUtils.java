package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorUtils {
    private ErrorUtils() {}

    public static String buildErrorMessage(Map<String, List<String>> errors) {
        StringBuilder messageBuilder = new StringBuilder("Validation failed:");

        errors.forEach((field, fieldErrors) ->
                fieldErrors.forEach(error ->
                        messageBuilder.append("\n").append(field).append(": ").append(error)
                )
        );

        return messageBuilder.toString();
    }

    public static void addError(Map<String, List<String>> errors, String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
    }
}
