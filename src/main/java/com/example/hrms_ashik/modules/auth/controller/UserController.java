package com.example.hrms_ashik.modules.auth.controller;

import com.example.hrms_ashik.modules.auth.dto.LogInRequest;
import com.example.hrms_ashik.modules.auth.dto.RegisterRequest;
import com.example.hrms_ashik.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final AuthService authService;

    @GetMapping("/connection_check")
    public String connectionCheck() {
        return "connected successfully";
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest) {
       return authService.register(registerRequest);
    }
    @PostMapping("/login")
    public String login(@Valid @RequestBody LogInRequest logInRequest){
        return authService.login(logInRequest);
    }
}
