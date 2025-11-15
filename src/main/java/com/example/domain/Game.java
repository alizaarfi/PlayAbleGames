package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

import java.time.LocalDate;

@MappedEntity("games")
public class Game {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("GameID")
    private Long id;

    @MappedProperty("GameName")
    private String name;

    @MappedProperty("Developer")
    private String developer;

    @MappedProperty("AgeRating")
    private String ageRating;

    @MappedProperty("LinkToStore")
    private String linkToStore;

    @MappedProperty("GameIconURL")
    private String gameIconUrl;

    @MappedProperty("GameDescription")
    private String gameDescription;

    @MappedProperty("ReleaseDate")
    private LocalDate releaseDate;

    @MappedProperty("VersionNumber")
    private String versionNumber;

    @MappedProperty("AboutVision")
    private String aboutVision;

    @MappedProperty("AboutHearing")
    private String aboutHearing;

    @MappedProperty("AboutCognitive")
    private String aboutCognitive;

    @MappedProperty("AboutSpeech")
    private String aboutSpeech;

    @MappedProperty("AboutMotor")
    private String aboutMotor;



    // -------------------------------------------------------------------------
    // Getters and setters — required by Micronaut Data for persistence mapping
    // -------------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getLinkToStore() {
        return linkToStore;
    }

    public void setLinkToStore(String linkToStore) {
        this.linkToStore = linkToStore;
    }

    public String getGameIconUrl() {
        return gameIconUrl;
    }

    public void setGameIconUrl(String gameIconUrl) {
        this.gameIconUrl = gameIconUrl;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getAboutVision() {
        return aboutVision;
    }

    public void setAboutVision(String aboutVision) {
        this.aboutVision = aboutVision;
    }

    public String getAboutHearing() {
        return aboutHearing;
    }

    public void setAboutHearing(String aboutHearing) {
        this.aboutHearing = aboutHearing;
    }

    public String getAboutCognitive() {
        return aboutCognitive;
    }

    public void setAboutCognitive(String aboutCognitive) {
        this.aboutCognitive = aboutCognitive;
    }

    public String getAboutSpeech() {
        return aboutSpeech;
    }

    public void setAboutSpeech(String aboutSpeech) {
        this.aboutSpeech = aboutSpeech;
    }
    public String getAboutMotor() { return aboutMotor; }
    public void setAboutMotor(String aboutMotor) { this.aboutMotor = aboutMotor; }

}

