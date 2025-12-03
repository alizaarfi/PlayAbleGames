package com.example.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BlogTest similar to FAQTest, just tests the entity class behaviour
 */
public class BlogTest {

    @Test
    void testBlogDefaultConstructor() {
        Blog blog = new Blog();

        assertNull(blog.getBlogId());
        assertNull(blog.getBlogTitle());
        assertNull(blog.getDateCreated());
        assertNull(blog.getLinkToBlog());
        assertNull(blog.getBlogDescription());
        assertNull(blog.getReadTime());
    }

    @Test
    void testBlogGettersAndSetters() {
        Blog blog = new Blog();

        blog.setBlogId(1L);
        blog.setBlogTitle("Micronaut Tips");
        LocalDateTime now = LocalDateTime.now();
        blog.setDateCreated(now);
        blog.setLinkToBlog("https://example.com/blog");
        blog.setBlogDescription("A short description");
        blog.setReadTime(5);

        assertEquals(1L, blog.getBlogId());
        assertEquals("Micronaut Tips", blog.getBlogTitle());
        assertEquals(now, blog.getDateCreated());
        assertEquals("https://example.com/blog", blog.getLinkToBlog());
        assertEquals("A short description", blog.getBlogDescription());
        assertEquals(5, blog.getReadTime());
    }

}
