package com.example.repository;

import com.example.domain.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    void testSaveAndFindUserById() {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("Password1");

        User saved = userRepository.save(user);

        assertNotNull(saved.getUserID());

        Optional<User> found = userRepository.findById(saved.getUserID());
        assertTrue(found.isPresent());
        assertEquals("john", found.get().getUsername());
        assertEquals("john@example.com", found.get().getEmail());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("uniqueUser");
        user.setEmail("unique@example.com");
        user.setPassword("Password1");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("uniqueUser");
        assertTrue(found.isPresent());
        assertEquals("unique@example.com", found.get().getEmail());
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("Password1");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("testUser", found.get().getUsername());
    }
}
