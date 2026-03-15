package com.example.hrms_ashik.service;

import com.example.hrms_ashik.dto.EmployeeRequest;
import com.example.hrms_ashik.dto.EmployeeResponse;
import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.entity.User;
import com.example.hrms_ashik.mapper.EmployeeMapper;
import com.example.hrms_ashik.repository.EmployeeRepository;
import com.example.hrms_ashik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));
        Employee employee = EmployeeMapper.toEntity(request,user);
        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toResponse).toList();
    }

    public EmployeeResponse getMyProfile() {

        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        Employee employee = employeeRepository
                .findByUserUsername(username)
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        return EmployeeMapper.toResponse(employee);
    }

}
