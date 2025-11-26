package com.example.controller;

import com.example.api.RegistrationRequest;
import com.example.api.RegistrationResponse;
import com.example.domain.User;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

/**
 * controller having user registration
 * allows new users to sign up with username, email, and password
 */
@Controller("/user")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService service) {
        this.userService = service;
    }

    /**
     * endpoint for registering a new user
     * 
     * @param request RegistrationRequest with username, email, password
     * @returnHTTP response with success or error message
     */
    @Secured(SecurityRule.IS_ANONYMOUS) // allow unauthenticated users
    @Post("/register")
    public HttpResponse<RegistrationResponse> register(@Body @Valid RegistrationRequest request) {
        try{
            userService.register(request.getUsername(), request.getEmail(), request.getPassword());
            return HttpResponse.ok(new RegistrationResponse("User registered successfully"));
        } catch (Exception e){
            return HttpResponse.badRequest(new RegistrationResponse(e.getMessage()));
        }
    }
}
