package com.example.repository;

import jakarta.inject.Singleton;
import io.micronaut.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Repository that loads a single user's profile and their reviews, and performs user updates.
 *
 * - Uses constructor injection to obtain a JDBC {@link DataSource}.
 * - Returns simple Map-based structures that controllers can serialize to JSON.
 * - Each public operation that touches the database is annotated with {@link Transactional}.
 */
@Singleton
public class IndividualUserRepository {
    private final DataSource dataSource;

    /**
     * Constructor injection: the framework supplies a DataSource bean.
     *
     * @param dataSource JDBC data source used to open connections.
     */
    public IndividualUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Load a user's basic information and their reviews.
     *
     * - Queries the users table for the requested userId. If not found, returns null.
     * - Delegates loading of the user's reviews to a private helper that maps result rows to Maps.
     * - Returns a Map with keys: "userId", "username", "enabled", and "reviews".
     *
     * @param userId id of the user to fetch
     * @return map of user data and reviews, or null when the user does not exist
     */
    @Transactional
    public Map<String, Object> getUserWithReviews(int userId) {
        Map<String, Object> result = new HashMap<>();

        // First, get user information
        String userSql = "SELECT UserID, Username, Enabled " +
                "FROM users " +
                "WHERE UserID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(userSql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result.put("userId", rs.getInt("UserID"));
                    result.put("username", rs.getString("Username"));
                    result.put("enabled", rs.getBoolean("Enabled"));
                } else {
                    return null; // User not found
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user data for userId: " + userId, e);
        }

        // Then, get all reviews for this user
        List<Map<String, Object>> reviews = getUserReviews(userId);
        result.put("reviews", reviews);

        return result;
    }

    /**
     * Helper that loads all reviews for a given user and maps each row to a Map.
     *
     * - Joins with the games table to include the game's display name.
     * - Preserves expected response keys used by the controller layer.
     *
     * @param userId id of the user whose reviews to fetch
     * @return list of maps representing reviews (empty list if none)
     */
    private List<Map<String, Object>> getUserReviews(int userId) {
        List<Map<String, Object>> reviews = new ArrayList<>();

        String sql = "SELECT r.ReviewID, r.GameID, r.HearingRating, " +
                "r.VisionRating, r.CognitiveRating, r.SpeechRating, " +
                "r.MotorRating, r.GameplayRating, r.ReviewText, r.ReviewDate, " +
                "r.Flagged, r.Removed, g.GameName " +
                "FROM reviews r " +
                "JOIN games g ON r.GameID = g.GameID " +
                "WHERE r.UserID = ? " +
                "ORDER BY r.ReviewDate DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> review = new HashMap<>();
                    review.put("id", rs.getInt("ReviewID"));
                    review.put("gameID", rs.getInt("GameID"));
                    review.put("game", rs.getString("GameName"));
                    review.put("hearingRating", rs.getInt("HearingRating"));
                    review.put("visionRating", rs.getInt("VisionRating"));
                    review.put("cognitiveRating", rs.getInt("CognitiveRating"));
                    review.put("speechRating", rs.getInt("SpeechRating"));
                    review.put("motorRating", rs.getInt("MotorRating"));
                    review.put("gamePlayRating", rs.getInt("GameplayRating"));
                    review.put("reviewText", rs.getString("ReviewText"));
                    review.put("reviewDate", rs.getDate("ReviewDate").toString());
                    review.put("flagged", rs.getBoolean("Flagged"));
                    review.put("removed", rs.getBoolean("Removed"));
                    reviews.add(review);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch reviews for userId: " + userId, e);
        }

        return reviews;
    }


    /**
     * Disable (ban) a user by setting Enabled = FALSE.
     *
     * - Returns true when a row was updated, false when the user does not exist.
     *
     * @param userId id of the user to ban
     * @return true if the update affected a row, false otherwise
     */
    @Transactional
    public boolean banUser(int userId) {
        String sql = "UPDATE users SET Enabled = FALSE WHERE UserID = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to ban user with userId: " + userId, e);
        }
    }
}