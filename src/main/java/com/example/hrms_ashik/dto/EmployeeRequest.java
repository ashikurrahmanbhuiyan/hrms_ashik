package com.example.hrms_ashik.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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

    private Set<String> roles;
}
