package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository );
    }

    @Test
    void testRegisterUser() {
        when(userRepository.findByEmail("john@mail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.register("john", "john@mail.com", "Password1");

        assertEquals("john", user.getUsername());

        // password should NOT be plaintext
        assertNotEquals("Password1", user.getPassword());
        assertTrue(BCrypt.checkpw("Password1", user.getPassword()));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterFailsWhenEmailExists() {
        when(userRepository.findByEmail("john@mail.com"))
                .thenReturn(Optional.of(new User()));

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () ->
                userService.register("john", "john@mail.com", "Password1")
        );

        assertEquals("Email already registered", e.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterFailsWhenUsernameExists() {
        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(new User()));

        when(userRepository.findByEmail("new@mail.com"))
                .thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () ->
                userService.register("john", "new@mail.com", "Password1")
        );

        assertEquals("Username already exists", e.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticateSuccess() {
        String rawPassword = "Password1";
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        User user = new User();
        user.setUsername("john");
        user.setPassword(hashed);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        Optional<User> result = userService.authenticate("john", rawPassword);

        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
    }

    @Test
    void testAuthenticateFailsWhenUserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = userService.authenticate("nonexistent", "Password1");

        assertTrue(result.isEmpty());
    }

    @Test
    void testAuthenticateFailsWhenPasswordIncorrect() {
        String hashed = BCrypt.hashpw("Password1", BCrypt.gensalt());
        User user = new User();
        user.setUsername("john");
        user.setPassword(hashed);

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        RuntimeException e = assertThrows(RuntimeException.class, () ->
                userService.authenticate("john", "WrongPassword")
        );

        assertEquals("Invalid password", e.getMessage());
    }

}
