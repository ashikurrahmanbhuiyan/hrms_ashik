package com.example.hrms_ashik.modules.employee.repository;

import com.example.hrms_ashik.modules.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
        Optional<Employee> findByUserUsername(String username);
}
