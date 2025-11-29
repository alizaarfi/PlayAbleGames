package com.example.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.Objects;


/**
 * ParsedCommand value object defines necessary info taken from captured audio
 * Immutable as each audio chunk turned ParsedCommand is unique (timestamp)
 * Carries command parameters (target) extracted from user speech
 * Enables clean separation between parsing logic and execution logic
 */
@Introspected // compile-time checks, so that the properties/methods are accessible.
@Serdeable
public class ParsedCommand {
    private final CommandType type;
    private final String target;
    private final String transcribedText;
    private final double confidence;
    private final Instant timestamp;

    // Target is nullable to support commands that don't need parameters
    public ParsedCommand( CommandType type, @Nullable String target, String transcribedText, double confidence, Instant timestamp) {
        this.type = type;
        this.target = target;
        this.transcribedText = transcribedText;
        this.confidence = confidence;
        this.timestamp = timestamp;
    }

//    // Comparing commands
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ParsedCommand that = (ParsedCommand) o;
//        return type == that.type && Objects.equals(target, that.target);
//    }
//    @Override
//    public String toString() {
//        return "ParsedCommand{type=" + type + ", target='" + target + "'}";
//    }

    // ----------- Getters -----------
    // No setters, they need to stay immutable
    public CommandType getType() {
        return type;
    }

    @Nullable
    public String getTarget() {
        return target;
    }

    public String getTranscribedText() {return transcribedText;}

    public double getConfidence() {return confidence;}

    public Instant getTimestamp() {return timestamp;}

}
