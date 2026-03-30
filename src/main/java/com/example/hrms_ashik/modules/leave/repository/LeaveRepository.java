package com.example.hrms_ashik.modules.leave.repository;

import com.example.hrms_ashik.modules.leave.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeUserUsername(String username);

    @Query("""
            SELECT COUNT(l) > 0 FROM Leave l
            WHERE l.employee.id = :employeeId
            AND l.status <> 'REJECTED'
            AND (
                l.startDate <= :endDate
                AND l.endDate >= :startDate
            )
            """)
    boolean existsOverlappingLeave(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate);
}