package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import com.example.domain.FAQ;
import com.example.repository.FAQRepository;

import jakarta.inject.Inject;
import java.util.List;

@Controller("/api/faq")
public class FAQController {

    @Inject
    private FAQRepository faqRepository;

    @Get
    public HttpResponse<List<FAQ>> getAllFAQs() {
        List<FAQ> faqs = (List<FAQ>) faqRepository.findAll();
        return HttpResponse.ok(faqs);
    }
}