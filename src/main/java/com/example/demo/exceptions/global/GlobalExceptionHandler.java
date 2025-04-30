package com.example.demo.exceptions.global;

import com.example.demo.dto.response.ApiErrorResponse;
import com.example.demo.exceptions.AuthenticationFailedException;
import com.example.demo.exceptions.JwtException;
import com.example.demo.exceptions.duplicate.CategoryDuplicateException;
import com.example.demo.exceptions.InvalidProductDataException;
import com.example.demo.exceptions.SignUpException;
import com.example.demo.exceptions.duplicate.DuplicateException;
import com.example.demo.exceptions.duplicate.ProductDuplicateException;
import com.example.demo.exceptions.notfound.CategoryNotFoundException;
import com.example.demo.exceptions.notfound.NotFoundException;
import com.example.demo.exceptions.notfound.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
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

    @ExceptionHandler({CategoryDuplicateException.class, ProductDuplicateException.class})
    public ResponseEntity<ApiErrorResponse> handleDuplicateErrors(DuplicateException exception) {
        Map<String, List<String>> errors;
        errors = exception.getErrors();

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({CategoryNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException exception) {
        Map<String, List<String>> errors;
        errors = exception.getErrors();

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({InvalidProductDataException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequest(InvalidProductDataException exception) {
        Map<String, List<String>> errors;
        errors = exception.getErrors();

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("key", List.of(exception.getMessage()));

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiErrorResponse> handleJwtExceptionException(JwtException exception) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("key", List.of(exception.getMessage()));

        ApiErrorResponse response = new ApiErrorResponse(errors,
                "error",
                HttpStatus.UNAUTHORIZED.value()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
