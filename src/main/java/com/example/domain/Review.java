package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

import java.time.LocalDateTime;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("reviews")
public class Review {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("ReviewID")
    private Long id;

    @MappedProperty("UserID")
    private Long userId;

    @MappedProperty("GameID")
    private Long gameId;

    @MappedProperty("HearingRating")
    private Integer hearingRating;

    @MappedProperty("VisualRating")
    private Integer visualRating;

    @MappedProperty("CognitiveRating")
    private Integer cognitiveRating;

    @MappedProperty("SpeechRating")
    private Integer speechRating;

    @MappedProperty("MotorRating")
    private Integer motorRating;

    @MappedProperty("GameplayRating")
    private Integer gameplayRating;

    @MappedProperty("ReviewText")
    private String reviewText;

    @MappedProperty("ReviewDate")
    private LocalDateTime reviewDate;

    @MappedProperty("Flagged")
    private Boolean flagged;

    @MappedProperty("Removed")
    private Boolean removed;

    // -------------------------------------------------------------------------
    // Getters — required for reading mapped data from the database
    // -------------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Integer getHearingRating() {
        return hearingRating;
    }

    public Integer getVisualRating() {
        return visualRating;
    }

    public Integer getCognitiveRating() {
        return cognitiveRating;
    }

    public Integer getSpeechRating() {
        return speechRating;
    }

    public Integer getMotorRating() {
        return motorRating;
    }

    public Integer getGameplayRating() {
        return gameplayRating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public Boolean getRemoved() {
        return removed;
    }

    // -------------------------------------------------------------------------
    // Setters — needed by Micronaut Data for hydration during SQL mapping
    // -------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public void setHearingRating(Integer hearingRating) {
        this.hearingRating = hearingRating;
    }

    public void setVisualRating(Integer visualRating) {
        this.visualRating = visualRating;
    }

    public void setCognitiveRating(Integer cognitiveRating) {
        this.cognitiveRating = cognitiveRating;
    }

    public void setSpeechRating(Integer speechRating) {
        this.speechRating = speechRating;
    }

    public void setMotorRating(Integer motorRating) {
        this.motorRating = motorRating;
    }

    public void setGameplayRating(Integer gameplayRating) {
        this.gameplayRating = gameplayRating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }
}
