package com.example.hrms_ashik.mapper;


import com.example.hrms_ashik.dto.EmployeeRequest;
import com.example.hrms_ashik.dto.EmployeeResponse;
import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.entity.User;
import org.jspecify.annotations.NonNull;

public class EmployeeMapper {
    public static Employee toEntity(@NonNull EmployeeRequest request, User user) {
        Employee employee = new Employee();

        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfJoining(request.getDateOfJoining())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .user(user)
                .designation(request.getDesignation())
                .build();
    }

    public static EmployeeResponse toResponse(@NonNull Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName(employee.getFirstName() + employee.getLastName())
                .salary(employee.getSalary())
                .designation(employee.getDesignation())
                .build();
    }
}
