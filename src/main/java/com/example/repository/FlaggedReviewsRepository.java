package com.example.repository;

import jakarta.inject.Singleton;
import io.micronaut.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Repository responsible for retrieving and updating reviews that have been flagged.
 *
 * - Uses constructor injection to obtain a JDBC {@link DataSource}.
 * - Returns simple POJO-like Map objects for easy JSON serialization by the controller layer.
 * - Each public method is annotated with {@link Transactional} so calls execute in a transactional context.
 */
@Singleton
public class FlaggedReviewsRepository {
    private final DataSource dataSource;

    /**
     * Constructor injection: framework supplies a JDBC DataSource.
     *
     * @param dataSource JDBC data source used to open connections.
     */
    public FlaggedReviewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Fetch all flagged reviews with associated user and game information.
     *
     * - Executes a SELECT joining reviews, users and games and filtering on the Flagged flag.
     * - Uses try-with-resources so Connection, PreparedStatement and ResultSet are closed automatically.
     * - Each row is mapped to a Map\<String,Object\> with keys expected by the controller layer.
     *
     * @return list of maps representing flagged reviews (empty list if none)
     */
    @Transactional
    public List<Map<String, Object>> getFlaggedReviews() {
        List<Map<String, Object>> reviews = new ArrayList<>();

        String sql = "SELECT r.ReviewID, r.UserID, r.GameID, r.HearingRating, " +
                "r.VisionRating, r.CognitiveRating, r.SpeechRating, " +
                "r.MotorRating, r.GameplayRating, r.ReviewText, r.ReviewDate, " +
                "u.Username, g.GameName " +
                "FROM reviews r " +
                "JOIN users u ON r.UserID = u.UserID " +
                "JOIN games g ON r.GameID = g.GameID " +
                "WHERE r.Flagged = TRUE " +
                "ORDER BY r.ReviewDate DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> review = new HashMap<>();
                review.put("id", rs.getInt("ReviewID"));
                review.put("userId", rs.getInt("UserID"));
                review.put("user", rs.getString("Username"));
                review.put("game", rs.getString("GameName"));
                review.put("hearingRating", rs.getInt("HearingRating"));
                review.put("visionRating", rs.getInt("VisionRating"));
                review.put("cognitiveRating", rs.getInt("CognitiveRating"));
                review.put("speechRating", rs.getInt("SpeechRating"));
                review.put("motorRating", rs.getInt("MotorRating"));
                review.put("gamePlayRating", rs.getInt("GameplayRating"));
                review.put("reviewText", rs.getString("ReviewText"));
                review.put("reviewDate", rs.getDate("ReviewDate").toString());
                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch flagged reviews", e);
        }

        return reviews;
    }


    /**
     * Accept a flagged review (mark as not flagged and removed).
     *
     * - Updates the review row and returns whether any row was affected.
     * - Uses try-with-resources to ensure JDBC resources are closed.
     *
     * @param reviewId id of the review to accept
     * @return true if the update changed at least one row, false otherwise
     */
    @Transactional
    public boolean acceptReview(int reviewId) {
        String sql = "UPDATE reviews SET Flagged = false, Removed = true WHERE ReviewID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to accept review " + reviewId, e);
        }
    }


    /**
     * Decline a flagged review (clear the flagged flag but do not mark removed).
     *
     * - Returns true when a row was updated, false when the review was not found.
     *
     * @param reviewId id of the review to decline
     * @return true if the update changed at least one row, false otherwise
     */
    @Transactional
    public boolean declineReview(int reviewId) {
        String sql = "UPDATE reviews SET Flagged = false WHERE ReviewID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to decline review " + reviewId, e);
        }
    }
}