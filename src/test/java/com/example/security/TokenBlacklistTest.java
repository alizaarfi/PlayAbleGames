package com.example.security;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TokenBlacklistTest {

    @Inject
    TokenBlacklist tokenBlacklist;

    private static final String TEST_TOKEN = "test.jwt.token";

    @BeforeEach
    void setUp() {
        // Clear any previous state (if using singleton, tokens persist)
        // For production, consider using @Prototype scope or clearing mechanism
    }

    @Test
    void testTokenIsNotBlacklistedInitially() {
        assertFalse(tokenBlacklist.isBlacklisted(TEST_TOKEN),
                "New token should not be blacklisted");
    }

    @Test
    void testBlacklistToken() {
        tokenBlacklist.blacklist(TEST_TOKEN);
        assertTrue(tokenBlacklist.isBlacklisted(TEST_TOKEN),
                "Token should be blacklisted after calling blacklist()");
    }

    @Test
    void testBlacklistMultipleTokens() {
        String token1 = "token.one";
        String token2 = "token.two";

        tokenBlacklist.blacklist(token1);
        tokenBlacklist.blacklist(token2);

        assertTrue(tokenBlacklist.isBlacklisted(token1));
        assertTrue(tokenBlacklist.isBlacklisted(token2));
    }

    @Test
    void testBlacklistingSameTokenTwice() {
        tokenBlacklist.blacklist(TEST_TOKEN);
        tokenBlacklist.blacklist(TEST_TOKEN); // Should not cause error

        assertTrue(tokenBlacklist.isBlacklisted(TEST_TOKEN));
    }

    @Test
    void testNonBlacklistedTokenReturnsFalse() {
        tokenBlacklist.blacklist("blacklisted.token");

        assertFalse(tokenBlacklist.isBlacklisted("different.token"),
                "Different token should not be affected");
    }
}