package com.example.service;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for VoiceCommandService
 *
 * Testing Strategy:
 * - Command parsing: Test all command types and their variations
 * - Fuzzy matching: Test common misheard phrases
 * - Edge cases: Test boundary conditions and unusual inputs
 * - Target assignment: Test correct target extraction for each command type
 *
 * Note: Audio processing tests require a valid Vosk model
 * These tests focus on the parseCommand method which doesn't require audio
 */
@MicronautTest
@DisplayName("VoiceCommandService Tests")
class VoiceCommandServiceTest {

    @Inject
    VoiceCommandService voiceCommandService;

    @Inject
    ObjectMapper objectMapper;

    // CommandType NAVIGATE Tests
    @Nested
    @DisplayName("NAVIGATE Command Parsing")
    class NavigateCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognise accessibility navigation commands")
        @ValueSource(strings = {
                "go to accessibility",
                "navigate to accessibility",
                "open accessibility",
                "accessibility",
                "go to access ability", // Commonlu misheard in my logs
                "navigate to access lousy",
                "open accessability"})
        void testNavigateToAccessibility(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("accessibility", command.getTarget());
            assertEquals(input, command.getTranscribedText());
        }

        @ParameterizedTest
        @DisplayName("Should recognise home navigation commands")
        @ValueSource(strings = {
                "go to home",
                "navigate to home",
                "home",
                "homepage",
                "go to homepage"
        })
        void testNavigateToHome(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("home", command.getTarget());
        }

