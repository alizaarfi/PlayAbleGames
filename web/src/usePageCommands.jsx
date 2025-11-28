

import { useEffect } from 'react';
import useVoiceControl from './useVoiceControl';

/**
 * Hook for handling voice commands on specific pages
 * Automatically processes commands and updates processing state
 *
 * @param {Object} handlers - Map of command types to handler functions
 * @example
 * usePageCommands({
 *   FAVOURITE: (gameName) => toggleFavorite(gameId),
 *   FILTER: (category) => applyFilter(category)
 * });
 */
export const usePageCommands = (handlers) => {
    const { commandData, setProcessingState } = useVoiceControl();

    useEffect(() => {
        if (!commandData) return;

        const handler = handlers[commandData.type];

        if (handler) {
            try {
                handler(commandData.target);
                setProcessingState('success');
                setTimeout(() => setProcessingState('idle'), 1500);
            } catch (err) {
                console.error('Command handler error:', err);
                setProcessingState('error');
                setTimeout(() => setProcessingState('idle'), 2000);
            }
        }
    }, [commandData, handlers, setProcessingState]);
};

export default usePageCommands;