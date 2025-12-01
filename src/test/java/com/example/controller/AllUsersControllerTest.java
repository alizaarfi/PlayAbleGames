package com.example.controller;

import com.example.repository.AllUsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AllUsersControllerTest {

    @Mock
    AllUsersRepository repository;
//   This test verifies that the getAllUsers method returns a map containing the user list
    @Test
    public void getAllUsers_returnsMapWithUserKey() {
        List<Map<String, Object>> mocked = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("id", 1);
        row.put("username", "testuser");
        row.put("email", "test@example.com");
        mocked.add(row);

        when(repository.getAllUsers()).thenReturn(mocked);

        AllUsersController controller = new AllUsersController(repository);
        Map<String, Object> resp = controller.getAllUsers();

        assertTrue(resp.containsKey("user"));
        assertSame(mocked, resp.get("user"));
        verify(repository).getAllUsers();
    }

    //   This test verifies that the getAllUsers method handles an empty user list correctly
    @Test
    public void getAllUsers_returnsEmptyListWhenNoUsers() {
        List<Map<String, Object>> emptyList = new ArrayList<>();

        when(repository.getAllUsers()).thenReturn(emptyList);

        AllUsersController controller = new AllUsersController(repository);
        Map<String, Object> resp = controller.getAllUsers();

        assertTrue(resp.containsKey("user"));
        assertTrue(((List<?>) resp.get("user")).isEmpty());
        verify(repository).getAllUsers();
    }
}
