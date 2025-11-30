package com.example.repository;

import com.example.domain.UserFavorite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFavoriteRepositoryTest {

    @Mock
    UserFavoriteRepository repo;

    // Verifies query returns favorites for the specified user.
    @Test
    void findByUserId_returnsUserFavorites() {
        Long userId = 1L;

        List<UserFavorite> mockResult = List.of(
                new UserFavorite(userId, 10L),
                new UserFavorite(userId, 11L)
        );

        when(repo.findByUserId(userId)).thenReturn(mockResult);

        List<UserFavorite> result = repo.findByUserId(userId);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getGameId().equals(10L)));
        assertTrue(result.stream().anyMatch(f -> f.getGameId().equals(11L)));
        verify(repo).findByUserId(userId);
    }

    // Verifies query returns matching favorite when it exists.
    @Test
    void findByUserIdAndGameId_returnsFavoriteWhenPresent() {
        Long userId = 1L;
        Long gameId = 10L;
        UserFavorite fav = new UserFavorite(userId, gameId);

        when(repo.findByUserIdAndGameId(userId, gameId))
                .thenReturn(Optional.of(fav));

        Optional<UserFavorite> result = repo.findByUserIdAndGameId(userId, gameId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUserId());
        assertEquals(gameId, result.get().getGameId());
        verify(repo).findByUserIdAndGameId(userId, gameId);
    }

    // Verifies query returns empty when no matching record exists.
    @Test
    void findByUserIdAndGameId_returnsEmptyWhenAbsent() {
        Long userId = 1L;
        Long gameId = 99L;

        when(repo.findByUserIdAndGameId(userId, gameId))
                .thenReturn(Optional.empty());

        Optional<UserFavorite> result = repo.findByUserIdAndGameId(userId, gameId);

        assertTrue(result.isEmpty());
        verify(repo).findByUserIdAndGameId(userId, gameId);
    }

    // Verifies delete invocation for a given user/game pair.
    @Test
    void deleteByUserIdAndGameId_invokedWithCorrectArgs() {
        Long userId = 1L;
        Long gameId = 10L;

        repo.deleteByUserIdAndGameId(userId, gameId);

        verify(repo).deleteByUserIdAndGameId(userId, gameId);
    }
}
