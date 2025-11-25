package com.example.api;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class RegistrationResponse {
    private String message;

    public RegistrationResponse() {
    }

    public RegistrationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
