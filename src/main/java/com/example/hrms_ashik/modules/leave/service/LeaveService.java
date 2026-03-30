package com.example.hrms_ashik.modules.leave.service;

import com.example.hrms_ashik.modules.employee.entity.Employee;
import com.example.hrms_ashik.modules.employee.service.EmployeeService;
import com.example.hrms_ashik.modules.leave.repository.EmployeeLeaveBalanceRepository;
import com.example.hrms_ashik.modules.leave.repository.LeaveRepository;
import com.example.hrms_ashik.modules.leave.repository.LeaveTypeRepository;
import com.example.hrms_ashik.modules.leave.dto.LeaveRequest;
import com.example.hrms_ashik.modules.leave.dto.LeaveResponse;
import com.example.hrms_ashik.modules.leave.entity.EmployeeLeaveBalance;
import com.example.hrms_ashik.modules.leave.entity.Leave;
import com.example.hrms_ashik.modules.leave.entity.LeaveStatus;
import com.example.hrms_ashik.modules.leave.entity.LeaveType;
import com.example.hrms_ashik.modules.leave.mapper.LeaveMapper;

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
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    private final EmployeeService employeeService;

    private int calculateDays(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("end date can't be before start date");
        }
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public Leave applyLeave(LeaveRequest leaveRequest) {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        Employee employee = employeeService.employee(username);

        LeaveType leaveType = leaveTypeRepository.findByName(leaveRequest.getLeaveType())
                .orElseThrow(() -> new RuntimeException("LeaveType not found"));

        int offDays = calculateDays(leaveRequest.getStartDate(), leaveRequest.getEndDate());

        EmployeeLeaveBalance employeeLeaveBalance = employeeLeaveBalanceRepository
                .findByEmployeeIdAndLeaveType(employee.getId(), leaveType)
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
        EmployeeLeaveBalance employeeLeaveBalance = employeeLeaveBalanceRepository.findByEmployeeIdAndLeaveType(
                leave.getEmployee().getId(),
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
