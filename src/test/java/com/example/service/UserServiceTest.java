package com.example.service;

import com.example.domain.User;
import com.example.domain.UserRole;
import com.example.domain.Role;
import com.example.repository.UserRepository;
import com.example.repository.UserRoleRepository;
import com.example.repository.RoleRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MicronautTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUserID(1L);
            return user;
        });
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(new UserRole());

        User result = userService.register(username, email, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertTrue(BCrypt.checkpw(password, result.getPassword()));
        verify(userRepository).save(any(User.class));
        verify(userRoleRepository).save(any(UserRole.class));
    }

    @Test
    void testRegisterWithExistingUsername() {
        String username = "existing";
        String email = "new@example.com";
        String password = "password123";

        User existingUser = new User();
        existingUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.register(username, email, password));

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterWithExistingEmail() {
        String username = "newuser";
        String email = "existing@example.com";
        String password = "password123";

        User existingUser = new User();
        existingUser.setEmail(email);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.register(username, email, password));

        assertEquals("Email already registered", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAuthenticateValidCredentials() {
        String username = "testuser";
        String rawPassword = "password123";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.authenticate(username, rawPassword);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void testAuthenticateInvalidPassword() {
        String username = "testuser";
        String correctPassword = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = BCrypt.hashpw(correctPassword, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.authenticate(username, wrongPassword);

        assertFalse(result.isPresent());
    }

    @Test
    void testAuthenticateNonExistentUser() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = userService.authenticate("nonexistent", "password");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetUserRoles() {
        Long userId = 1L;

        UserRole userRole = new UserRole();
        userRole.setRoleId(1);

        Role role = new Role();
        role.setName("ADMIN");

        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(userRole));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        List<String> roles = userService.getUserRoles(userId);

        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0));
    }

    @Test
    void testIsAdmin() {
        Long userId = 1L;

        UserRole userRole = new UserRole();
        userRole.setRoleId(1);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(userRole));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(adminRole));

        assertTrue(userService.isAdmin(userId));
    }

    @Test
    void testIsNotAdmin() {
        Long userId = 2L;

        UserRole userRole = new UserRole();
        userRole.setRoleId(2);

        Role basicRole = new Role();
        basicRole.setName("BASIC");

        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(userRole));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(basicRole));

        assertFalse(userService.isAdmin(userId));
    }

    @Test
    void testChangePasswordSuccess() {
        Long userId = 1L;
        String oldPassword = "oldpass123";
        String newPassword = "newpass123";
        String hashedOldPassword = BCrypt.hashpw(oldPassword, BCrypt.gensalt());

        User user = new User();
        user.setUserID(userId);
        user.setPassword(hashedOldPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).updatePassword(anyLong(), anyString());

        assertDoesNotThrow(() ->
                userService.changePassword(userId, oldPassword, newPassword));

        verify(userRepository).updatePassword(eq(userId), anyString());
    }

    @Test
    void testChangePasswordWrongOldPassword() {
        Long userId = 1L;
        String oldPassword = "oldpass123";
        String wrongPassword = "wrongpass";
        String newPassword = "newpass123";
        String hashedOldPassword = BCrypt.hashpw(oldPassword, BCrypt.gensalt());

        User user = new User();
        user.setUserID(userId);
        user.setPassword(hashedOldPassword);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.changePassword(userId, wrongPassword, newPassword));

        assertEquals("Old password is incorrect", exception.getMessage());
        verify(userRepository, never()).updatePassword(anyLong(), anyString());
    }
}