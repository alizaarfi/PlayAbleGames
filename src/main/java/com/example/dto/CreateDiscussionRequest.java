package com.example.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CreateDiscussionRequest {
    private String discussionTitle;
    private String discussionText;

    public String getDiscussionTitle() { return discussionTitle; }
    public void setDiscussionTitle(String discussionTitle) { this.discussionTitle = discussionTitle; }

    public String getDiscussionText() { return discussionText; }
    public void setDiscussionText(String discussionText) { this.discussionText = discussionText; }
}