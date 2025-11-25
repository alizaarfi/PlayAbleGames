package com.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserFields(){
        User user = new User();

        user.setUserID(10L);
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("Password1");
        user.setEnabled(false);
        user.setProfilePictureURL("https://example.com/image.png");

        assertEquals(10L,user.getUserID());
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("Password1", user.getPassword());
        assertFalse(user.getEnabled());
        assertEquals("https://example.com/image.png", user.getProfilePictureURL());
    }

    @Test
    void testDefaultEnabledIsTrue() {
        User user = new User();
        assertTrue(user.getEnabled());
    }
}
