package com.example.hrms_ashik.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(@NonNull MethodArgumentNotValidException e, @NonNull HttpServletRequest request) {

        String errorMessage = e.getBindingResult()
                .getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiError apiError = ApiError.builder()
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .error("validation fail")
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<ApiError> genericException(@NonNull Exception e, @NonNull HttpServletRequest request) {
        ApiError error = ApiError.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .error("validation fail")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
