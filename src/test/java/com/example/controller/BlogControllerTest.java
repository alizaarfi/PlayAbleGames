package com.example.controller;

import com.example.domain.Blog;
import com.example.repository.BlogRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BlogControllerTest tests the controllers REST endpoints, following similar structure
 * to DiscussionController and FAQController.
 */

@MicronautTest
class BlogControllerTest {

    @Inject
    private BlogController blogController;

    @Inject
    private BlogRepository blogRepository;

    @BeforeEach
    void setup() {
        blogRepository.deleteAll();
    }

    @Test
    void testGetAllBlogs() throws InterruptedException {
        Blog blog1 = new Blog();
        blog1.setBlogTitle("Blog1: New ps4 accessibility features");
        blog1.setDateCreated(LocalDateTime.now());
        blog1.setLinkToBlog("https://example.com/blog");
        blog1.setBlogDescription("2026 PS4 accessibility features");
        blog1.setReadTime(5);
        blogRepository.save(blog1);

        // Ensure different timestamp
        Thread.sleep(10);
        Blog blog2 = new Blog();
        blog2.setBlogTitle("Blog2: xbox future haptic feedback");
        blog2.setDateCreated(LocalDateTime.now());
        blog2.setLinkToBlog("https://example.com/blog");
        blog2.setBlogDescription("New xbox console controllers haptic feedback features");
        blog2.setReadTime(5);
        blogRepository.save(blog2);

        HttpResponse<List<Blog>> response = blogController.getAllBlogs();

        assertEquals(200, response.getStatus().getCode());
        List<Blog> blogs = response.body();
        assertNotNull(blogs);
        assertEquals(2, blogs.size());
        assertEquals("Blog1: New ps4 accessibility features", blogs.get(0).getBlogTitle());
        assertEquals("Blog2: xbox future haptic feedback", blogs.get(1).getBlogTitle());
    }

    @Test
    void testGetBlogById() {
        Blog blog = new Blog();
        blog.setBlogTitle("Single Blog");
        blog.setDateCreated(LocalDateTime.now());
        blog.setLinkToBlog("https://example.com/blog");
        blog.setBlogDescription("2026 accessibility features");
        blog.setReadTime(5);
        Blog saved = blogRepository.save(blog);

        // Do for ValidID
        HttpResponse<Blog> response = blogController.getBlog(saved.getBlogId());
        assertEquals(200, response.getStatus().getCode());
        assertEquals("Single Blog", response.body().getBlogTitle());

        // Do for Invalid ID
        HttpResponse<Blog> notFound = blogController.getBlog(999L);
        assertEquals(404, notFound.getStatus().getCode());
    }
}
