import { useState, useRef } from 'react';

// Custom hook for capturing and processing audio
function useCaptureAudio(stream) {
    const [isRecording, setIsRecording] = useState(false);
    const [transcription, setTranscription] = useState('');
    const [isProcessing, setIsProcessing] = useState(false);
    const [error, setError] = useState(null);

    const mediaRecorderRef = useRef(null);
    const audioChunksRef = useRef([]);

    const startRecording = () => {
        if (!stream) {
            setError('No audio stream available');
            return;
        }

        audioChunksRef.current = [];

        const mimeType = MediaRecorder.isTypeSupported('audio/webm')
            ? 'audio/webm'
            : 'audio/mp4';

        mediaRecorderRef.current = new MediaRecorder(stream, {
            mimeType: mimeType,
            audioBitsPerSecond: 16000
        });

        mediaRecorderRef.current.ondataavailable = (event) => {
            if (event.data.size > 0) {
                audioChunksRef.current.push(event.data);
            }
        };

        mediaRecorderRef.current.onstop = async () => {
            const audioBlob = new Blob(audioChunksRef.current, { type: mimeType });
            await sendToBackend(audioBlob);
        };

        mediaRecorderRef.current.start();
        setIsRecording(true);
        setError(null);
    };

    const stopRecording = () => {
        if (mediaRecorderRef.current && isRecording) {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        }
    };

    const sendToBackend = async (audioBlob) => {
        setIsProcessing(true);
        setError(null);

        try {
            const response = await fetch('http://localhost:8081/api/v1/transcription/process', {
                method: 'POST',
                headers: { 'Content-Type': 'application/octet-stream' },
                body: audioBlob
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            setTranscription(result.transcription || result.text || '');
            console.log("Transcription: ", transcription);

        } catch (err) {
            setError(err.message);
            console.error('Backend error:', err);
        } finally {
            setIsProcessing(false);
        }
    };

    return {
        isRecording,
        transcription,
        isProcessing,
        error,
        startRecording,
        stopRecording
    };
}

export default useCaptureAudio;