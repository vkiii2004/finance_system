package com.vishal.finance_backend.dto.requests;

import com.vishal.finance_backend.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @jakarta.validation.constraints.NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @jakarta.validation.constraints.NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    private Boolean active;

    @NotNull
    private RoleName role;

    @Size(min = 6, max = 100)
    private String password; // optional (null -> keep old password)

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

