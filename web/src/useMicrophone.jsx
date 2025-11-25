//can allow camera with userMedia too but not needed
import { useState, useEffect } from 'react';

// React Hook to use within HomePage
// could add more error handling in
function useMicrophone() {
    const [stream, setStream] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const openMic = async () => {
        console.log("═══════════════════════════════════════════════════════");
        console.log("🎤 OPENING MICROPHONE");
        setIsLoading(true);
        setError(null);

        try {
            console.log("⏳ Requesting user media...");
            const mediaStream = await navigator.mediaDevices.getUserMedia({
                audio: {
                    channelCount: 1,
                    sampleRate: 16000,
                    echoCancellation: true,
                    noiseSuppression: true,
                    autoGainControl: true
                }
            });

            console.log("✅ Microphone access granted");
            console.log("   📊 Stream tracks:", mediaStream.getTracks().length);
            console.log("   📊 Audio tracks:", mediaStream.getAudioTracks().length);

            setStream(mediaStream);
            setIsLoading(false);
            console.log("🎤 Mic ready for Vosk");
            console.log("═══════════════════════════════════════════════════════");
            return mediaStream;

        } catch (err) {
            console.error("═══════════════════════════════════════════════════════");
            console.error("❌ MICROPHONE ERROR");
            console.error("   💥 Error name:", err.name);
            console.error("   💬 Error message:", err.message);
            console.error("   📚 Full error:", err);
            console.error("═══════════════════════════════════════════════════════");
            setError(err);
            setIsLoading(false);
            return null;
        }
    };

    const closeMic = () => {
        console.log("🔇 CLOSING MICROPHONE");
        if (stream) {
            stream.getTracks().forEach(track => {
                console.log("   ⏹️  Stopping track:", track.kind);
                track.stop();
            });
            setStream(null);
            console.log("✅ Microphone closed");
        } else {
            console.log("⚠️  No stream to close");
        }
    };

    // Cleanup on unmount
    useEffect(() => {
        return () => {
            console.log("🧹 Component unmounting - cleaning up mic");
            closeMic();
        };
    }, []);

    return { stream, error, isLoading, openMic, closeMic };
}
export default useMicrophone;