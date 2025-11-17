package com.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies mapping behaviour of the GalleryImage entity.
 */
class GalleryImageTest {

    @Test
    void gettersAndSettersWorkAsExpected() {
        GalleryImage image = new GalleryImage();

        image.setId(5L);
        image.setGameId(1L);
        image.setImageUrl("https://example.com/img.jpg");
        image.setAltText("Alt text");

        assertEquals(5L, image.getId());
        assertEquals(1L, image.getGameId());
        assertEquals("https://example.com/img.jpg", image.getImageUrl());
        assertEquals("Alt text", image.getAltText());
    }
}
