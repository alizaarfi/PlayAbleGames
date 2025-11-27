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
    const [isMicActive, setIsMicActive] = useState(true);
    const [transcription, setTranscription] = useState('');
    const [commandData, setCommandData] = useState(null);
    const [error, setError] = useState(null);

    const hasInitialized = useRef(false);
    const isMicActiveRef = useRef(true)
    const mediaRecorderRef = useRef(null);
    const audioChunksRef = useRef([]);
    // put this so i can track loading time
    const [processingState, setProcessingState] = useState('idle');

    // Request mic access ONCE on mount
    useEffect(() => {
        if (hasInitialized.current) {
            console.log("⏭️ Already initialized, skipping");
            return;
        }
        hasInitialized.current = true;

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

                // Auto-start recording after mic is ready
                setTimeout(() => {
                    startRecordingAuto(mediaStream);
                }, 500);

            } catch (err) {
                console.error("❌ Microphone error:", err);
                setError(err.message);
            }
        };

        void initMic();

        return () => {
            console.log("🧹 Cleaning up microphone...");
            // DON'T reset hasInitialized here!

            // Stop the media recorder if running
            if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
                mediaRecorderRef.current.stop();
            }

            // Get the stream from state and stop all tracks
            setStream(currentStream => {
                if (currentStream) {
                    currentStream.getTracks().forEach(track => {
                        console.log("🛑 Stopping track:", track.kind);
                        track.stop();
                    });
                }
                return null;
            });
        };
    }, []);

    const toggleRecording = () => {
        if (isMicActive) {
            // MUTE
            stopRecording();
            setIsMicActive(false);
            isMicActiveRef.current = false;
            setProcessingState('idle'); //may need to change
        } else {
            // UNMUTE
            setIsMicActive(true);
            isMicActiveRef.current = true;
            if (stream) {
                startRecordingAuto(stream, true); // Pass true explicitly
            }
        }
    };

    const stopRecording = () => {
        console.log("🛑 Stopping recording manually");

        if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        }
    };

    const startRecordingAuto = (mediaStream, forceActive = null) => {
        // Use forceActive if provided, otherwise check isMicActive
        const shouldStart = forceActive !== null ? forceActive : isMicActive;

        if (!shouldStart) {
            console.log("⏸️ Mic is muted by user, not starting");
            return;
        }

        console.log("═══════════════════════════════════════════════════════");
        console.log("▶️  AUTO-START RECORDING");

        audioChunksRef.current = [];
        setTranscription('');
        setCommandData(null);
        setError(null);

        const mimeType = MediaRecorder.isTypeSupported('audio/webm') ? 'audio/webm' : 'audio/mp4';

        mediaRecorderRef.current = new MediaRecorder(mediaStream, {
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
            console.log("⏹️  Recording stopped - Processing audio");
            //atm doesn't diff from speech and from commanding
            setProcessingState('processing');

            const audioBlob = new Blob(audioChunksRef.current, { type: mimeType });
            await convertToWavAndSend(audioBlob);

            console.log("🔄 About to schedule restart...");
            console.log("🔄 mediaStream exists?", !!mediaStream);
            console.log("🔄 isMicActiveRef?", isMicActiveRef.current);

            setTimeout(() => {
                console.log("🔄 Inside setTimeout - attempting restart");
                console.log("🔄 mediaStream still exists?", !!mediaStream);
                console.log("🔄 isMicActiveRef still true?", isMicActiveRef.current);

                if (mediaStream && isMicActiveRef.current) {
                    console.log("✅ Calling startRecordingAuto");
                    startRecordingAuto(mediaStream);
                } else {
                    console.log("❌ Not restarting - conditions not met");
                }
            }, 1000);
        };
        mediaRecorderRef.current.start();
        setIsRecording(true);
        // recording now
        setProcessingState('recording');

        setTimeout(() => {
            if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
                mediaRecorderRef.current.stop();
                setIsRecording(false);
            }
        }, 4000); // 7 seconds

        console.log("🔴 Continuous recording started");
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

                console.log("📦 RAW RESPONSE:", voiceCommand);
                console.log("📦 Type:", voiceCommand.type);
                console.log("📦 Target:", voiceCommand.target);
                console.log("📦 Transcribed Text:", voiceCommand.transcribedText); // Changed

                if (voiceCommand.type !== 'NO_SPEECH' && voiceCommand.transcribedText) {
                    console.log("✅ Speech detected:", voiceCommand.transcribedText);

                    // now access processing state
                    if (voiceCommand.type !== 'UNKNOWN') {
                        setProcessingState('executing'); // ← Valid command! Show executing
                    } else {
                        setProcessingState('idle'); // ← Unknown command, back to idle
                        console.log("Speech detected but not a recognized command");
                    }

                    setTranscription(voiceCommand.transcribedText); // Changed
                    setCommandData({
                        type: voiceCommand.type,
                        target: voiceCommand.target,
                        confidence: voiceCommand.confidence,
                        timestamp: voiceCommand.timestamp
                    });
                } else {
                    // set back if no speech
                    setProcessingState('idle');
                }

            } else {
                const errorText = await response.text();
                console.error("❌ HTTP Error:", response.status, errorText);
                setProcessingState('error'); // ← Show error state
                setTimeout(() => setProcessingState('idle'), 3000);
                throw new Error(`HTTP ${response.status}: ${errorText}`);
            }

        } catch (err) {
            console.error("❌ Backend error:", err);
            setError(err.message);
            setProcessingState('error'); // ← Show error state
            setTimeout(() => setProcessingState('idle'), 3000);
        }
    };

    return {
        stream,
        isRecording,
        isMicActive,
        processingState,
        setProcessingState,  // for setting it
        transcription,
        commandData,
        error,
        toggleRecording
    };
}

export default useVoiceControl;

