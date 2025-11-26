package com.example.api;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class LoginResponse {
    public String token;
    public String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }
    public String getMessage() {
        return message;
    }
}
