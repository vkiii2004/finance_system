package com.vishal.finance_backend.service;

import com.vishal.finance_backend.dto.requests.CreateUserRequest;
import com.vishal.finance_backend.dto.requests.UpdateUserRequest;
import com.vishal.finance_backend.dto.responses.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
