package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDateTime;

@Serdeable
@MappedEntity("blog")
public class Blog {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty("BlogId")
    private Long blogId;

    @MappedProperty("BlogTitle")
    private String blogTitle;

    @MappedProperty("DateCreated")
    private LocalDateTime dateCreated;

    @MappedProperty("LinkToBlog")
    private String linkToBlog;

    @MappedProperty("BlogDescription")
    private String blogDescription;

    @MappedProperty("ReadTime")
    private Integer readTime;

    // Getters and Setters
    public Long getBlogId() { return blogId; }
    public void setBlogId(Long blogId) { this.blogId = blogId; }

    public String getBlogTitle() { return blogTitle; }
    public void setBlogTitle(String blogTitle) { this.blogTitle = blogTitle; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public String getLinkToBlog() { return linkToBlog; }
    public void setLinkToBlog(String linkToBlog) { this.linkToBlog = linkToBlog; }

    public String getBlogDescription() { return blogDescription; }
    public void setBlogDescription(String blogDescription) { this.blogDescription = blogDescription; }

    public Integer getReadTime() { return readTime; }
    public void setReadTime(Integer readTime) { this.readTime = readTime; }
}