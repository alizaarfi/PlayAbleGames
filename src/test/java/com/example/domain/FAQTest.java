package com.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FAQTest {

    @Test
    void testFaqGettersAndSetters() {
        FAQ faq = new FAQ();

        // Set values
        faq.setFaqID(1L);
        faq.setQuestion("What is Micronaut?");
        faq.setAnswer("Micronaut is a modern JVM framework.");

        // Assert values
        assertEquals(1L, faq.getFaqID());
        assertEquals("What is Micronaut?", faq.getQuestion());
        assertEquals("Micronaut is a modern JVM framework.", faq.getAnswer());
    }

    @Test
    void testFaqConstructorAndDefaultValues() {
        FAQ faq = new FAQ();

        // Default values should be null
        assertNull(faq.getFaqID());
        assertNull(faq.getQuestion());
        assertNull(faq.getAnswer());
    }
}
