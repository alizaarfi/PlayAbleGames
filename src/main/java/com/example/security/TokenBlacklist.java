package com.example.security;

import jakarta.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class TokenBlacklist {
    private final Set<String> blacklist= new HashSet<>();
    public void blacklist(String token) {
        blacklist.add(token);
    }
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
