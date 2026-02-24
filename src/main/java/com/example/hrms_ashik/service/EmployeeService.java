package com.example.hrms_ashik.service;

import com.example.hrms_ashik.dto.EmployeeRequest;
import com.example.hrms_ashik.dto.EmployeeResponse;
import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.mapper.EmployeeMapper;
import com.example.hrms_ashik.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = EmployeeMapper.toEntity(request);
        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> EmployeeMapper.toResponse(employee)).toList();
    }

    public EmployeeResponse getMyProfile() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Employee employee = employeeRepository
                .findByUserUsername(username)
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        return EmployeeMapper.toResponse(employee);
    }

}
