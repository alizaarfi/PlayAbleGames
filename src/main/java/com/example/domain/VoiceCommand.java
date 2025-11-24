package com.example.domain;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;


// Voice Command Entity - complete record of a voice command interaction
// Business logic - what happens when '' is recognised
// Mainly if we need to store in DB but also debugging at the moment
@Serdeable // serialisation without runtime reflection needed for JSON serialization
public class VoiceCommand {
    private String id;
    private String transcribedText; // since user's speech, comply with GDPR if storing in db
    private String command;
    private String commandTarget;
    private double confidence; // for recognition from Vosk
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


    // The parsed command type as a string e.g NAVIGATE
    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public String getCommandTarget() { return commandTarget; }
    public void setCommandTarget(String commandTarget) {
        this.commandTarget = commandTarget;
    }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}