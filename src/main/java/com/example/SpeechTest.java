package com.example;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

// This was purely to test the vosk library in the beginning
public class SpeechTest {

    public static void main(String[] args) {
        LibVosk.setLogLevel(LogLevel.INFO);

        // Defines commands to listen for
        String grammar = "[\"go home\", \"go back\", \"scroll up\", \"scroll down\", \"next page\", \"search\", \"open menu\", \"[unk]\"]";

        try (Model model = new Model("model")) {

            AudioFormat format = new AudioFormat(16000f, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Microphone not supported");
                return;
            }

            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            System.out.println("Vosk Voice Command Test");
            System.out.println("Say commands like: go home, go back, scroll up, next page");

            // Use grammar-constrained recognition
            try (Recognizer recognizer = new Recognizer(model, 16000, grammar)) {

                byte[] buffer = new byte[4096];

                while (true) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);

                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        String result = recognizer.getResult();
                        String text = extractText(result);

                        if (!text.isEmpty() && !text.equals("[unk]")) {
                            System.out.println(">>> Command recognized: " + text);
                        }
                    } else {
                        String partial = recognizer.getPartialResult();
                        String partialText = extractPartial(partial);

                        if (!partialText.isEmpty()) {
                            System.out.print("\rListening: " + partialText + "          ");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String extractText(String json) {
        // Extract text from JSON response
        int start = json.indexOf("\"text\"");
        if (start == -1) return "";

        int colonPos = json.indexOf(":", start);
        int firstQuote = json.indexOf("\"", colonPos);
        int secondQuote = json.indexOf("\"", firstQuote + 1);

        if (firstQuote != -1 && secondQuote != -1) {
            return json.substring(firstQuote + 1, secondQuote);
        }
        return "";
    }

    private static String extractPartial(String json) {
        // Extract partial value
        int start = json.indexOf("\"partial\"");
        if (start == -1) return "";

        int colonPos = json.indexOf(":", start);
        int firstQuote = json.indexOf("\"", colonPos);
        int secondQuote = json.indexOf("\"", firstQuote + 1);

        if (firstQuote != -1 && secondQuote != -1) {
            return json.substring(firstQuote + 1, secondQuote);
        }
        return "";
    }
}