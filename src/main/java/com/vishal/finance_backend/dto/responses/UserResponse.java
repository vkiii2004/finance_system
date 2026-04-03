package com.vishal.finance_backend.dto.responses;

import com.vishal.finance_backend.enums.RoleName;

public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private boolean active;
    private RoleName role;

    public UserResponse() {
    }

    public UserResponse(Long id, String name, String username, boolean active, RoleName role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.active = active;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}

