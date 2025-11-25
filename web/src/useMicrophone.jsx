//can allow camera with userMedia too but not needed
import { useState, useEffect } from 'react';

// React Hook to use within HomePage
// could add more error handling in
function useMicrophone() {
    const [stream, setStream] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const openMic = async () => {
        setIsLoading(true);
        setError(null);

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

            setStream(mediaStream);
            setIsLoading(false);
            console.log("Mic ready for Vosk")
            return mediaStream;

        } catch (err) {
            setError(err);
            setIsLoading(false);
            console.log("Mic error:", err)
            return null;
        }
    };

    const closeMic = () => {
        if (stream) {
            stream.getTracks().forEach(track => track.stop());
            setStream(null);
        }
    };

    // Cleanup on unmount
    useEffect(() => {
        return () => {
            closeMic();
        };
    }, []);

    return { stream, error, isLoading, openMic, closeMic };
}
export default useMicrophone;