        @ParameterizedTest
        @DisplayName("Should recognise settings navigation")
        @ValueSource(strings = {
                "go to settings",
                "navigate to settings",
                "open settings",
                "settings"
        })
        void testNavigateToSettings(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("settings", command.getTarget());
        }
    }


    // CommandType SEARCH tests
    @Nested
    @DisplayName("SEARCH Command Parsing")
    class SearchCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognise search commands with various phrases")
        @CsvSource({
                "'search minecraft', 'minecraft'",
                "'search for minecraft', 'minecraft'",
                "'search for pokemon go', 'pokemon go'",
                "'search call of duty', 'call of duty'"
        })
        void testSearchCommands(String input, String expectedTarget) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.SEARCH, command.getType());
            assertNotNull(command.getTarget());
            assertTrue(command.getTarget().contains(expectedTarget.split(" ")[0]));
        }
    }


    // Scroll tests
    @Nested
    @DisplayName("SCROLL Command Parsing")
    class ScrollCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognise all scroll directions")
        @CsvSource({
                "'scroll up', 'up'",
                "'scroll down', 'down'",
                "'scroll top', 'top'",
                "'scroll bottom', 'bottom'"
        })
        void testScrollCommands(String input, String expectedDirection) {
            ParsedCommand command = voiceCommandService.parseCommand(input);
            assertEquals(CommandType.SCROLL, command.getType());
            assertEquals(expectedDirection, command.getTarget());
        }
    }

    // Filter tests
    @Nested
    @DisplayName("FILTER Command Parsing")
    class FilterCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognise genre filters")
        @CsvSource({
                "'filter puzzle', 'puzzle'",
                "'filter by action', 'action'",
                "'filter adventure', 'adventure'",
                "'filter strategy', 'strategy'",
                "'filter casual', 'casual'"
        })
        void testGenreFilters(String input, String expectedGenre) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.FILTER, command.getType());
            assertNotNull(command.getTarget());
        }

        @ParameterizedTest
        @DisplayName("Should handle fuzzy matching for puzzle variations")
        @ValueSource(strings = {
                "filter puz",
                "filter puzzle",
                "filter by puz"
        })
        void testPuzzleFuzzyMatching(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.FILTER, command.getType());
            assertEquals("puzzle", command.getTarget());
        }
    }


    // Sort Tests
    @Nested
    @DisplayName("SORT Command Parsing")
    class SortCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize sort commands")
        @CsvSource({
                "'sort by rating', 'rating'",
                "'sort rating', 'rating'",
                "'sort by alphabetical', 'alphabetical'",
                "'sort most reviewed', 'most reviewed'"
        })
        void testSortCommands(String input, String expectedSort) {
            ParsedCommand command = voiceCommandService.parseCommand(input);
            assertEquals(CommandType.SORT, command.getType());
            assertNotNull(command.getTarget());
        }

        @ParameterizedTest
        @DisplayName("Should handle fuzzy matching for rating")
        @ValueSource(strings = {
                "sort rat",
                "sort rating",
                "sort by rat"
        })
        void testRatingFuzzyMatching(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.SORT, command.getType());
            assertEquals("rating", command.getTarget());
        }
    }


    // Favourite Tests
    @Nested
    @DisplayName("FAVOURITE Command Parsing")
    class FavouriteCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognise game names with fuzzy matching")
        @CsvSource({
                "'favourite minecraft', 'minecraft'",
                "'favorite mine craft', 'minecraft'",
                "'favourite monument valley', 'monument valley'",
                "'favorite among us', 'among us'",
                "'favourite amongst us', 'among us'",
                "'favorite pokemon go', 'pokemon go'",
                "'favourite poke mon go', 'pokemon go'",
                "'favorite stardew valley', 'stardew valley'",
                "'favourite star valley', 'stardew valley'"
        })
        void testFavouriteCommands(String input, String expectedGame) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.FAVOURITE, command.getType());
            assertEquals(expectedGame, command.getTarget());
        }

        @Test
        @DisplayName("Should handle Call of Duty variations")
        void testCallOfDutyVariations() {
            // Test "call of duty"
            ParsedCommand command1 = voiceCommandService.parseCommand("favourite call of duty");
            assertEquals(CommandType.FAVOURITE, command1.getType());
            assertEquals("call of duty", command1.getTarget());

            // Test "cod" abbreviation
            ParsedCommand command2 = voiceCommandService.parseCommand("favourite cod");
            assertEquals(CommandType.FAVOURITE, command2.getType());
            assertEquals("call of duty", command2.getTarget());
        }
    }

    // Click commands
    @Nested
    @DisplayName("CLICK Command Parsing")
    class ClickCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize click commands")
        @CsvSource({
                "'click button', 'button'",
                "'click submit', 'submit'",
                "'click next', 'next'"
        })
        void testClickCommands(String input, String expectedTarget) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.CLICK, command.getType());
            assertEquals(expectedTarget, command.getTarget());
        }
    }

    // Unknown tests
    @Nested
    @DisplayName("UNKNOWN Command Handling")
    class UnknownCommandTests {

        @ParameterizedTest
        @DisplayName("Should return UNKNOWN for unrecognized commands")
        @ValueSource(strings = {
                "random gibberish",
                "this is not a command",
                "xyz abc def",
                "play music"
        })
        void testUnknownCommands(String input) {
            ParsedCommand command = voiceCommandService.parseCommand(input);

            assertEquals(CommandType.UNKNOWN, command.getType());
            assertNull(command.getTarget());
            assertEquals(input, command.getTranscribedText());
        }
    }

    // ========== TIMESTAMP TESTS ==========

    @Nested
    @DisplayName("Timestamp Tests")
    class TimestampTests {

        @Test
        @DisplayName("Should include timestamp in ParsedCommand")
        void testTimestampPresent() {
            ParsedCommand command = voiceCommandService.parseCommand("go to home");

            assertNotNull(command.getTimestamp());
        }

        @Test
        @DisplayName("Should have recent timestamp")
        void testTimestampRecency() {
            long beforeTime = System.currentTimeMillis();
            ParsedCommand command = voiceCommandService.parseCommand("search minecraft");
            long afterTime = System.currentTimeMillis();
            long commandTime = command.getTimestamp().toEpochMilli();
            assertTrue(commandTime >= beforeTime && commandTime <= afterTime);
        }
    }


    // Transcription tests
    // Check that after parseCommand, needed for backend logging
    @Nested
    @DisplayName("Transcription Preservation Tests")
    class TranscriptionTests {

        @Test
        @DisplayName("Should preserve original transcription text")
        void testTranscriptionPreserved() {
            // Arrange
            String originalText = "go to accessibility page";

            // Act
            ParsedCommand command = voiceCommandService.parseCommand(originalText);

            // Assert
            assertEquals(originalText, command.getTranscribedText());
        }

        @Test
        @DisplayName("Should preserve transcription even for unknown commands")
        void testTranscriptionPreservedForUnknown() {
            // Arrange
            String unknownText = "this is completely random";

            // Act
            ParsedCommand command = voiceCommandService.parseCommand(unknownText);

            // Assert
            assertEquals(CommandType.UNKNOWN, command.getType());
            assertEquals(unknownText, command.getTranscribedText());
        }
    }
}