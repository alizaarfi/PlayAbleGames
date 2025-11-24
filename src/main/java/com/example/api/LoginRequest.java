package com.example.api;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    //getters & setters
    public String getUsername() {return username;};
    public String getPassword() {return password;};

    public void setUsername(String username) {};
    public void setPassword(String password) {};
}
