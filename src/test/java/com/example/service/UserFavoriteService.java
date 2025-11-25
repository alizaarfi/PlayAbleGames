package com.example.service;

import com.example.domain.UserFavorite;
import com.example.repository.UserFavoriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFavoriteServiceTest {

    @Mock
    private UserFavoriteRepository repo;

    @InjectMocks
    private UserFavoriteService service;

    // Verifies new favorites are persisted when not already present.
    @Test
    void addFavorite_savesNewFavorite() {
        Long userId = 1L;
        Long gameId = 42L;

        when(repo.findByUserIdAndGameId(userId, gameId)).thenReturn(Optional.empty());

        service.addFavorite(userId, gameId);

        ArgumentCaptor<UserFavorite> captor = ArgumentCaptor.forClass(UserFavorite.class);
        verify(repo).save(captor.capture());

        UserFavorite saved = captor.getValue();
        assertEquals(userId, saved.getUserId());
        assertEquals(gameId, saved.getGameId());
    }

    // Verifies duplicates are not persisted.
    @Test
    void addFavorite_doesNotSaveDuplicate() {
        Long userId = 1L;
        Long gameId = 42L;

        when(repo.findByUserIdAndGameId(userId, gameId))
                .thenReturn(Optional.of(new UserFavorite(userId, gameId)));

        service.addFavorite(userId, gameId);

        verify(repo, never()).save(any());
    }

    // Verifies deletion is delegated correctly.
    @Test
    void removeFavorite_deletesByUserAndGame() {
        Long userId = 1L;
        Long gameId = 42L;

        service.removeFavorite(userId, gameId);

        verify(repo).deleteByUserIdAndGameId(userId, gameId);
    }
}
