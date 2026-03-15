package com.example.hrms_ashik.leave.Repository;

import com.example.hrms_ashik.leave.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
    Optional<LeaveType> findByName(String name);
}
