package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDateTime;
import java.util.List;

@MappedEntity("discussions")
@Serdeable
public class Discussion {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("DiscussionID")
    private Long discussionID;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @MappedProperty("UserID")
    private User user;

    @MappedProperty("DiscussionTitle")
    private String discussionTitle;

    @MappedProperty("DateCreated")
    private LocalDateTime dateCreated;

    @MappedProperty("DiscussionText")
    private String discussionText;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "discussion")
    private List<Reply> replies;

    // Constructors
    public Discussion() {}

    // Getters and Setters
    public Long getDiscussionID() { return discussionID; }
    public void setDiscussionID(Long discussionID) { this.discussionID = discussionID; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDiscussionTitle() { return discussionTitle; }
    public void setDiscussionTitle(String discussionTitle) { this.discussionTitle = discussionTitle; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public String getDiscussionText() { return discussionText; }
    public void setDiscussionText(String discussionText) { this.discussionText = discussionText; }

    public List<Reply> getReplies() { return replies; }
    public void setReplies(List<Reply> replies) { this.replies = replies; }
}