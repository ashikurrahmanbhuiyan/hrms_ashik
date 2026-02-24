package com.example.hrms_ashik.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String error;
    private String path;
}