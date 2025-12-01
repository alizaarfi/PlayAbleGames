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

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @MappedProperty("UserID")
    private User user;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @MappedProperty("DiscussionID")
    private Discussion discussion;

    @MappedProperty("ReplyText")
    private String replyText;

    @MappedProperty("DateCreated")
    private LocalDateTime dateCreated;

    // Constructors
    public Reply() {}

    // Getters and Setters
    public Long getReplyID() { return replyID; }
    public void setReplyID(Long replyID) { this.replyID = replyID; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Discussion getDiscussion() { return discussion; }
    public void setDiscussion(Discussion discussion) { this.discussion = discussion; }

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}