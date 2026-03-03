package com.example.hrms_ashik.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Table(name = "leaves")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;
}
