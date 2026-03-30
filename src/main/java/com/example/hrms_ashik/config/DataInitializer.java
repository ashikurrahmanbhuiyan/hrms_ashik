package com.example.hrms_ashik.config;

import com.example.hrms_ashik.modules.auth.entity.Permission;
import com.example.hrms_ashik.modules.auth.entity.Role;
import com.example.hrms_ashik.modules.auth.entity.User;
import com.example.hrms_ashik.modules.auth.repository.PermissionRepository;
import com.example.hrms_ashik.modules.auth.repository.RoleRepository;
import com.example.hrms_ashik.modules.auth.repository.UserRepository;
import com.example.hrms_ashik.security.Permissions;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {

        if (roleRepository.findByRoleName("ADMIN").isPresent()) {
            return; // already initialized
        }

        Permission empCreate = permissionRepository.save(
                Permission.builder().permissionName(Permissions.EMPLOYEE_CREATE).build());

        Permission empView = permissionRepository.save(
                Permission.builder().permissionName(Permissions.EMPLOYEE_VIEW).build());

        Permission empUpdate = permissionRepository.save(
                Permission.builder().permissionName(Permissions.EMPLOYEE_UPDATE).build());

        Permission empDelete = permissionRepository.save(
                Permission.builder().permissionName(Permissions.EMPLOYEE_DELETE).build());

        Role adminRole = roleRepository.save(
                Role.builder()
                        .roleName("ADMIN")
                        .permissions(Set.of(empCreate, empView, empUpdate, empDelete))
                        .build()
        );

        Role hrRole = roleRepository.save(
                Role.builder()
                        .roleName("HR")
                        .permissions(Set.of(empCreate, empView, empUpdate))
                        .build()
        );

        Role employeeRole = roleRepository.save(
                Role.builder()
                        .roleName("EMPLOYEE")
                        .permissions(Set.of(empView))
                        .build()
        );

        User admin = User.builder()
                .username("admin")
                .email("admin@hrms.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(Set.of(adminRole))
                .build();

        userRepository.save(admin);
    }
}