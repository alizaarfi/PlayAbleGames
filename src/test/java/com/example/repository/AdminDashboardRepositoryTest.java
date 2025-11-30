package com.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminDashboardRepositoryTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    CallableStatement callableStatement;

    @Test
    public void getTotalFlagged_returnsValueFromProcedure() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareCall(ArgumentMatchers.anyString())).thenReturn(callableStatement);

        doNothing().when(callableStatement).registerOutParameter(1, java.sql.Types.INTEGER);
        when(callableStatement.execute()).thenReturn(true);
        when(callableStatement.getInt(1)).thenReturn(7);

        AdminDashboardRepository repo = new AdminDashboardRepository(dataSource);
        int result = repo.getTotalFlagged();
        assertEquals(7, result);

        verify(connection).prepareCall(ArgumentMatchers.contains("totalFlaggedReviews"));
        verify(callableStatement).registerOutParameter(1, java.sql.Types.INTEGER);
        verify(callableStatement).execute();
        verify(callableStatement).getInt(1);
        verify(connection).close();
        verify(callableStatement).close();
    }

    @Test
    public void getTotalFlagged_throwsRuntimeExceptionOnSqlError() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB down"));
        AdminDashboardRepository repo = new AdminDashboardRepository(dataSource);
        RuntimeException ex = assertThrows(RuntimeException.class, repo::getTotalFlagged);
        assertTrue(ex.getMessage().contains("Failed to call procedure"));
    }
}