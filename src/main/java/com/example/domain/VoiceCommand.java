package com.example.domain;

// Domain layer handles business logic

import java.time.LocalDateTime;

//Voice Command entities
// Speech recognition domain models
// Business logic - what happens when '' is recognised
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;

@Serdeable
public class VoiceCommand {
    private String id;
    private String transcribedText;
    private String command;
    private double confidence;
    private Instant timestamp;
    private boolean processed;

    public VoiceCommand() {
        this.timestamp = Instant.now();
    }

    public VoiceCommand(String transcribedText) {
        this();
        this.transcribedText = transcribedText;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTranscribedText() { return transcribedText; }
    public void setTranscribedText(String transcribedText) { this.transcribedText = transcribedText; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}