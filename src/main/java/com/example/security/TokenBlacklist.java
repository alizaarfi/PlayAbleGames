package com.example.security;

import jakarta.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple in memory service to store and check blacklisted tokens
 * Token can be added to the blacklist and checked later
 */

@Singleton
public class TokenBlacklist {

    //set of blacklisted tokens
    private final Set<String> blacklist= new HashSet<>();

    //adding a token to the blacklist
    public void blacklist(String token) {
        blacklist.add(token);
    }

    //check if a token is blacklisted
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
