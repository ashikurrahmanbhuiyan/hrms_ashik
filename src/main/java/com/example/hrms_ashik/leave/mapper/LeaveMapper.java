package com.example.hrms_ashik.leave.mapper;

import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.leave.dto.LeaveRequest;
import com.example.hrms_ashik.leave.dto.LeaveResponse;
import com.example.hrms_ashik.leave.entity.Leave;
import com.example.hrms_ashik.leave.entity.LeaveStatus;
import com.example.hrms_ashik.leave.entity.LeaveType;
import lombok.NonNull;

public class LeaveMapper {
    public static Leave toEntity(@NonNull LeaveRequest leaveRequest, Employee employee, LeaveType leaveType,
            Integer offDays) {
        return Leave.builder()
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .totalDays(offDays)
                .status(LeaveStatus.PENDING)
                .leaveType(leaveType)
                .reason(leaveRequest.getReason())
                .employee(employee)
                .build();
    }

    public static LeaveResponse toResponse(@NonNull Leave leave) {
        return LeaveResponse.builder()
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                // .type(leave.getLeaveType().toString())
                .build();
    }
}
