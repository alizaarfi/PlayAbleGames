package com.example.repository;

import jakarta.inject.Singleton;
import io.micronaut.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Singleton
public class FlaggedReviewsRepository {
    private final DataSource dataSource;

    public FlaggedReviewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
                review.put("UserID", rs.getInt("UserID"));
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