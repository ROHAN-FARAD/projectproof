package com.projectproof.serviceimpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectproof.dto.auth.LoginRequest;
import com.projectproof.dto.auth.LoginResponse;
import com.projectproof.dto.auth.RegisterRequest;
import com.projectproof.dto.user.UserResponse;
import com.projectproof.entity.Role;
import com.projectproof.entity.User;
import com.projectproof.exception.BadRequestException;
import com.projectproof.repository.RoleRepository;
import com.projectproof.repository.UserRepository;
import com.projectproof.security.JwtService;
import com.projectproof.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        // Find requested role
        String roleName = request.getRole();

        if (roleName == null || roleName.isBlank()) {
            roleName = "STUDENT";
        }

        String finalRoleName = roleName.toUpperCase();

        Role role = roleRepository.findByName(finalRoleName)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Role not found: " + finalRoleName));

        // Create user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setStudentId(request.getStudentId());
        user.setRole(role);
        user.setIsActive(true);

        // Save user
        User savedUser = userRepository.save(user);

        // Convert entity to response
        return mapToResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadRequestException(
                    "Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Return login response
        return new LoginResponse(
                token,
                "Bearer",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    private UserResponse mapToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setStudentId(user.getStudentId());

        if (user.getRole() != null) {
            response.setRole(user.getRole().getName());
        }

        if (user.getDepartment() != null) {
            response.setDepartment(
                    user.getDepartment().getName()
            );
        }

        response.setIsActive(user.getIsActive());

        return response;
    }
}