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
public class FlaggedReviewsRepositoryTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Test
    public void getFlaggedReviews_returnsMappedRows() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("ReviewID")).thenReturn(101);           // Database column
        when(resultSet.getInt("UserID")).thenReturn(11);              // Database column
        when(resultSet.getString("Username")).thenReturn("alice");    // Database column
        when(resultSet.getString("GameName")).thenReturn("SpaceGame"); // Database column
        when(resultSet.getInt("HearingRating")).thenReturn(3);
        when(resultSet.getInt("VisionRating")).thenReturn(4);
        when(resultSet.getInt("CognitiveRating")).thenReturn(5);
        when(resultSet.getInt("SpeechRating")).thenReturn(2);
        when(resultSet.getInt("MotorRating")).thenReturn(1);
        when(resultSet.getInt("GameplayRating")).thenReturn(4);
        when(resultSet.getString("ReviewText")).thenReturn("inappropriate");
        when(resultSet.getDate("ReviewDate")).thenReturn(Date.valueOf("2023-01-02"));

        FlaggedReviewsRepository repo = new FlaggedReviewsRepository(dataSource);
        List<Map<String, Object>> results = repo.getFlaggedReviews();

        assertEquals(1, results.size());
        Map<String, Object> r = results.get(0);

        assertEquals(101, r.get("id"));
        assertEquals(11, r.get("UserID"));
        assertEquals("alice", r.get("user"));
        assertEquals("SpaceGame", r.get("game"));
        assertEquals(3, r.get("hearingRating"));
        assertEquals(4, r.get("visionRating"));
        assertEquals(5, r.get("cognitiveRating"));
        assertEquals(2, r.get("speechRating"));
        assertEquals(1, r.get("motorRating"));
        assertEquals(4, r.get("gamePlayRating"));
        assertEquals("inappropriate", r.get("reviewText"));
        assertEquals("2023-01-02", r.get("reviewDate"));

        verify(connection).prepareStatement(ArgumentMatchers.contains("FROM reviews r"));
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void getFlaggedReviews_throwsRuntimeExceptionOnSqlError() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB down"));
        FlaggedReviewsRepository repo = new FlaggedReviewsRepository(dataSource);

        RuntimeException ex = assertThrows(RuntimeException.class, repo::getFlaggedReviews);
        assertTrue(ex.getMessage().contains("Failed to fetch flagged reviews"));
    }

    @Test
    public void getFlaggedReviews_returnsEmptyListWhenNoFlaggedReviews() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        FlaggedReviewsRepository repo = new FlaggedReviewsRepository(dataSource);
        List<Map<String, Object>> results = repo.getFlaggedReviews();

        assertEquals(0, results.size());
        verify(preparedStatement).executeQuery();
    }
}