package com.example.repository;

import jakarta.inject.Singleton;
import io.micronaut.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Singleton
public class AllUsersRepository {
    private final DataSource dataSource;

    public AllUsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public List<Map<String, Object>> getAllUsers() {
        List<Map<String, Object>> users = new ArrayList<>();

        String sql = "Select users.UserID, users.Username, users.Enabled, COUNT(reviews.ReviewID) as reviewCount\n" +
                "FROM users\n" +
                "LEFT JOIN reviews ON users.UserID = reviews.UserID\n" +
                "LEFT JOIN user_authentication ON users.UserID = user_authentication.userid\n" +
                "WHERE role != 'ADMIN'\n" +
                "GROUP BY users.UserID, users.Username, users.Enabled\n" +
                "ORDER BY Username asc;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("userId", rs.getInt("UserID"));
                user.put("user", rs.getString("Username"));
                user.put("numReviews", rs.getInt("reviewCount"));
                user.put("enabled", rs.getBoolean("Enabled"));

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all users", e);
        }

        return users;
    }
}
