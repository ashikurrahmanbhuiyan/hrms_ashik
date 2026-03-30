package com.example.hrms_ashik.modules.auth.service;

import com.example.hrms_ashik.config.JwtService;
import com.example.hrms_ashik.modules.auth.dto.LogInRequest;
import com.example.hrms_ashik.modules.auth.dto.RegisterRequest;
import com.example.hrms_ashik.modules.auth.entity.Role;
import com.example.hrms_ashik.modules.auth.entity.User;
import com.example.hrms_ashik.modules.auth.repository.RoleRepository;
import com.example.hrms_ashik.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public String  register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("username is exist");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("email is exist");
        }
        Role employeeRole = roleRepository.findByRoleName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("role is not exist"));

        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(employeeRole))
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(@NonNull LogInRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );
        return jwtService.generateToken(request.getUsername());
    }


//    String username = Objects.requireNonNull(SecurityContextHolder
//                    .getContext()
//                    .getAuthentication())
//                    .getName();
}
