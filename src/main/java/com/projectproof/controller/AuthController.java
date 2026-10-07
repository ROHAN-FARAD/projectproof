package com.projectproof.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectproof.dto.auth.LoginRequest;
import com.projectproof.dto.auth.LoginResponse;
import com.projectproof.dto.auth.RegisterRequest;
import com.projectproof.dto.user.UserResponse;
import com.projectproof.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register new user
    @PostMapping("/register")
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    // Login user
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}