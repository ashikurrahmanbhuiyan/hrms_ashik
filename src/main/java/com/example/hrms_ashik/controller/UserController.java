package com.example.hrms_ashik.controller;

import com.example.hrms_ashik.dto.LogInRequest;
import com.example.hrms_ashik.dto.RegisterRequest;
import com.example.hrms_ashik.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final AuthService authService;
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest) {
       return authService.register(registerRequest);
    }
    @PostMapping("/login")
    public String login(@Valid @RequestBody LogInRequest logInRequest){
        return authService.login(logInRequest);
    }
}
