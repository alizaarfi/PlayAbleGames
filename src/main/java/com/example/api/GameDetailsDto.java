package com.example.api;

import com.example.domain.Game;
import com.example.domain.GalleryImage;
import com.example.domain.Review;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;


//-------------------------------------------------------------------------------
//Data transfer object that aggregates game details, accessibility information,
//average ratings, reviews, and gallery images for use by the API.
//-------------------------------------------------------------------------------
@Serdeable
public record GameDetailsDto(
        Long id,
        String name,
        String developer,
        String ageRating,
        String linkToStore,
        String iconUrl,
        String description,
        String releaseDate,
        String versionNumber,
        String aboutVision,
        String aboutHearing,
        String aboutCognitive,
        String aboutSpeech,
        String aboutMotor,
        Double avgVisualRating,
        Double avgHearingRating,
        Double avgCognitiveRating,
        Double avgSpeechRating,
        Double avgMotorRating,
        Double avgGameplayRating,
        List<Review> reviews,
        List<GalleryImage> gallery
) {
    // ---------------------------------------------------------
    // Factory method to build the DTO and compute average ratings
    // ---------------------------------------------------------

    public static GameDetailsDto from(Game game,
                                      List<Review> reviews,
                                      List<GalleryImage> gallery) {

        int count = reviews.size();

        double avgVisual    = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getVisionRating())).average().orElse(0);
        double avgHearing   = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getHearingRating())).average().orElse(0);
        double avgCognitive = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getCognitiveRating())).average().orElse(0);
        double avgSpeech    = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getSpeechRating())).average().orElse(0);
        double avgMotor     = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getMotorRating())).average().orElse(0);
        double avgGameplay  = count == 0 ? 0 : reviews.stream().mapToInt(r -> n(r.getGameplayRating())).average().orElse(0);

        return new GameDetailsDto(
                game.getId(),
                game.getName(),
                game.getDeveloper(),
                game.getAgeRating(),
                game.getLinkToStore(),
                game.getGameIconUrl(),
                game.getGameDescription(),
                game.getReleaseDate() == null ? null : game.getReleaseDate().toString(),
                game.getVersionNumber(),
                game.getAboutVision(),
                game.getAboutHearing(),
                game.getAboutCognitive(),
                game.getAboutSpeech(),
                game.getAboutMotor(),
                avgVisual,
                avgHearing,
                avgCognitive,
                avgSpeech,
                avgMotor,
                avgGameplay,
                reviews,
                gallery
        );
    }

    // ---------------------------------------------------------
    // Convert null Integer to zero for rating averages
    // ---------------------------------------------------------
    private static int n(Integer v) {
        return v == null ? 0 : v;
    }
}
