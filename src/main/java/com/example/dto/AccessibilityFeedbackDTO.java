package com.example.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class AccessibilityFeedbackDTO {
    private String name;
    private String email;
    private String concernType;
    private String message;

    public AccessibilityFeedbackDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getConcernType() { return concernType; }
    public void setConcernType(String concernType) { this.concernType = concernType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}