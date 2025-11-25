import { useEffect } from 'react';

function useHoverSpeech(enabled) {
    useEffect(() => {
        if (!enabled) return;

        let currentUtterance = null;
        let hoverTimeout = null;

        const speak = (text) => {
            // Cancel any ongoing speech
            window.speechSynthesis.cancel();

            // Create new utterance
            currentUtterance = new SpeechSynthesisUtterance(text);
            currentUtterance.rate = 1.0;
            currentUtterance.pitch = 1.0;
            currentUtterance.volume = 1.0;

            window.speechSynthesis.speak(currentUtterance);
        };

        const handleMouseEnter = (e) => {
            const target = e.target;

            // Get text content from specific elements
            let textToRead = null;

            // Game cards
            if (target.closest('.game-card')) {
                const card = target.closest('.game-card');
                const title = card.querySelector('h3')?.textContent;
                const description = card.querySelector('.game-description')?.textContent;
                if (title) textToRead = `${title}. ${description || ''}`;
            }
            // Buttons and links
            else if (target.matches('button, a, .nav-btn')) {
                textToRead = target.textContent.trim() || target.getAttribute('aria-label');
            }
            // Headings
            else if (target.matches('h1, h2, h3, h4, h5, h6')) {
                textToRead = target.textContent.trim();
            }
            // Paragraphs
            else if (target.matches('p')) {
                textToRead = target.textContent.trim();
            }
            // Setting items
            else if (target.closest('.setting-item')) {
                const settingItem = target.closest('.setting-item');
                const title = settingItem.querySelector('h3')?.textContent;
                const description = settingItem.querySelector('p')?.textContent;
                if (title) textToRead = `${title}. ${description || ''}`;
            }

            if (textToRead && textToRead.length > 0) {
                // Add delay to avoid accidental triggers
                hoverTimeout = setTimeout(() => {
                    speak(textToRead);
                }, 500); // 500ms delay
            }
        };

        const handleMouseLeave = () => {
            // Clear timeout if user moves away quickly
            if (hoverTimeout) {
                clearTimeout(hoverTimeout);
                hoverTimeout = null;
            }

            // Stop speaking when mouse leaves
            window.speechSynthesis.cancel();
        };

        // Add event listeners
        document.addEventListener('mouseenter', handleMouseEnter, true);
        document.addEventListener('mouseleave', handleMouseLeave, true);

        // Cleanup
        return () => {
            document.removeEventListener('mouseenter', handleMouseEnter, true);
            document.removeEventListener('mouseleave', handleMouseLeave, true);
            window.speechSynthesis.cancel();
            if (hoverTimeout) clearTimeout(hoverTimeout);
        };
    }, [enabled]);
}

export default useHoverSpeech;