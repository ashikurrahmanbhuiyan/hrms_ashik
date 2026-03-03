package com.example.hrms_ashik.service;

import com.example.hrms_ashik.dto.LeaveRequest;
import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.entity.Leave;
import com.example.hrms_ashik.entity.LeaveStatus;
import com.example.hrms_ashik.repository.EmployeeRepository;
import com.example.hrms_ashik.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;


    public Leave applyLeave(LeaveRequest leaveRequest){
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        Employee employee = employeeRepository.findByUserUsername(username)
                .orElseThrow(()->new RuntimeException("Employee not found"));

        Leave leave = Leave.builder()
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .status(LeaveStatus.PENDING)
                .type(leaveRequest.getType())
                .reason(leaveRequest.getReason())
                .employee(employee)
                .build();

        return leaveRepository.save(leave);
    }


    public List<Leave> getMyLeave(){
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        return leaveRepository.findByEmployeeUserUsername(username);
    }

    public List<Leave> getAllLeave(){
        return leaveRepository.findAll();
    }
}
