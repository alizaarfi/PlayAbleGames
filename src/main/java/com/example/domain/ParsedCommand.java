package com.example.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

import java.util.Objects;


// a value object for when parsed voice commands are ready
@Introspected
public class ParsedCommand {
    private final CommandType type;
    private final String target;

    public ParsedCommand(CommandType type, @Nullable String target) {
        this.type = type;
        this.target = target;
    }

    public CommandType getType() {
        return type;
    }

    @Nullable
    public String getTarget() {
        return target;
    }

    /**
     * Create a new ParsedCommand with a different target
     */
    public ParsedCommand withTarget(String target) {
        return new ParsedCommand(this.type, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedCommand that = (ParsedCommand) o;
        return type == that.type && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, target);
    }

    @Override
    public String toString() {
        return "ParsedCommand{type=" + type + ", target='" + target + "'}";
    }
}
