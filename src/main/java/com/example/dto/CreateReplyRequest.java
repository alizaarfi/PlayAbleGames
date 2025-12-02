package com.example.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CreateReplyRequest {
    private String replyText;

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }
}
