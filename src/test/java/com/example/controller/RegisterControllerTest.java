package com.example.controller;

import com.example.api.RegistrationRequest;
import com.example.api.RegistrationResponse;
import com.example.service.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class RegisterControllerTest {

    @Inject
    RegisterController controller;

    @Inject
    UserService userService;   //micronaut injects real bean

    @Test
    void testSuccessfulRegister() {
        // arranges
        RegistrationRequest req = new RegistrationRequest();
        req.setUsername("john");
        req.setEmail("john@example.com");
        req.setPassword("password");

        // mocks service behaviour
        UserService mockService = Mockito.mock(UserService.class);
        controller = new RegisterController(mockService);

        // acts
        HttpResponse<RegistrationResponse> res = controller.register(req);

        // asserts
        assertEquals(200, res.getStatus().getCode());
        assertEquals("User registered successfully", res.body().getMessage());
    }

    @Test
    void testRegistrationFailDuplicateEmail() {
        //arranges
        RegistrationRequest req = new RegistrationRequest();
        req.setUsername("john");
        req.setEmail("john@example.com");
        req.setPassword("password");

        UserService mockService = Mockito.mock(UserService.class);

        //simulate duplicate email
        doThrow(new RuntimeException("Email already exists"))
                .when(mockService)
                .register(anyString(), anyString(), anyString());

        controller = new RegisterController(mockService);

        //acts
        HttpResponse<RegistrationResponse> res = controller.register(req);

        //asserts
        assertEquals(400, res.getStatus().getCode());
        assertEquals("Email already exists", res.body().getMessage());
    }

    @Test
    void testRegistrationFailDuplicateUsername() {
        // arranges
        RegistrationRequest req = new RegistrationRequest();
        req.setUsername("john");                 // duplicate username
        req.setEmail("newemail@example.com");
        req.setPassword("password");

        UserService mockService = Mockito.mock(UserService.class);

        //simulates duplicate username error from service
        doThrow(new RuntimeException("Username already exists"))
                .when(mockService)
                .register(anyString(), anyString(), anyString());

        controller = new RegisterController(mockService);

        // Acts
        HttpResponse<RegistrationResponse> res = controller.register(req);

        //asserts
        assertEquals(400, res.getStatus().getCode());
        assertEquals("Username already exists", res.body().getMessage());
    }

}
