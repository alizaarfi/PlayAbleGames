package com.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * FAQTest similar to BlogTest, just tests the entity class behaviour
 */

class FAQTest {

    @Test
    void testFaqGettersAndSetters() {
        FAQ faq = new FAQ();
        faq.setFaqID(1L);
        faq.setQuestion("What is Micronaut?");
        faq.setAnswer("Micronaut is a modern JVM framework.");

        assertEquals(1L, faq.getFaqID());
        assertEquals("What is Micronaut?", faq.getQuestion());
        assertEquals("Micronaut is a modern JVM framework.", faq.getAnswer());
    }

    @Test
    void testFaqConstructorAndDefaultValues() {
        FAQ faq = new FAQ();

        assertNull(faq.getFaqID());
        assertNull(faq.getQuestion());
        assertNull(faq.getAnswer());
    }
}
