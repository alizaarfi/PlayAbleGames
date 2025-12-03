package com.example.controller;


import com.example.domain.FAQ;
import com.example.repository.FAQRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class FAQControllerTest {

    @Inject
    private FAQController faqController;

    @Inject
    private FAQRepository faqRepository;

    @BeforeEach
    void setup() {
        faqRepository.deleteAll();
    }

    @Test
    void testGetAllFAQs() {
        // Save FAQs in the DB
        FAQ faq1 = new FAQ();
        faq1.setQuestion("Q1");
        faq1.setAnswer("A1");
        FAQ faq2 = new FAQ();
        faq2.setQuestion("Q2");
        faq2.setAnswer("A2");

        faqRepository.save(faq1);
        faqRepository.save(faq2);

        // Call controller
        HttpResponse<List<FAQ>> response = faqController.getAllFAQs();

        // Assert that the response is correct
        assertEquals(200, response.getStatus().getCode());
        List<FAQ> faqs = response.body();
        assertNotNull(faqs);
        assertEquals(2, faqs.size());
        assertTrue(faqs.stream().anyMatch(f -> "Q1".equals(f.getQuestion())));
        assertTrue(faqs.stream().anyMatch(f -> "Q2".equals(f.getQuestion())));
    }
}