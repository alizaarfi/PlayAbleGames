import { useState, useRef } from 'react';

function useCaptureAudio(stream) {
    const [isRecording, setIsRecording] = useState(false);
    const [transcription, setTranscription] = useState('');
    const [isProcessing, setIsProcessing] = useState(false);
    const [error, setError] = useState(null);

    const mediaRecorderRef = useRef(null);
    const audioChunksRef = useRef([]);

    const startRecording = () => {
        console.log("═══════════════════════════════════════════════════════");
        console.log("▶️  START RECORDING");

        if (!stream) {
            console.error("❌ No audio stream available");
            setError('No audio stream available');
            return;
        }

        console.log("✅ Stream is active");
        audioChunksRef.current = [];

        const mimeType = MediaRecorder.isTypeSupported('audio/webm')
            ? 'audio/webm'
            : 'audio/mp4';

        console.log("📝 Using MIME type:", mimeType);

        mediaRecorderRef.current = new MediaRecorder(stream, {
            mimeType: mimeType,
            audioBitsPerSecond: 16000
        });

        mediaRecorderRef.current.ondataavailable = (event) => {
            if (event.data.size > 0) {
                console.log("📦 Audio chunk received:", event.data.size, "bytes");
                audioChunksRef.current.push(event.data);
            }
        };

        mediaRecorderRef.current.onstop = async () => {
            console.log("⏹️  Recording stopped");
            console.log("📊 Total chunks:", audioChunksRef.current.length);

            const audioBlob = new Blob(audioChunksRef.current, { type: mimeType });
            console.log("📦 Created blob:", audioBlob.size, "bytes");

            await sendToBackend(audioBlob);
        };

        mediaRecorderRef.current.start();
        setIsRecording(true);
        setError(null);
        console.log("🔴 Recording in progress...");
    };

    const stopRecording = () => {
        console.log("⏸️  STOP RECORDING called");
        if (mediaRecorderRef.current && isRecording) {
            mediaRecorderRef.current.stop();
            setIsRecording(false);
        } else {
            console.warn("⚠️  Cannot stop - not currently recording");
        }
    };

    const sendToBackend = async (audioBlob) => {
        console.log("═══════════════════════════════════════════════════════");
        console.log("📤 SENDING TO BACKEND");
        console.log("   📊 Blob size:", audioBlob.size, "bytes");
        console.log("   📊 Blob size:", (audioBlob.size / 1024).toFixed(2), "KB");
        console.log("   📝 Blob type:", audioBlob.type);
        console.log("   🌐 URL: http://localhost:8081/api/v1/transcription/process");

        setIsProcessing(true);
        setError(null);

        try {
            console.log("⏳ Sending request...");

            const response = await fetch('http://localhost:8081/api/v1/transcription/process', {
                method: 'POST',
                headers: { 'Content-Type': 'application/octet-stream' },
                body: audioBlob
            });

            console.log("📥 RESPONSE RECEIVED");
            console.log("   📊 Status:", response.status);
            console.log("   ✅ OK:", response.ok);
            console.log("   📝 Status Text:", response.statusText);

            if (!response.ok) {
                const errorText = await response.text();
                console.error("❌ Backend error response:", errorText);
                throw new Error(`HTTP ${response.status}: ${errorText}`);
            }

            const result = await response.json();
            console.log("✅ SUCCESS - Response parsed");
            console.log("   📦 Full response:", result);
            console.log("   📝 Transcription:", result.transcription);
            console.log("═══════════════════════════════════════════════════════");

            setTranscription(result.transcription || result.text || '');

        } catch (err) {
            console.error("═══════════════════════════════════════════════════════");
            console.error("❌ ERROR");
            console.error("   💥 Type:", err.name);
            console.error("   💬 Message:", err.message);
            console.error("   📚 Full error:", err);
            console.error("═══════════════════════════════════════════════════════");
            setError(err.message);
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
