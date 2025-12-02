package com.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndividualUserRepositoryTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    //   This test verifies that getUserWithReviews correctly fetches a user and their reviews from the database
    @Test
    public void getUserWithReviews_returnsUserWithReviews() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true, true, false);

        when(resultSet.getInt("UserID")).thenReturn(1);
        when(resultSet.getString("Username")).thenReturn("alice");
        when(resultSet.getBoolean("Enabled")).thenReturn(true);

        when(resultSet.getInt("ReviewID")).thenReturn(101, 102);
        when(resultSet.getInt("GameID")).thenReturn(10, 11);
        when(resultSet.getString("GameName")).thenReturn("SpaceGame", "RacingGame");
        when(resultSet.getInt("HearingRating")).thenReturn(3, 4);
        when(resultSet.getInt("VisionRating")).thenReturn(4, 5);
        when(resultSet.getInt("CognitiveRating")).thenReturn(5, 3);
        when(resultSet.getInt("SpeechRating")).thenReturn(2, 1);
        when(resultSet.getInt("MotorRating")).thenReturn(1, 2);
        when(resultSet.getInt("GameplayRating")).thenReturn(4, 5);
        when(resultSet.getString("ReviewText")).thenReturn("Great game", "Good controls");
        when(resultSet.getDate("ReviewDate")).thenReturn(
                Date.valueOf("2023-01-02"),
                Date.valueOf("2023-01-03")
        );
        when(resultSet.getBoolean("Flagged")).thenReturn(false, true);
        when(resultSet.getBoolean("Removed")).thenReturn(false, false);

        IndividualUserRepository repo = new IndividualUserRepository(dataSource);
        Map<String, Object> result = repo.getUserWithReviews(1);

        assertNotNull(result);

        assertEquals(1, result.get("userId"));
        assertEquals("alice", result.get("username"));
        assertEquals(true, result.get("enabled"));

        List<Map<String, Object>> reviews = (List<Map<String, Object>>) result.get("reviews");
        assertEquals(2, reviews.size());

        Map<String, Object> review1 = reviews.get(0);
        assertEquals(101, review1.get("id"));
        assertEquals(10, review1.get("gameID"));
        assertEquals("SpaceGame", review1.get("game"));
        assertEquals(3, review1.get("hearingRating"));
        assertEquals(4, review1.get("visionRating"));
        assertEquals(5, review1.get("cognitiveRating"));
        assertEquals(2, review1.get("speechRating"));
        assertEquals(1, review1.get("motorRating"));
        assertEquals(4, review1.get("gamePlayRating"));
        assertEquals("Great game", review1.get("reviewText"));
        assertEquals("2023-01-02", review1.get("reviewDate"));
        assertEquals(false, review1.get("flagged"));
        assertEquals(false, review1.get("removed"));

        verify(connection, times(2)).prepareStatement(ArgumentMatchers.anyString());
        verify(preparedStatement, times(2)).executeQuery();
    }

    //   This test verifies that getUserWithReviews returns null when the user is not found
    @Test
    public void getUserWithReviews_returnsNullWhenUserNotFound() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        IndividualUserRepository repo = new IndividualUserRepository(dataSource);
        Map<String, Object> result = repo.getUserWithReviews(999);

        assertNull(result);
        verify(preparedStatement).executeQuery();
    }

    //   This test verifies that getUserWithReviews returns a user with an empty reviews list when the user has no reviews
    @Test
    public void getUserWithReviews_returnsUserWithEmptyReviewsList() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("UserID")).thenReturn(1);
        when(resultSet.getString("Username")).thenReturn("alice");
        when(resultSet.getBoolean("Enabled")).thenReturn(true);

        IndividualUserRepository repo = new IndividualUserRepository(dataSource);
        Map<String, Object> result = repo.getUserWithReviews(1);

        assertNotNull(result);
        assertEquals(1, result.get("userId"));

        List<Map<String, Object>> reviews = (List<Map<String, Object>>) result.get("reviews");
        assertEquals(0, reviews.size());
    }

    //   This test verifies that getUserWithReviews throws a RuntimeException when a SQLException occurs
    @Test
    public void getUserWithReviews_throwsRuntimeExceptionOnSqlError() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB down"));
        IndividualUserRepository repo = new IndividualUserRepository(dataSource);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repo.getUserWithReviews(1));
        assertTrue(ex.getMessage().contains("Failed to fetch user data for userId: 1"));
    }

    //   This test verifies that banUser correctly updates the user's enabled status to false
    @Test
    public void banUser_returnsTrue_whenUserBannedSuccessfully() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        IndividualUserRepository repo = new IndividualUserRepository(dataSource);
        boolean result = repo.banUser(1);

        assertTrue(result);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    //   This test verifies that banUser returns false when no rows are affected (userId does not exist)
    @Test
    public void banUser_returnsFalse_whenNoRowsAffected() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);

        IndividualUserRepository repo = new IndividualUserRepository(dataSource);
        boolean result = repo.banUser(999);

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }

    //   This test verifies that banUser throws a RuntimeException when a SQLException occurs
    @Test
    public void banUser_throwsRuntimeExceptionOnSqlError() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB down"));
        IndividualUserRepository repo = new IndividualUserRepository(dataSource);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> repo.banUser(1));
        assertTrue(ex.getMessage().contains("Failed to ban user with userId: 1"));
    }
}
