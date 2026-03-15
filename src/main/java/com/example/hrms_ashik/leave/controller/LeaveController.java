package com.example.hrms_ashik.leave.controller;

import com.example.hrms_ashik.leave.dto.LeaveRequest;
import com.example.hrms_ashik.leave.dto.LeaveResponse;
import com.example.hrms_ashik.leave.mapper.LeaveMapper;
import com.example.hrms_ashik.leave.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leaves")
public class LeaveController {
    private final LeaveService leaveService;
    private static final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @GetMapping("/allLeaves")
    @PreAuthorize("hasAuthority('HR_VIEW')")
    public List<LeaveResponse> getAllLeave() {
        return leaveService.getAllLeave();
    }

    @GetMapping("myLeaves")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    public List<LeaveResponse> getMyLeave() {
        logger.error("this is my error so nice");
        logger.warn("this is my warn so nice");
        logger.info(leaveService.getAllLeave().toString());
        return leaveService.getMyLeave();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('LEAVE_CREATE')")
    public LeaveResponse createLeave(@RequestBody LeaveRequest leaveRequest) {
        return LeaveMapper.toResponse(leaveService.applyLeave(leaveRequest));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('LEAVE_APPROVE')")
    public LeaveResponse approveLeave(@PathVariable Long id) {
        return LeaveMapper.toResponse(leaveService.approveLeave(id));
    }

}
