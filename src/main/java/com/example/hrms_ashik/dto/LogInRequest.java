package com.example.hrms_ashik.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
