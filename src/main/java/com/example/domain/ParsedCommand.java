package com.example.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import java.util.Objects;

// An immutable value object for voice commands that have been parsed and are ready for execution.
// Carries command parameters (target) extracted from user speech
// Enables clean separation between parsing logic and execution logic

@Introspected // compile-time checks, so that the properties/methods are accessible.
public class ParsedCommand {
    private final CommandType type;
    private final String target;

    public ParsedCommand(CommandType type, @Nullable String target) {
        this.type = type;
        this.target = target;
    }

    // Type is never null (set in enum CommandType)
    // Returns the command type
    public CommandType getType() {
        return type;
    }

    // Target is nullable to support commands that don't need parameters
    // Returns the target (String) of the command e.g "go to home" for "home"
    @Nullable
    public String getTarget() {
        return target;
    }


    // withTarget() - creates a new ParsedCommand with a different target
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
