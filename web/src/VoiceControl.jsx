import React, { useState, useRef, useEffect } from 'react';

// HomePage prompts for mic access?

// Captures audio from microphone
// Sends to your Java backend (http://localhost:8080/api/v1/transcription/upload)
// Displays the result from backend
// Handles errors gracefully

// Request mic access on booting default page
// With permissions record

function captureAudio(stream) {
    // Before capturing
    const [isRecording, setIsRecording] = useState(false);
    const [transcription, setTranscription] = useState('');
    const [isProcessing, setIsProcessing] = useState(false);
    const [error, setError] = useState(null);



}