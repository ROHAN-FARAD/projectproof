package com.projectproof.service;

import com.projectproof.dto.auth.LoginRequest;
import com.projectproof.dto.auth.LoginResponse;
import com.projectproof.dto.auth.RegisterRequest;
import com.projectproof.dto.user.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}