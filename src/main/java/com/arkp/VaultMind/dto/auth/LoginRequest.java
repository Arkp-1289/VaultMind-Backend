package com.arkp.VaultMind.dto.auth;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Username Cannot be empty")
    String userId;
    String password;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userName) {
        this.userId = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
