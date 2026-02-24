package com.example.hrms_ashik.controller;

import com.example.hrms_ashik.dto.EmployeeRequest;
import com.example.hrms_ashik.dto.EmployeeResponse;
import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/myprofile")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_VIEW')")
    public EmployeeResponse getMyProfile(){
        return employeeService.getMyProfile();
    }
}
