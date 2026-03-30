package com.example.hrms_ashik.modules.leave.entity;

import com.example.hrms_ashik.modules.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_leave_balance", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "employee_id", "leave_type_id" })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeaveType leaveType;

    private Integer remainingDays;

}
