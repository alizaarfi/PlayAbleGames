package com.example;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete voice control feature
 */
@MicronautTest
@DisplayName("Voice Control Integration Tests")
class VoiceControlIntegrationTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Nested
    @DisplayName("Complete Audio-to-Command Workflow")
    class CompleteWorkflowTests {

        @Test
        @DisplayName("Should process complete workflow for text parsing")
        void testCompleteTextWorkflow() {
            String commandText = "go to accessibility";

            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/parse", commandText)
                            .contentType(MediaType.TEXT_PLAIN),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, response.getStatus());
            assertNotNull(response.body());

            ParsedCommand command = response.body();
            assertEquals(CommandType.NAVIGATE, command.getType());
            assertEquals("accessibility", command.getTarget());
            assertEquals(commandText, command.getTranscribedText());
            assertNotNull(command.getTimestamp());
        }

        @Test
        @DisplayName("Should handle multiple sequential requests")
        void testMultipleSequentialRequests() {
            // navigate command
            String nav = "go to home";
            HttpResponse<ParsedCommand> response1 = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/parse", nav)
                            .contentType(MediaType.TEXT_PLAIN),
                    ParsedCommand.class
            );
            assertEquals(HttpStatus.OK, response1.getStatus());
            assertEquals(CommandType.NAVIGATE, response1.body().getType());

            // search command
            String search = "search minecraft";
            HttpResponse<ParsedCommand> response2 = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/parse", search)
                            .contentType(MediaType.TEXT_PLAIN),
                    ParsedCommand.class
            );
            assertEquals(HttpStatus.OK, response2.getStatus());
            assertEquals(CommandType.SEARCH, response2.body().getType());

            // filter command
            String filter = "filter by puzzle";
            HttpResponse<ParsedCommand> response3 = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/parse", filter)
                            .contentType(MediaType.TEXT_PLAIN),
                    ParsedCommand.class
            );
            assertEquals(HttpStatus.OK, response3.getStatus());
            assertEquals(CommandType.FILTER, response3.body().getType());
        }
    }

    // Integration tests for error handling

    @Nested
    @DisplayName("Error Handling Integration")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle invalid content type gracefully")
        void testInvalidContentType() {

            byte[] data = createSimpleWavData();

            try {
                HttpResponse<?> response = client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/process", data)
                                .contentType(MediaType.APPLICATION_JSON), // Wrong type
                        String.class
                );

                // Should either reject or handle gracefully
                assertTrue(response.getStatus().getCode() >= 400);
            } catch (Exception e) {
                // Expected for incorrect content type
                assertTrue(true);
            }
        }

        @Test
        @DisplayName("Should handle malformed requests")
        void testMalformedRequest() {
            try {
                HttpResponse<?> response = client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/process", "not-bytes")
                                .contentType(MediaType.APPLICATION_OCTET_STREAM),
                        String.class
                );

                // If it doesn't throw, should return error status!
                assertTrue(response.getStatus().getCode() >= 400);
            } catch (Exception e) {

                assertTrue(true);
            }
        }
    }

    // Tests for performance of the voice control feature
    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Should process requests within reasonable time")
        void testResponseTime() {
            String commandText = "search for games";
            long maxResponseTime = 1000; // 1 second

            long startTime = System.currentTimeMillis();
            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/parse", commandText)
                            .contentType(MediaType.TEXT_PLAIN),
                    ParsedCommand.class
            );
            long endTime = System.currentTimeMillis();

            assertEquals(HttpStatus.OK, response.getStatus());
            long responseTime = endTime - startTime;
            assertTrue(responseTime < maxResponseTime,
                    "Response took " + responseTime + "ms, expected < " + maxResponseTime + "ms");
        }

        @Test
        @DisplayName("Should handle rapid successive requests")
        void testRapidRequests() {
            int requestCount = 10;
            String[] commands = {
                    "go to home",
                    "search minecraft",
                    "filter puzzle",
                    "sort by rating",
                    "scroll down"
            };

            for (int i = 0; i < requestCount; i++) {
                String command = commands[i % commands.length];
                HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                        HttpRequest.POST("/api/v1/transcription/parse", command)
                                .contentType(MediaType.TEXT_PLAIN),
                        ParsedCommand.class
                );

                assertEquals(HttpStatus.OK, response.getStatus());
                assertNotNull(response.body());
            }
        }
    }

    // Integration tests for how audio is processed

    @Nested
    @DisplayName("Audio Processing Integration")
    @EnabledIfEnvironmentVariable(named = "VOSK_MODEL_AVAILABLE", matches = "true")
    class AudioProcessingTests {

        @Test
        @DisplayName("Should accept valid WAV format audio")
        void testValidWavAudio() {
            byte[] wavData = createSimpleWavData();

            HttpResponse<ParsedCommand> response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", wavData)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );

            assertEquals(HttpStatus.OK, response.getStatus());
            assertNotNull(response.body());

            // Will likely be NO_SPEECH for synthetic data
            assertNotNull(response.body().getType());
        }

        @Test
        @DisplayName("Should handle various audio sizes")
        void testVariousAudioSizes() {
            // Test small audio
            byte[] smallAudio = createSimpleWavData(100);
            HttpResponse<ParsedCommand> response1 = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", smallAudio)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );
            assertEquals(HttpStatus.OK, response1.getStatus());

            // Test medium audio
            byte[] mediumAudio = createSimpleWavData(1000);
            HttpResponse<ParsedCommand> response2 = client.toBlocking().exchange(
                    HttpRequest.POST("/api/v1/transcription/process", mediumAudio)
                            .contentType(MediaType.APPLICATION_OCTET_STREAM),
                    ParsedCommand.class
            );
            assertEquals(HttpStatus.OK, response2.getStatus());
        }
    }

    // createSimpleWavData has 2 variants (1 for no speech and 1 speech for tests)
    // writeInt and writeShort are additional helper methods

    /**
     * createSimpleWavData  creates a simple WAV file for testing (no speech)
     */
    private byte[] createSimpleWavData() {
        return createSimpleWavData(1000);
    }

    /**
     * createSimpleWavData creates a simple WAV file for testing with speech and specifics
     */
    private byte[] createSimpleWavData(int sampleCount) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // WAV Header (44 bytes)
            // RIFF chunk
            baos.write("RIFF".getBytes());
            writeInt(baos, 36 + sampleCount * 2); // File size - 8
            baos.write("WAVE".getBytes());

            // fmt chunk
            baos.write("fmt ".getBytes());
            writeInt(baos, 16); // Subchunk1Size
            writeShort(baos, (short) 1); // AudioFormat (PCM)
            writeShort(baos, (short) 1); // NumChannels (Mono)
            writeInt(baos, 16000); // SampleRate
            writeInt(baos, 32000); // ByteRate
            writeShort(baos, (short) 2); // BlockAlign
            writeShort(baos, (short) 16); // BitsPerSample

            // data chunk
            baos.write("data".getBytes());
            writeInt(baos, sampleCount * 2); // Subchunk2Size

            // Write sample data (silence)
            for (int i = 0; i < sampleCount; i++) {
                writeShort(baos, (short) 0);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create WAV data", e);
        }
    }

    private void writeInt(ByteArrayOutputStream baos, int value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(value);
        baos.write(buffer.array());
    }

    private void writeShort(ByteArrayOutputStream baos, short value) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(value);
        baos.write(buffer.array());
    }
}