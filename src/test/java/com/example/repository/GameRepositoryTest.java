package com.example.repository;

import com.example.domain.Game;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GameRepository using the test database.
 */
@MicronautTest(startApplication = false)
class GameRepositoryTest {

    @Inject
    GameRepository gameRepository;

    @Test
    void findById_returnsSeededMinecraftGame() {
        // --- act ---
        Optional<Game> result = gameRepository.findById(1L);

        // --- assert: record from data.sql is visible ---
        assertTrue(result.isPresent());
        Game game = result.get();
        assertEquals("Minecraft", game.getName());
        assertEquals("Mojang Studios", game.getDeveloper());
    }
}
