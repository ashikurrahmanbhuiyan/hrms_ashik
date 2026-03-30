package com.example.hrms_ashik.modules.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {
    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String phone;

    private LocalDate dateOfJoining;

    @NotBlank
    private String designation;

    @NotNull
    private Double salary;

    private Long userId;
}
