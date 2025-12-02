package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDateTime;

@MappedEntity("replies")
@Serdeable
public class Reply {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("ReplyID")
    private Long replyID;

    @MappedProperty("UserID")
    private Long userID;

    @MappedProperty("DiscussionID")
    private Long discussionID;

    @MappedProperty("ReplyText")
    private String replyText;

    @MappedProperty("DateCreated")
    private LocalDateTime dateCreated;

    // Transient field for username (populated by query join)
    @Transient
    private String username;

    // Constructors
    public Reply() {}

    // Getters and Setters
    public Long getReplyID() { return replyID; }
    public void setReplyID(Long replyID) { this.replyID = replyID; }

    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public Long getDiscussionID() { return discussionID; }
    public void setDiscussionID(Long discussionID) { this.discussionID = discussionID; }

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}