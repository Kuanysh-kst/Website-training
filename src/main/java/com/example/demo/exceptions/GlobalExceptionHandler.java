package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationAndBusinessErrors(Exception ex) {
        Map<String, List<String>> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException validationEx) {
            // Обработка ошибок валидации
            validationEx.getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String message = error.getDefaultMessage();

                // Для случаев, когда одно поле имеет несколько ошибок
                errors.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(message);
            });
        } else if (ex instanceof EmailAlreadyExistsException businessEx) {
            // Обработка бизнес-ошибки - добавляем в ту же структуру
            errors.computeIfAbsent("email", key -> new ArrayList<>()).add(businessEx.getMessage());
        }

        // Создаем единый формат ответа для всех ошибок
        ApiErrorResponse response = new ApiErrorResponse(
                errors,
                "validation_error",
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );

        return ResponseEntity.unprocessableEntity().body(response);
    }
}
