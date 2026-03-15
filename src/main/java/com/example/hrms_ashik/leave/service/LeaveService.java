package com.example.hrms_ashik.leave.service;

import com.example.hrms_ashik.entity.*;
import com.example.hrms_ashik.leave.Repository.EmployeeLeaveBalanceRepository;
import com.example.hrms_ashik.leave.Repository.LeaveRepository;
import com.example.hrms_ashik.leave.Repository.LeaveTypeRepository;
import com.example.hrms_ashik.leave.dto.LeaveRequest;
import com.example.hrms_ashik.leave.dto.LeaveResponse;
import com.example.hrms_ashik.leave.entity.EmployeeLeaveBalance;
import com.example.hrms_ashik.leave.entity.Leave;
import com.example.hrms_ashik.leave.entity.LeaveStatus;
import com.example.hrms_ashik.leave.entity.LeaveType;
import com.example.hrms_ashik.leave.mapper.LeaveMapper;
import com.example.hrms_ashik.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;

    private int calculateDays(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("end date can't be before start date");
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public Leave applyLeave(LeaveRequest leaveRequest) {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        Employee employee = employeeRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveType leaveType = leaveTypeRepository.findByName(leaveRequest.getLeaveType())
                .orElseThrow(() -> new RuntimeException("LeaveType not found"));

        int offDays = calculateDays(leaveRequest.getStartDate(), leaveRequest.getEndDate());

        EmployeeLeaveBalance employeeLeaveBalance = employeeLeaveBalanceRepository
                .findByEmployeeAndLeaveType(employee, leaveType)
                .orElseThrow(() -> new RuntimeException("EmployeeLeaveBalance not initialize"));

        if (employeeLeaveBalance.getRemainingDays() < offDays) {
            throw new RuntimeException("Not enough days remaining");
        }

        boolean overlap = leaveRepository.existsOverlappingLeave(
                employee.getId(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate());

        if (overlap) {
            throw new RuntimeException("Leave overlap");
        }

        Leave leave = LeaveMapper.toEntity(leaveRequest, employee, leaveType, offDays);
        return leaveRepository.save(leave);
    }

    public Leave approveLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already approved");
        }
        EmployeeLeaveBalance employeeLeaveBalance = employeeLeaveBalanceRepository.findByEmployeeAndLeaveType(
                leave.getEmployee(),
                leave.getLeaveType()).orElseThrow(() -> new RuntimeException("EmployeeLeaveBalance not initialize"));

        employeeLeaveBalance.setRemainingDays(employeeLeaveBalance.getRemainingDays() - leave.getTotalDays());
        leave.setStatus(LeaveStatus.APPROVED);
        leave.setApprovedBy(
                Objects.requireNonNull(
                        SecurityContextHolder.getContext().getAuthentication()).getName());

        employeeLeaveBalanceRepository.save(employeeLeaveBalance);
        return leaveRepository.save(leave);
    }

    public List<LeaveResponse> getMyLeave() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        return leaveRepository.findByEmployeeUserUsername(username)
                .stream().map(LeaveMapper::toResponse).toList();
    }

    public List<LeaveResponse> getAllLeave() {
        return leaveRepository.findAll()
                .stream().map(LeaveMapper::toResponse).toList();
    }
}
