package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDateTime;

@MappedEntity("discussions")
@Serdeable
public class Discussion {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("DiscussionID")
    private Long discussionID;

    @MappedProperty("UserID")
    private Long userID;

    @MappedProperty("DiscussionTitle")
    private String discussionTitle;

    @MappedProperty("DateCreated")
    private LocalDateTime dateCreated;

    @MappedProperty("DiscussionText")
    private String discussionText;

    // Transient field for username to get it to come onto UI after join query
    @Transient
    private String username;

    public Discussion() {}

    // Getters and Setters
    public Long getDiscussionID() { return discussionID; }
    public void setDiscussionID(Long discussionID) { this.discussionID = discussionID; }

    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public String getDiscussionTitle() { return discussionTitle; }
    public void setDiscussionTitle(String discussionTitle) { this.discussionTitle = discussionTitle; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public String getDiscussionText() { return discussionText; }
    public void setDiscussionText(String discussionText) { this.discussionText = discussionText; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}