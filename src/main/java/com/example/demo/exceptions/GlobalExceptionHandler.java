package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationAndBusinessErrors(Exception ex) {
        Map<String, List<String>> errors = new HashMap<>();

        if (ex instanceof ValidationException) {
            errors = ((ValidationException) ex).getErrors();
        }
        // Создаем единый формат ответа для всех ошибок
        ApiErrorResponse response = new ApiErrorResponse(
                errors,
                "error",
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );

        return ResponseEntity.unprocessableEntity().body(response);
    }
}
