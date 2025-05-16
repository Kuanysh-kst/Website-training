package com.example.demo.exceptions.global;

import com.example.demo.dto.response.ApiErrorResponse;
import com.example.demo.exceptions.unauthorized.AuthenticationFailedException;
import com.example.demo.exceptions.unauthorized.JwtException;
import com.example.demo.exceptions.duplicate.CategoryDuplicateException;
import com.example.demo.exceptions.InvalidProductDataException;
import com.example.demo.exceptions.SignUpException;
import com.example.demo.exceptions.duplicate.DuplicateException;
import com.example.demo.exceptions.duplicate.ProductDuplicateException;
import com.example.demo.exceptions.notfound.CategoryNotFoundException;
import com.example.demo.exceptions.notfound.NotFoundException;
import com.example.demo.exceptions.notfound.ProductNotFoundException;
import com.example.demo.exceptions.unauthorized.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<ApiErrorResponse> handleSignUpException(SignUpException exception) {
        return handleException(exception.getErrors(), HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({AuthenticationFailedException.class, JwtException.class})
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedExceptionErrors(UnauthorizedException exception) {
        return handleException(exception.getErrors(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({CategoryDuplicateException.class, ProductDuplicateException.class})
    public ResponseEntity<ApiErrorResponse> handleDuplicateErrors(DuplicateException exception) {
        return handleException(exception.getErrors(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CategoryNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException exception) {
        return handleException(exception.getErrors(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidProductDataException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequest(InvalidProductDataException exception) {
        return handleException(exception.getErrors(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("key", List.of(exception.getMessage()));

        return handleException(errors, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiErrorResponse> handleException(Map<String, List<String>> errors, int code, HttpStatusCode status) {
        ApiErrorResponse response = new ApiErrorResponse(errors, "error", code);
        return ResponseEntity.status(status).body(response);
    }
}
