import React, { useState, useRef, useEffect } from 'react';

// HomePage prompts for mic access?

// Captures audio from microphone
// Sends to your Java backend (http://localhost:8080/api/v1/transcription/upload)
// Displays the result from backend
// Handles errors gracefully

// Request mic access on booting default page
// With permissions record

function useVoiceControl() {
    const [stream, setStream] = useState(null);
    const [isRecording, setIsRecording] = useState(false);
    const [transcription, setTranscription] = useState('');
    const [commandData, setCommandData] = useState(null);
    const [error, setError] = useState(null);

    const mediaRecorderRef = useRef(null);
    const audioChunksRef = useRef([]);

    // Request mic access ONCE on mount
    useEffect(() => {
        console.log("🎤 Requesting microphone access...");

        const initMic = async () => {
            try {
                const mediaStream = await navigator.mediaDevices.getUserMedia({
                    audio: {
                        channelCount: 1,
                        sampleRate: 16000,
                        echoCancellation: true,
                        noiseSuppression: true,
                        autoGainControl: true
                    }
                });

                console.log("✅ Microphone ready");
                setStream(mediaStream);

            } catch (err) {
                console.error("❌ Microphone error:", err);
                setError(err.message);
            }
        };

        void initMic();

        return () => {
            if (stream) {
                stream.getTracks().forEach(track => track.stop());
            }
        };
    }, []);

    const toggleRecording = () => {
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
        }
    };

    const startRecording = () => {
        console.log("═══════════════════════════════════════════════════════");
        console.log("▶️  START RECORDING");

        if (!stream) {
            console.error("❌ No microphone available");
            setError('Microphone not available');
            return;
        }

        audioChunksRef.current = [];
        setTranscription('');
        setCommandData(null);
        setError(null);

        const mimeType = MediaRecorder.isTypeSupported('audio/webm') ? 'audio/webm' : 'audio/mp4';

        mediaRecorderRef.current = new MediaRecorder(stream, {
            mimeType: mimeType,
            audioBitsPerSecond: 16000
        });

        mediaRecorderRef.current.ondataavailable = (event) => {
            if (event.data.size > 0) {
                console.log("📦 Audio chunk:", event.data.size, "bytes");
                audioChunksRef.current.push(event.data);
            }
        };

        mediaRecorderRef.current.onstop = async () => {
            console.log("⏹️  Recording stopped");
            const audioBlob = new Blob(audioChunksRef.current, { type: mimeType });
            console.log("📦 Original audio:", audioBlob.size, "bytes, type:", audioBlob.type);
            await convertToWavAndSend(audioBlob);
        };

        mediaRecorderRef.current.start();
        setIsRecording(true);
        console.log("🔴 Recording...");
    };

    const stopRecording = () => {
        console.log("⏹️  Stopping recording");
        if (mediaRecorderRef.current && isRecording) {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        }
    };

    // Convert audio to WAV format that Vosk expects
    const convertToWavAndSend = async (audioBlob) => {
        try {
            console.log("🔄 Converting to WAV...");

            const arrayBuffer = await audioBlob.arrayBuffer();
            const audioContext = new (window.AudioContext || window.webkitAudioContext)({ sampleRate: 16000 });
            const audioBuffer = await audioContext.decodeAudioData(arrayBuffer);

            console.log("Audio buffer:");
            console.log("- Sample rate:", audioBuffer.sampleRate);
            console.log("- Channels:", audioBuffer.numberOfChannels);
            console.log("- Duration:", audioBuffer.duration.toFixed(2), "seconds");

            if (audioBuffer.duration < 0.5) {
                console.warn("⚠️ Recording is very short, may not transcribe well");
            }

            const wavBlob = audioBufferToWav(audioBuffer);
            console.log("✅ Converted to WAV:", wavBlob.size, "bytes");

            await sendToBackend(wavBlob);

        } catch (err) {
            console.error("❌ Audio conversion error:", err);
            setError(`Audio conversion failed: ${err.message}`);
        }
    };

    // Convert AudioBuffer to WAV format (16-bit PCM, mono, 16kHz)
    const audioBufferToWav = (buffer) => {
        const numberOfChannels = 1; // Mono
        const sampleRate = buffer.sampleRate;
        const format = 1; // PCM
        const bitDepth = 16;

        const bytesPerSample = bitDepth / 8;
        const blockAlign = numberOfChannels * bytesPerSample;

        // Get mono channel data
        const data = buffer.getChannelData(0);
        const dataLength = data.length * bytesPerSample;
        const bufferLength = 44 + dataLength;

        const arrayBuffer = new ArrayBuffer(bufferLength);
        const view = new DataView(arrayBuffer);

        // Write WAV header
        writeString(view, 0, 'RIFF');
        view.setUint32(4, 36 + dataLength, true);
        writeString(view, 8, 'WAVE');
        writeString(view, 12, 'fmt ');
        view.setUint32(16, 16, true); // Subchunk1Size (16 for PCM)
        view.setUint16(20, format, true); // AudioFormat (1 for PCM)
        view.setUint16(22, numberOfChannels, true);
        view.setUint32(24, sampleRate, true);
        view.setUint32(28, sampleRate * blockAlign, true); // ByteRate
        view.setUint16(32, blockAlign, true);
        view.setUint16(34, bitDepth, true);
        writeString(view, 36, 'data');
        view.setUint32(40, dataLength, true);

        // Write PCM samples
        let offset = 44;
        for (let i = 0; i < data.length; i++) {
            const sample = Math.max(-1, Math.min(1, data[i]));
            const intSample = sample < 0 ? sample * 0x8000 : sample * 0x7FFF;
            view.setInt16(offset, intSample, true);
            offset += 2;
        }

        return new Blob([arrayBuffer], { type: 'audio/wav' });
    };

    const writeString = (view, offset, string) => {
        for (let i = 0; i < string.length; i++) {
            view.setUint8(offset + i, string.charCodeAt(i));
        }
    };

    const sendToBackend = async (audioBlob) => {
        console.log("📤 Sending WAV to backend...");

        try {
            const response = await fetch('http://localhost:8081/api/v1/transcription/process', {
                method: 'POST',
                headers: { 'Content-Type': 'application/octet-stream' },
                body: audioBlob
            });

            console.log("📥 Response status:", response.status);

            if (response.ok) {
                const voiceCommand = await response.json();

                console.log("✅ Full response:", voiceCommand);
                console.log("✅ Transcription:", voiceCommand.transcribedText);
                console.log("✅ Command:", voiceCommand.command);
                console.log("✅ Target:", voiceCommand.commandTarget);
                console.log("✅ Confidence:", voiceCommand.confidence);

                setTranscription(voiceCommand.transcribedText || '');

                setCommandData({
                    command: voiceCommand.command,
                    target: voiceCommand.commandTarget,
                    confidence: voiceCommand.confidence,
                    timestamp: voiceCommand.timestamp,
                    processed: voiceCommand.processed
                });

            } else {
                const errorText = await response.text();
                console.error("❌ HTTP Error:", response.status, errorText);
                throw new Error(`HTTP ${response.status}: ${errorText}`);
            }

        } catch (err) {
            console.error("❌ Backend error:", err);
            setError(err.message);
        }
    };

    return {
        stream,
        isRecording,
        transcription,
        commandData,
        error,
        toggleRecording
    };
}

export default useVoiceControl;

