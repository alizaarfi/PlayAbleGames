package com.example.repository;

import jakarta.inject.Singleton;
import io.micronaut.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


/**
 * Repository that retrieves a list of non-admin users and their review counts.
 *
 * - Uses constructor injection to obtain a JDBC {@link DataSource}.
 * - Executes a single SQL query that left-joins reviews and authentication info,
 *   filters out ADMIN roles, groups by user and returns basic user info.
 * - Returns a list of maps representing each user row for simple JSON serialization.
 */
@Singleton
public class AllUsersRepository {
    private final DataSource dataSource;

    /**
     * Constructor injection: framework supplies a DataSource bean.
     *
     * @param dataSource JDBC data source used to open connections.
     */
    public AllUsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Fetch all non-admin users with the number of reviews they have.
     *
     * - The SQL query performs LEFT JOIN to include users with zero reviews.
     * - Joins with user_authentication to access role information and exclude admins.
     * - Groups by user fields and orders by username ascending.
     *
     * @return list of maps; each map contains keys: "userId", "user", "numReviews", "enabled"
     */
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
