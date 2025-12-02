package com.example.repository;

import com.example.domain.GalleryImage;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests
 */

@MicronautTest(startApplication = false)
class GalleryRepositoryTest {

    @Inject
    GalleryRepository galleryRepository;

    @Test
    void findByGameId_returnsImagesForMinecraft() {
        // --- act ---
        List<GalleryImage> images = galleryRepository.findByGameId(1L);

        // --- assert: images from data.sql are available ---
        assertFalse(images.isEmpty());
        assertEquals(1L, images.get(0).getGameId());
        assertNotNull(images.get(0).getImageUrl());
    }
}
