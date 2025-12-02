package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import com.example.domain.Blog;
import com.example.repository.BlogRepository;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@Controller("/api/blogs")
public class BlogController {

    @Inject
    private BlogRepository blogRepository;

    @Get
    public HttpResponse<List<Blog>> getAllBlogs() {
        return HttpResponse.ok(blogRepository.findAllOrderByDateCreatedDesc());
    }

    @Get("/{id}")
    public HttpResponse<Blog> getBlog(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}