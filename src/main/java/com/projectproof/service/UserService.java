package com.projectproof.service;

import java.util.List;

import com.projectproof.dto.user.UserResponse;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, String name, String studentId);

    void deleteUser(Long id);
}