package com.example.controller;

import com.example.api.LoginRequest;
import com.example.api.LoginResponse;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.Optional;

@Controller("/user")
public class LoginController {

    private final UserService userService;
    @Inject
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginController(UserService userService, JwtTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.jwtTokenGenerator=jwtTokenGenerator;
    }

    @Post("/login")
    public HttpResponse<?> login(@Body LoginRequest loginRequest) {
        var user= userService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword());

        if (user.isEmpty()){
            return HttpResponse.unauthorized();
        }

        //generating JWT using micronaut
        Optional<String> tokenOpt= jwtTokenGenerator.generateToken(
                        Map.of("sub",loginRequest.getUsername())
        );

        if (tokenOpt.isEmpty()){
            return HttpResponse.serverError("Token generation failed");
        }

        return HttpResponse.ok(new LoginResponse(tokenOpt.get(), "Login Successful"));

    }

}
