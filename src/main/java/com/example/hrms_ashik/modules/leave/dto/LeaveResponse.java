package com.example.hrms_ashik.modules.leave.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LeaveResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String reason;
}
