package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;   // 👈 add this import

@Serdeable
@MappedEntity("gallery")
public class GalleryImage {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("ImageID")
    private Long id;

    @MappedProperty("GameID")
    private Long gameId;

    @MappedProperty("ImageURL")
    private String imageUrl;

    @MappedProperty("AltText")
    private String altText;

    // -------------------------------------------------------------------------
    // Getters and setters — required for Micronaut Data’s SQL mapping process
    // -------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    // -------------------------------------------------------------------------
    // Setter for the primary key.
    // Required so Micronaut Data can populate the ID when reading from SQL.
    // -------------------------------------------------------------------------
    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }
}
