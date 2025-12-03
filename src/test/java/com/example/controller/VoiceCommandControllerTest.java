package com.example.controller;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import com.example.service.VoiceCommandService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * VoiceCommandControllerTest
 * Tests:
 * - Unit tests: Test controller logic in isolation with a mocked service
 * - Integration tests: Test HTTP endpoints with actual requests
 * - Edge cases: Empty data, null values, malformed input
 * - Error handling: Exception scenarios and error responses
 * Architecture & Design Patterns:
 * - Nested test classes for logical grouping
 * - Mock objects to isolate controller from service layer
 */
@MicronautTest
@DisplayName("VoiceCommandController Tests")
class VoiceCommandControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    VoiceCommandService voiceCommandService;

    /**
     * Creates a mock bean for VoiceCommandService
     */
    @MockBean(VoiceCommandService.class)
    VoiceCommandService voiceCommandService() {
        return Mockito.mock(VoiceCommandService.class);

    }

    // Creates mock audio data must be a byte
    private byte[] createMockAudioData(int size) {
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) (Math.random() * 256 - 128);
        }
        return data;
    }

    //ProcessAudio tests
    @Nested
    @DisplayName("POST /api/v1/transcription/process - Audio Processing")
    class ProcessAudioTests {

        @Test
        @DisplayName("Should return valid ParsedCommand for valid audio with speech")
        void testProcessAudio_ValidSpeech_ReturnsCommand() {
            // Arrange
            byte[] validAudioData = createMockAudioData(1000);
            ParsedCommand expectedCommand = new ParsedCommand(
                    CommandType.NAVIGATE,
                    "accessibility",
                    "go to accessibility",
                    0.95,
                    Instant.now()
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(expectedCommand);

            // Act
            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", validAudioData)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            // Assert
            assertEquals(HttpStatus.OK, response.getStatus());
            assertNotNull(response.body());
            ParsedCommand actualCommand = response.body();
            assertEquals(CommandType.NAVIGATE, actualCommand.getType());
            assertEquals("accessibility", actualCommand.getTarget());
            assertEquals("go to accessibility", actualCommand.getTranscribedText());
            assertTrue(actualCommand.getConfidence() > 0.9);

            verify(voiceCommandService, times(1)).processAudio(any(byte[].class));
        }

        @Test
        @DisplayName("Should return NO_SPEECH for audio with no detectable speech")
        void testProcessAudio_NoSpeech_ReturnsNoSpeechCommand() {
            byte[] audioData = createMockAudioData(500);
            ParsedCommand noSpeechCommand = new ParsedCommand(
                    CommandType.NO_SPEECH,
                    null,
                    "",
                    0.0,
                    Instant.now()
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(noSpeechCommand);

            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", audioData)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, response.getStatus());
            assertNotNull(response.body());
            assertEquals(CommandType.NO_SPEECH, response.body().getType());
            assertNull(response.body().getTarget());
            String transcribedText = response.body().getTranscribedText();
            assertTrue(transcribedText == null || transcribedText.isEmpty());
        }

        @Test
        @DisplayName("Should return NO_SPEECH for very short transcription")
        void testProcessAudio_ShortTranscription_ReturnsNoSpeech() {
            byte[] audioData = createMockAudioData(200);
            ParsedCommand shortTextCommand = new ParsedCommand(
                    CommandType.UNKNOWN,
                    null,
                    "a", // Only 1 character
                    0.5,
                    Instant.now()
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(shortTextCommand);

            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", audioData)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, response.getStatus());
            assertEquals(CommandType.NO_SPEECH, response.body().getType());
        }

        @Test
        @DisplayName("Should return BAD_REQUEST for null audio data")
        void testProcessAudio_NullAudio_ReturnsBadRequest() {

            assertThrows(HttpClientResponseException.class, () -> {
                client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/process", null)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM),
                        String.class
                );
            });
            verify(voiceCommandService, never()).processAudio(any());
        }

        @Test
        @DisplayName("Should return BAD_REQUEST for empty audio data")
        void testProcessAudio_EmptyAudio_ReturnsBadRequest() {
            byte[] emptyAudio = new byte[0];

            try {
                // modified to avoid specifying a body type because Micronaut
                // will rerun it to the controller and trigger the service mocks so it was causing issues.
                client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/process", emptyAudio)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                );
                fail("Expected BAD_REQUEST");
            } catch (HttpClientResponseException e) {
                assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            }

            verify(voiceCommandService, never()).processAudio(any());
        }

        @Test
        @DisplayName("Should handle service exceptions gracefully")
        void testProcessAudio_ServiceException_ReturnsServerError() {
            byte[] audioData = createMockAudioData(1000);
            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenThrow(new RuntimeException("Vosk model error"));

            try {
                HttpResponse<?> response = client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/process", audioData)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM),
                        String.class
                );
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
            } catch (HttpClientResponseException e) {
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatus());
            }

            verify(voiceCommandService, times(1)).processAudio(any(byte[].class));
        }

        @Test
        @DisplayName("Should handle different command types correctly")
        void testProcessAudio_DifferentCommandTypes() {
            // Test SEARCH command
            byte[] searchAudio = createMockAudioData(800);
            ParsedCommand searchCommand = new ParsedCommand(
                    CommandType.SEARCH,
                    "minecraft",
                    "search for minecraft",
                    0.88,
                    Instant.now()
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(searchCommand);

            HttpResponse<ParsedCommand> searchResponse = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", searchAudio)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, searchResponse.getStatus());
            assertEquals(CommandType.SEARCH, searchResponse.body().getType());
            assertEquals("minecraft", searchResponse.body().getTarget());

            // Test FILTER command
            byte[] filterAudio = createMockAudioData(800);
            ParsedCommand filterCommand = new ParsedCommand(
                    CommandType.FILTER,
                    "vision support",
                    "filter by vision support",
                    0.92,
                    Instant.now()
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(filterCommand);

            HttpResponse<ParsedCommand> filterResponse = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", filterAudio)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, filterResponse.getStatus());
            assertEquals(CommandType.FILTER, filterResponse.body().getType());
            assertEquals("vision support", filterResponse.body().getTarget());
        }

        @Test
        @DisplayName("Should preserve timestamp in response")
        void testProcessAudio_PreservesTimestamp() {
            byte[] audioData = createMockAudioData(1000);
            Instant expectedTimestamp = Instant.now();
            ParsedCommand command = new ParsedCommand(
                    CommandType.SCROLL,
                    "down",
                    "scroll down",
                    0.85,
                    expectedTimestamp
            );

            when(voiceCommandService.processAudio(any(byte[].class)))
                    .thenReturn(command);

            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", audioData)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, response.getStatus());
            assertNotNull(response.body().getTimestamp());
        }
    }

}