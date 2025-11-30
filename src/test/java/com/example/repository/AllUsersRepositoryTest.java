package com.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AllUsersRepositoryTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Test
    public void getAllUsers_returnsMappedRows() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getInt("UserID")).thenReturn(1, 2);
        when(resultSet.getString("Username")).thenReturn("alice", "bob");
        when(resultSet.getInt("reviewCount")).thenReturn(5, 3);
        when(resultSet.getBoolean("Enabled")).thenReturn(true, false);

        AllUsersRepository repo = new AllUsersRepository(dataSource);
        List<Map<String, Object>> results = repo.getAllUsers();

        assertEquals(2, results.size());

        Map<String, Object> user1 = results.get(0);
        assertEquals(1, user1.get("userId"));
        assertEquals("alice", user1.get("user"));
        assertEquals(5, user1.get("numReviews"));
        assertEquals(true, user1.get("enabled"));

        Map<String, Object> user2 = results.get(1);
        assertEquals(2, user2.get("userId"));
        assertEquals("bob", user2.get("user"));
        assertEquals(3, user2.get("numReviews"));
        assertEquals(false, user2.get("enabled"));

        verify(connection).prepareStatement(ArgumentMatchers.contains("FROM users"));
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void getAllUsers_throwsRuntimeExceptionOnSqlError() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB down"));
        AllUsersRepository repo = new AllUsersRepository(dataSource);

        RuntimeException ex = assertThrows(RuntimeException.class, repo::getAllUsers);
        assertTrue(ex.getMessage().contains("Failed to fetch all users"));
    }

    @Test
    public void getAllUsers_returnsEmptyListWhenNoUsers() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        AllUsersRepository repo = new AllUsersRepository(dataSource);
        List<Map<String, Object>> results = repo.getAllUsers();

        assertEquals(0, results.size());
        verify(preparedStatement).executeQuery();
    }
}
