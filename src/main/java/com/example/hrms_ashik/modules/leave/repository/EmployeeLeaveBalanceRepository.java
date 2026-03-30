package com.example.hrms_ashik.modules.leave.repository;

import com.example.hrms_ashik.modules.leave.entity.EmployeeLeaveBalance;
import com.example.hrms_ashik.modules.leave.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance, Long> {
    Optional<EmployeeLeaveBalance> findByEmployeeIdAndLeaveType(Long employeeId, LeaveType leaveType);
}
