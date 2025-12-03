package com.example.repository;

import com.example.domain.FAQ;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class FAQRepositoryTest {

    @Inject
    private FAQRepository faqRepository;

    @Test
    void testSaveAndFindById() {
        FAQ faq = new FAQ();
        faq.setQuestion("What is haptic feedback?");
        faq.setAnswer("Tech that simulates touch senses through vibrations, motions and forces.");

        // Save to DB
        FAQ savedFaq = faqRepository.save(faq);

        assertNotNull(savedFaq.getFaqID());

        // Retrieve by ID
        Optional<FAQ> retrieved = faqRepository.findById(savedFaq.getFaqID());
        assertTrue(retrieved.isPresent());
        assertEquals("What is Micronaut?", retrieved.get().getQuestion());
        assertEquals("A modern JVM framework.", retrieved.get().getAnswer());
    }

    @Test
    void testFindAllAndDelete() {
        FAQ faq1 = new FAQ();
        faq1.setQuestion("Q1");
        faq1.setAnswer("A1");
        FAQ faq2 = new FAQ();
        faq2.setQuestion("Q2");
        faq2.setAnswer("A2");

        faqRepository.save(faq1);
        faqRepository.save(faq2);

        Iterable<FAQ> faqs = faqRepository.findAll();
        assertTrue(faqs.iterator().hasNext());

        // Delete one
        faqRepository.delete(faq1);
        Optional<FAQ> deleted = faqRepository.findById(faq1.getFaqID());
        assertTrue(deleted.isEmpty());
    }
}
