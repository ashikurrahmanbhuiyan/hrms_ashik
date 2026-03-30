package com.example.hrms_ashik.modules.employee.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {
    private Long id;
    private String fullName;
    private String email;
    private String designation;
    private Double salary;
}
