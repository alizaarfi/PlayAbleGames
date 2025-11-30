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

    // ========== NAVIGATE COMMAND TESTS ==========

    @Nested
    @DisplayName("NAVIGATE Command Parsing")
    class NavigateCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize accessibility navigation commands")
        @ValueSource(strings = {
                "go to accessibility",
                "navigate to accessibility",
                "open accessibility",
                "accessibility",
                "go to access ability", // Common misheard
                "navigate to access lousy", // Common Vosk error
                "open accessability" // Common misspelling
        })
        void testNavigateToAccessibility(String input) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("accessibility", command.getTarget());
            assertEquals(input, command.getTranscribedText());
        }

        @ParameterizedTest
        @DisplayName("Should recognize home navigation commands")
        @ValueSource(strings = {
                "go to home",
                "navigate to home",
                "home",
                "homepage",
                "go to homepage"
        })
        void testNavigateToHome(String input) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("home", command.getTarget());
        }

        @ParameterizedTest
        @DisplayName("Should recognize settings navigation")
        @ValueSource(strings = {
                "go to settings",
                "navigate to settings",
                "open settings",
                "settings"
        })
        void testNavigateToSettings(String input) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("settings", command.getTarget());
        }
    }

    // ========== SEARCH COMMAND TESTS ==========

    @Nested
    @DisplayName("SEARCH Command Parsing")
    class SearchCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize search commands with various phrases")
        @CsvSource({
                "'search minecraft', 'minecraft'",
                "'search for minecraft', 'minecraft'",
                "'search for pokemon go', 'pokemon go'",
                "'search call of duty', 'call of duty'"
        })
        void testSearchCommands(String input, String expectedTarget) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.SEARCH, command.getType());
            assertNotNull(command.getTarget());
            assertTrue(command.getTarget().contains(expectedTarget.split(" ")[0]));
        }

        @Test
        @DisplayName("Should handle multi-word search queries")
        void testMultiWordSearch() {
            // Arrange
            String input = "search for adventure games with vision support";

            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.SEARCH, command.getType());
            assertNotNull(command.getTarget());
            assertFalse(command.getTarget().isEmpty());
        }
    }

    // ========== SCROLL COMMAND TESTS ==========

    @Nested
    @DisplayName("SCROLL Command Parsing")
    class ScrollCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize all scroll directions")
        @CsvSource({
                "'scroll up', 'up'",
                "'scroll down', 'down'",
                "'scroll top', 'top'",
                "'scroll bottom', 'bottom'"
        })
        void testScrollCommands(String input, String expectedDirection) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.SCROLL, command.getType());
            assertEquals(expectedDirection, command.getTarget());
        }
    }

    // ========== FILTER COMMAND TESTS ==========

    @Nested
    @DisplayName("FILTER Command Parsing")
    class FilterCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize genre filters")
        @CsvSource({
                "'filter puzzle', 'puzzle'",
                "'filter by action', 'action'",
                "'filter adventure', 'adventure'",
                "'filter strategy', 'strategy'",
                "'filter casual', 'casual'"
        })
        void testGenreFilters(String input, String expectedGenre) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
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
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.FILTER, command.getType());
            assertEquals("puzzle", command.getTarget());
        }
    }

    // ========== SORT COMMAND TESTS ==========

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
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
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
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.SORT, command.getType());
            assertEquals("rating", command.getTarget());
        }
    }

    // ========== FAVOURITE COMMAND TESTS ==========

    @Nested
    @DisplayName("FAVOURITE Command Parsing")
    class FavouriteCommandTests {

        @ParameterizedTest
        @DisplayName("Should recognize game names with fuzzy matching")
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

    // ========== CLICK COMMAND TESTS ==========

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
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.CLICK, command.getType());
            assertEquals(expectedTarget, command.getTarget());
        }
    }

    // ========== UNKNOWN COMMAND TESTS ==========

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
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.UNKNOWN, command.getType());
            assertNull(command.getTarget());
            assertEquals(input, command.getTranscribedText());
        }

        @Test
        @DisplayName("Should handle empty string")
        void testEmptyString() {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand("");

            // Assert
            assertEquals(CommandType.UNKNOWN, command.getType());
        }

        @Test
        @DisplayName("Should handle whitespace-only input")
        void testWhitespaceOnly() {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand("   ");

            // Assert
            assertEquals(CommandType.UNKNOWN, command.getType());
        }
    }

    // ========== CASE SENSITIVITY TESTS ==========

    @Nested
    @DisplayName("Case Sensitivity Tests")
    class CaseSensitivityTests {

        @ParameterizedTest
        @DisplayName("Should be case-insensitive")
        @ValueSource(strings = {
                "GO TO ACCESSIBILITY",
                "go to accessibility",
                "Go To Accessibility",
                "gO tO aCcEsSiBiLiTy"
        })
        void testCaseInsensitivity(String input) {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand(input);

            // Assert
            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("accessibility", command.getTarget());
        }
    }

    // ========== TIMESTAMP TESTS ==========

    @Nested
    @DisplayName("Timestamp Tests")
    class TimestampTests {

        @Test
        @DisplayName("Should include timestamp in ParsedCommand")
        void testTimestampPresent() {
            // Act
            ParsedCommand command = voiceCommandService.parseCommand("go to home");

            // Assert
            assertNotNull(command.getTimestamp());
        }

        @Test
        @DisplayName("Should have recent timestamp")
        void testTimestampRecency() {
            // Arrange
            long beforeTime = System.currentTimeMillis();

            // Act
            ParsedCommand command = voiceCommandService.parseCommand("search minecraft");
            long afterTime = System.currentTimeMillis();

            // Assert
            long commandTime = command.getTimestamp().toEpochMilli();
            assertTrue(commandTime >= beforeTime && commandTime <= afterTime);
        }
    }

    // ========== TRANSCRIPTION PRESERVATION TESTS ==========

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