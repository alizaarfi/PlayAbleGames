package com.example.api;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class LoginResponse {
    public String token;
    public String message;
    private String role;

    public LoginResponse(String token, String message, String role) {
        this.token = token;
        this.message = message;
        this.role = role;
    }

    public String getToken() {
        return token;
    }
    public String getMessage() {
        return message;
    }
    public String getRole() { return role; }
}
