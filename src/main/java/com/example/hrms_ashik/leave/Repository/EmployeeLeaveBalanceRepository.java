package com.example.hrms_ashik.leave.Repository;

import com.example.hrms_ashik.entity.Employee;
import com.example.hrms_ashik.leave.entity.EmployeeLeaveBalance;
import com.example.hrms_ashik.leave.entity.LeaveType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance, Long> {
    Optional<EmployeeLeaveBalance> findByEmployeeAndLeaveType(Employee employee, LeaveType leaveType);
}
