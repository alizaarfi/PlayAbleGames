package com.example.repository;

import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class AdminDashboardRepository {
    private final DataSource dataSource;

    public AdminDashboardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getTotalFlagged() {
        return callProcedureWithOutParam("totalFlaggedReviews");
    }

    public int getTotalUsers() {
        return callProcedureWithOutParam("totalUsers");
    }

    public int getTotalGames() {
        return callProcedureWithOutParam("totalGames");
    }

    private int callProcedureWithOutParam(String procedureName) {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL " + procedureName + "(?)}")) {

            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to call procedure: " + procedureName, e);
        }
    }
}