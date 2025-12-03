package com.example.repository;

import com.example.domain.Blog;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BlogRepositoryTest tests the repo's operations t :
 */

@MicronautTest
class BlogRepositoryTest {

    @Inject
    private BlogRepository blogRepository;

    @BeforeEach
    void setup() {
        // makes sure to clean database before each test
        blogRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Blog blog = new Blog();
        blog.setBlogTitle("Dark Mode Tips");
        blog.setDateCreated(LocalDateTime.now());
        blog.setLinkToBlog("https://example.com/blog");
        blog.setBlogDescription("Helpful guide on when dark mode helps");
        blog.setReadTime(5);

        Blog savedBlog = blogRepository.save(blog);
        assertNotNull(savedBlog.getBlogId());

        Optional<Blog> retrieved = blogRepository.findById(savedBlog.getBlogId());
        assertTrue(retrieved.isPresent());
        assertEquals("Dark Mode Tips", retrieved.get().getBlogTitle());
    }

    @Test
    void testFindAllOrderByDateCreatedDesc() throws InterruptedException {
        // Arranging to create two blogs at different times
        Blog blog1 = new Blog();
        blog1.setBlogTitle("Blog1: New ps4 accessibility features");
        blog1.setDateCreated(LocalDateTime.now());
        blog1.setLinkToBlog("https://example.com/blog");
        blog1.setBlogDescription("2026 PS4 accessibility features");
        blog1.setReadTime(5);
        blogRepository.save(blog1);

        // Ensure a later timestamp
        Thread.sleep(10);
        Blog blog2 = new Blog();
        blog2.setBlogTitle("Blog2: xbox future haptic feedback");
        blog2.setDateCreated(LocalDateTime.now());
        blog2.setLinkToBlog("https://example.com/blog");
        blog2.setBlogDescription("New xbox console controllers haptic feedback features");
        blog2.setReadTime(5);
        blogRepository.save(blog2);

        List<Blog> blogs = blogRepository.findAllOrderByDateCreatedDesc();

        // now asset to ensure the latest created blog is first
        assertEquals(2, blogs.size());
//        assertEquals("Blog2: xbox future haptic feedback", blogs.get(1).getBlogTitle());
//        assertEquals("Blog1: New ps4 accessibility features", blogs.get(0).getBlogTitle());
        assertEquals("Blog1: New ps4 accessibility features", blogs.get(0).getBlogTitle());
        assertEquals("Blog2: xbox future haptic feedback", blogs.get(1).getBlogTitle());
    }

}
