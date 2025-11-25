import { useState, useEffect } from 'react';

function useSpeech() {
    const [isSpeaking, setIsSpeaking] = useState(false);

    const speak = (text) => {
        // Check if browser supports speech synthesis
        if (!('speechSynthesis' in window)) {
            alert('Sorry, your browser does not support text-to-speech.');
            return;
        }

        // Stop any ongoing speech
        window.speechSynthesis.cancel();

        // Create new speech utterance
        const utterance = new SpeechSynthesisUtterance(text);

        // Set voice properties
        utterance.rate = 1.0; // Speed (0.1 to 10)
        utterance.pitch = 1.0; // Pitch (0 to 2)
        utterance.volume = 1.0; // Volume (0 to 1)

        // Track when speaking starts and ends
        utterance.onstart = () => setIsSpeaking(true);
        utterance.onend = () => setIsSpeaking(false);
        utterance.onerror = () => setIsSpeaking(false);

        // Start speaking
        window.speechSynthesis.speak(utterance);
    };

    const stop = () => {
        window.speechSynthesis.cancel();
        setIsSpeaking(false);
    };

    const readPage = () => {
        // Get all readable text from the page
        const mainContent = document.querySelector('.app') || document.body;

        // Get all text nodes, excluding script and style tags
        const walker = document.createTreeWalker(
            mainContent,
            NodeFilter.SHOW_TEXT,
            {
                acceptNode: function(node) {
                    // Skip if parent is script, style, or hidden
                    const parent = node.parentElement;
                    if (!parent) return NodeFilter.FILTER_REJECT;

                    const tagName = parent.tagName.toLowerCase();
                    if (tagName === 'script' || tagName === 'style') {
                        return NodeFilter.FILTER_REJECT;
                    }

                    // Skip if text is empty or whitespace
                    if (!node.textContent.trim()) {
                        return NodeFilter.FILTER_REJECT;
                    }

                    return NodeFilter.FILTER_ACCEPT;
                }
            }
        );

        const textNodes = [];
        let node;
        while (node = walker.nextNode()) {
            textNodes.push(node.textContent.trim());
        }

        const pageText = textNodes.join('. ');

        if (pageText) {
            speak(pageText);
        } else {
            alert('No text found to read on this page.');
        }
    };

    // Cleanup on unmount
    useEffect(() => {
        return () => {
            window.speechSynthesis.cancel();
        };
    }, []);

    return { isSpeaking, speak, stop, readPage };
}

export default useSpeech;