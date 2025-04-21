package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SignUpException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationAndBusinessErrors(SignUpException ex) {
        Map<String, List<String>> errors;
        errors = ex.getErrors();

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );

        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler({AuthenticationFailedException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationFailedErrors(AuthenticationFailedException exception) {
        Map<String, List<String>> errors;
        errors = exception.getErrors();

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.UNAUTHORIZED.value()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
