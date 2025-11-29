import React, { useState, useRef, useEffect } from 'react';
/**
 * useVoiceControl hook captures audio, sends to backend to be processed, handles backend response continuously
 *
 * Architecture & Design Patterns:
 *  - Custom Hook pattern keeps recording audio in one place and allows logic to be reused
 *  - Continuous recording/processing stream as restarts recording automatically every 3 seconds
 *  - Processing state to feedback to users
 *  - Ref Pattern & cleanup pattern
 *
 *  Understand the flow:
 *  - useEffect - initialises mic permission on mount with initMic()
 *  - toggleRecording - toggles use of stopRecording and startRecordingAuto based on user muting or unmuting the mic button in the Navbar UI
 *  - startRecordingAuto calls convertToWaveAndSend which takes audio sends to backend
 *  - convertToWavAndSend calls audioBufferToWave and sendToBackend
 *  - sendToBackend sends to controller, awaits JSON response and handles it to either create a NO_SPEECH ParsedCommand, or a valid command type as part of commandData
 * */

function useVoiceControl() {
    const [stream, setStream] = useState(null); // The media stream is null until mic permission from browser is accepted
    // These states trigger rendering again on the UI (isRecording, transcription, error)
    const [isRecording, setIsRecording] = useState(false);
    const [isMicActive, setIsMicActive] = useState(true); // mute/unmuting by user
    const [transcription, setTranscription] = useState('');

    const [commandData, setCommandData] = useState(null);
    const [error, setError] = useState(null);

    // These refs are mutable as they do not render again (mediaRecorder, audio chunks, isMicActiveRef)
    const hasInitialised = useRef(false);
    const isMicActiveRef = useRef(true) //tracks isMicActive for muting/unmuting and recording
    const mediaRecorderRef = useRef(null);
    const audioChunksRef = useRef([]);

    // Track loading time for commands to display in visual feedback box
    const [processingState, setProcessingState] = useState('idle');

    // Request mic access once on mount
    useEffect(() => {
        if (hasInitialised.current) {
            console.log("Already initialised, skipping");
            return;
        }
        hasInitialised.current = true;

        console.log("Requesting microphone access...");

        const initMic = async () => {
            try {
                // The constraints are for Vosk
                const mediaStream = await navigator.mediaDevices.getUserMedia({
                    audio: {
                        channelCount: 1,
                        sampleRate: 16000,
                        echoCancellation: true,
                        noiseSuppression: true,
                        autoGainControl: true
                    }
                });

                console.log("Microphone ready");
                setStream(mediaStream);

                // Auto-start recording after mic is ready after short delay
                setTimeout(() => {
                    startRecordingAuto(mediaStream);
                }, 800);

            } catch (err) {
                console.error("Microphone error:", err);
                setError(err.message);
            }
        };

        void initMic();

        // Cleanup for mic when unmounting
        return () => {
            console.log("Cleaning up microphone...");

            // Stop the media recorder if running
            if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
                mediaRecorderRef.current.stop();
            }

            // Get the stream from state and stop all tracks
            setStream(currentStream => {
                if (currentStream) {
                    currentStream.getTracks().forEach(track => {
                        console.log("Stopping track:", track.kind);
                        track.stop();
                    });
                }
                return null;
            });
        };
    }, []);

    // For mute/unmute when mic icon is clicked
    const toggleRecording = () => {
        // mute mic if its currently active
        if (isMicActive) {
            stopRecording();
            //update states
            setIsMicActive(false);
            isMicActiveRef.current = false;
            setProcessingState('idle'); //may need to change later

        } else {
            // if inactive, unmute, and start stream
            setIsMicActive(true);
            isMicActiveRef.current = true;
            if (stream) {
                startRecordingAuto(stream, true);
            }
        }
    };

    // When mute stop recording
    const stopRecording = () => {
        console.log("Stopping recording manually");

        if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        }
    };

    // Unmuted for startrecording made auto (used to have just recording)
    const startRecordingAuto = (mediaStream, forceActive = null) => {
        // Use forceActive if provide
        const shouldStart = forceActive !== null ? forceActive : isMicActive;

        if (!shouldStart) {
            console.log("Mic is muted by user, not starting");
            return;
        }

        console.log("----------------------------------------------");
        console.log("AUTO-START RECORDING");

        audioChunksRef.current = [];
        setTranscription('');
        setCommandData(null);
        setError(null);

        const mimeType = MediaRecorder.isTypeSupported('audio/webm') ? 'audio/webm' : 'audio/mp4';

        // Created MediaRecorder (Vosk constraints - better for Vosk)
        mediaRecorderRef.current = new MediaRecorder(mediaStream, {
            mimeType: mimeType,
            audioBitsPerSecond: 16000
        });

        // accumulates chunks
        mediaRecorderRef.current.ondataavailable = (event) => {
            if (event.data.size > 0) {
                console.log("Audio chunk:", event.data.size, "bytes");
                audioChunksRef.current.push(event.data);
            }
        };

        // processes the captured audio and schedules when next recording
        mediaRecorderRef.current.onstop = async () => {
            console.log("Recording stopped - Processing audio");
            //atm doesn't diff from speech and from commanding
            // setProcessingState('processing');

            const audioBlob = new Blob(audioChunksRef.current, { type: mimeType });
            await convertToWavAndSend(audioBlob);

            // needed when testing if recording auto restarts
            console.log("About to schedule restart...");
            console.log("mediaStream exists?", !!mediaStream);
            console.log("isMicActiveRef?", isMicActiveRef.current);

            // When next recording
            setTimeout(() => {
                console.log("Inside setTimeout() trying restart");
                console.log("mediaStream still exists?", !!mediaStream);
                console.log("isMicActiveRef still true?", isMicActiveRef.current);

                if (mediaStream && isMicActiveRef.current) {
                    console.log("calling startRecordingAuto");
                    startRecordingAuto(mediaStream);
                } else {
                    console.log("Not restarting no media stream/active mic");
                }
            }, 1000);
        };
        // This starts capturing the audio
        mediaRecorderRef.current.start();
        setIsRecording(true);
        // set recording now for UI
        setProcessingState('recording');

        // Setting timing for capturing audio, can edit time for testing
        setTimeout(() => {
            if (mediaRecorderRef.current && mediaRecorderRef.current.state === 'recording') {
                mediaRecorderRef.current.stop();
                setIsRecording(false);
            }
        }, 3000); // 3 seconds

        console.log("Continuous recording started");
    };


    // Convert audio to WAV format that Vosk expects
    const convertToWavAndSend = async (audioBlob) => {
        try {
            console.log("Converting to WAV...");

            const arrayBuffer = await audioBlob.arrayBuffer();
            const audioContext = new (window.AudioContext || window.webkitAudioContext)({ sampleRate: 16000 });
            const audioBuffer = await audioContext.decodeAudioData(arrayBuffer);

            console.log("Audio buffer:");
            console.log("- Sample rate:", audioBuffer.sampleRate);
            console.log("- Channels:", audioBuffer.numberOfChannels);
            console.log("- Duration:", audioBuffer.duration.toFixed(2), "seconds");

            // For short recordings
            if (audioBuffer.duration < 0.5) {
                console.warn("Recording is very short, may not transcribe well");
            }

            // Converts to WAV from the buffer
            const wavBlob = audioBufferToWav(audioBuffer);
            console.log("Converted to WAV:", wavBlob.size, "bytes");

            // waits for response when calling sendToBackend
            await sendToBackend(wavBlob);

        } catch (err) {
            console.error("Audio conversion error:", err);
            setError(`Audio conversion failed: ${err.message}`);
        }
    };

    // Convert AudioBuffer to WAV format
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

        // Write WAV header - sampleData good for vosk
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

    // helper for AudioBufferToWav
    const writeString = (view, offset, string) => {
        for (let i = 0; i < string.length; i++) {
            view.setUint8(offset + i, string.charCodeAt(i));
        }
    };

    // sending converted data to backend
    const sendToBackend = async (audioBlob) => {
        console.log("Sending WAV to backend...");
        setProcessingState('processing'); //set here instead of onstop

        try {
            // in application.properties need micronaut config for cors enabled or will encounter CORS error
            const response = await fetch('http://localhost:8081/api/v1/transcription/process', {
                method: 'POST',
                headers: { 'Content-Type': 'application/octet-stream' },
                body: audioBlob
            });

            console.log("Response status:", response.status);

            // handle response
            if (response.ok) {
                const voiceCommand = await response.json();

                console.log("RAW RESPONSE:", voiceCommand);
                console.log("Type:", voiceCommand.type);
                console.log("Target:", voiceCommand.target);
                console.log("Transcribed Text:", voiceCommand.transcribedText); // Changed

                if (voiceCommand.type !== 'NO_SPEECH' && voiceCommand.transcribedText) {
                    console.log("Speech detected:", voiceCommand.transcribedText);
                    // now access processing state
                    if (voiceCommand.type !== 'UNKNOWN') {
                        //let navbar handle this?
                        // setProcessingState('executing');
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
                console.error("HTTP Error:", response.status, errorText);
                setProcessingState('error'); // ← Show error state
                setTimeout(() => setProcessingState('idle'), 3000);
                throw new Error(`HTTP ${response.status}: ${errorText}`);
            }

        } catch (err) {
            console.error("Backend error:", err);
            setError(err.message);
            setProcessingState('error'); // ← Show error state
            setTimeout(() => setProcessingState('idle'), 3000);
        }
    };

    // exposes the state and controls to Navbar (parent component)
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

