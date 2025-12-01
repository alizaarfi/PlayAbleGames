package com.example.controller;

import com.example.repository.IndividualUserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndividualUserControllerTest {

    @Mock
    IndividualUserRepository repository;

    //   This test verifies that the getUserById method returns OK status with user data when the user exists
    @Test
    public void getUserById_returnsOkWithUserData() {
        int userId = 1;
        Map<String, Object> mocked = new HashMap<>();
        mocked.put("id", userId);
        mocked.put("username", "testuser");
        mocked.put("reviews", new ArrayList<>());

        when(repository.getUserWithReviews(userId)).thenReturn(mocked);

        IndividualUserController controller = new IndividualUserController(repository);
        HttpResponse<Map<String, Object>> resp = controller.getUserById(userId);

        assertEquals(HttpStatus.OK, resp.getStatus());
        assertSame(mocked, resp.body());
        verify(repository).getUserWithReviews(userId);
    }

    //   This test verifies that the getUserById method returns Not Found status with error message when the user does not exist
    @Test
    public void getUserById_returnsNotFoundWhenUserDoesNotExist() {
        int userId = 999;

        when(repository.getUserWithReviews(userId)).thenReturn(null);

        IndividualUserController controller = new IndividualUserController(repository);
        HttpResponse<Map<String, Object>> resp = controller.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, resp.getStatus());
        assertNotNull(resp.body());
        assertEquals("User not found", resp.body().get("error"));
        assertEquals(userId, resp.body().get("userId"));
        verify(repository).getUserWithReviews(userId);
    }

    //   This test verifies that the banUser method returns OK status when banning is successful
    @Test
    public void banUser_returnsOkWhenSuccessful() {
        int userId = 1;

        when(repository.banUser(userId)).thenReturn(true);

        IndividualUserController controller = new IndividualUserController(repository);
        HttpResponse<Map<String, Object>> resp = controller.banUser(userId);

        assertEquals(HttpStatus.OK, resp.getStatus());
        assertNotNull(resp.body());
        assertEquals("User banned successfully", resp.body().get("message"));
        assertEquals(userId, resp.body().get("userId"));
        verify(repository).banUser(userId);
    }

    //   This test verifies that the banUser method returns Server Error status when banning fails
    @Test
    public void banUser_returnsServerErrorWhenFailed() {
        int userId = 1;

        when(repository.banUser(userId)).thenReturn(false);

        IndividualUserController controller = new IndividualUserController(repository);
        HttpResponse<Map<String, Object>> resp = controller.banUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatus());
        assertNotNull(resp.body());
        assertEquals("Failed to ban user", resp.body().get("error"));
        assertEquals(userId, resp.body().get("userId"));
        verify(repository).banUser(userId);
    }
}
