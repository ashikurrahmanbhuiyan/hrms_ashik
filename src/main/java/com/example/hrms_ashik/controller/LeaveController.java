package com.example.hrms_ashik.controller;

import com.example.hrms_ashik.dto.LeaveRequest;
import com.example.hrms_ashik.entity.Leave;
import com.example.hrms_ashik.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leaves")
public class LeaveController {
    private final LeaveService leaveService;

    @GetMapping("/allLeaves")
    @PreAuthorize("hasAuthority('HR_VIEW')")
    public List<Leave> getAllLeave(){
        return leaveService.getAllLeave();
    }

    @GetMapping("myLeaves")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    public List<Leave> getMyLeave(){
        return leaveService.getMyLeave();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public ResponseEntity<Leave> createLeave(@RequestBody LeaveRequest leaveRequest){
        return ResponseEntity.ok(leaveService.applyLeave(leaveRequest));
    }
}
