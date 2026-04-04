package com.vishal.finance_backend.service;

import com.vishal.finance_backend.Entity.Role;
import com.vishal.finance_backend.Entity.User;
import com.vishal.finance_backend.dto.requests.CreateUserRequest;
import com.vishal.finance_backend.dto.requests.UpdateUserRequest;
import com.vishal.finance_backend.dto.responses.UserResponse;
import com.vishal.finance_backend.exception.ApiException;
import com.vishal.finance_backend.repository.RoleRepository;
import com.vishal.finance_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw ApiException.conflict("Username already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(Boolean.TRUE.equals(request.getActive()));

        Role role = roleRepository
                .findByName(request.getRole())
                .orElseThrow(() -> ApiException.notFound("Role not found"));

        user.setRole(role);

        User saved = userRepository.save(user);
        return toResponse(saved);
    }
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> ApiException.notFound("User not found"));
        return toResponse(user);
    }


    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> ApiException.notFound("User not found"));

        // If username changes, ensure uniqueness.
        String targetUsername = request.getUsername();
        if (!user.getUsername().equals(targetUsername) && userRepository.existsByUsername(targetUsername)) {
            throw ApiException.conflict("Username already exists");
        }

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setActive(Boolean.TRUE.equals(request.getActive()));

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Role role = roleRepository
                .findByName(request.getRole())
                .orElseThrow(() -> ApiException.notFound("Role not found"));
        user.setRole(role);

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() -> ApiException.notFound("User not found"));

        userRepository.delete(user);
    }


    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.isActive(),
                user.getRole() != null ? user.getRole().getName() : null
        );
    }

}
