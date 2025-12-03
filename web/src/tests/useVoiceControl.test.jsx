import { renderHook, waitFor, act } from '@testing-library/react';
import '@testing-library/jest-dom';
import useVoiceControl from '../useVoiceControl';

/**
 * useVoiceControl.test tests the hook
 *
 * Tests the voice control functionality:
 * - Hook initialisation with default state
 * - Microphone permission requests
 * - Stream creation and management
 * - Toggle microphone on/off (when clicking mic icon on UI)
 * - Processing state changes e.g recording as needed for the visual feedback box
 */

beforeEach(() => {
    jest.clearAllMocks();

    // Mock MediaRecorder
    global.MediaRecorder = jest.fn().mockImplementation(() => ({
        start: jest.fn(),
        stop: jest.fn(),
        state: 'inactive',
        ondataavailable: null,
        onstop: null,
    }));

    global.MediaRecorder.isTypeSupported = jest.fn(() => true);

    global.AudioContext = jest.fn().mockImplementation(() => ({
        sampleRate: 16000,
        decodeAudioData: jest.fn(() => Promise.resolve({
            sampleRate: 16000,
            numberOfChannels: 1,
            duration: 2,
            getChannelData: jest.fn(() => new Float32Array(100))
        }))
    }));

    const mockStream = {
        getTracks: jest.fn(() => [{
            kind: 'audio',
            stop: jest.fn()
        }])
    };

    global.navigator.mediaDevices = {
        getUserMedia: jest.fn(() => Promise.resolve(mockStream))
    };

    // Mock fetch with global
    global.fetch = jest.fn(() =>
        Promise.resolve({
            ok: true,
            json: () => Promise.resolve({
                type: 'NAVIGATE',
                target: 'home',
                transcribedText: 'go home',
                confidence: 0.9,
                timestamp: Date.now()
            })
        })
    );
});

describe('useVoiceControl Hook - Basic Tests', () => {
    test('initializes with default state', async () => {
        const { result } = renderHook(() => useVoiceControl());

        // Check initial states
        expect(result.current.stream).toBeNull();
        expect(result.current.isRecording).toBe(false);
        expect(result.current.isMicActive).toBe(true);
        expect(result.current.transcription).toBe('');
        expect(result.current.commandData).toBeNull();
        expect(result.current.error).toBeNull();
        expect(result.current.processingState).toBe('idle');
    });

    test('requests microphone permission on mount', async () => {
        renderHook(() => useVoiceControl());

        await waitFor(() => {
            expect(navigator.mediaDevices.getUserMedia).toHaveBeenCalledWith({
                audio: {
                    channelCount: 1,
                    sampleRate: 16000,
                    echoCancellation: true,
                    noiseSuppression: true,
                    autoGainControl: true
                }
            });
        });
    });

    test('sets stream after microphone access granted', async () => {
        const { result } = renderHook(() => useVoiceControl());

        await waitFor(() => {
            expect(result.current.stream).not.toBeNull();
        }, { timeout: 3000 });
    });

    test('toggles mic to inactive state', async () => {
        const { result } = renderHook(() => useVoiceControl());

        // Wait for initialization
        await waitFor(() => {
            expect(result.current.stream).not.toBeNull();
        });

        // Initially active
        expect(result.current.isMicActive).toBe(true);

        // Toggle to inactive using act
        await act(async () => {
            result.current.toggleRecording();
        });

        await waitFor(() => {
            expect(result.current.isMicActive).toBe(false);
        });
    });

    test('toggles mic back to active state', async () => {
        const { result } = renderHook(() => useVoiceControl());

        // Wait for initialization
        await waitFor(() => {
            expect(result.current.stream).not.toBeNull();
        });

        // Toggle to inactive
        await act(async () => {
            result.current.toggleRecording();
        });

        await waitFor(() => {
            expect(result.current.isMicActive).toBe(false);
        });

        // Toggle back to active
        await act(async () => {
            result.current.toggleRecording();
        });

        await waitFor(() => {
            expect(result.current.isMicActive).toBe(true);
        });
    });

    test('handles microphone permission error', async () => {
        // Mock getUserMedia to reject
        navigator.mediaDevices.getUserMedia = jest.fn(() =>
            Promise.reject(new Error('Permission denied'))
        );

        const { result } = renderHook(() => useVoiceControl());

        await waitFor(() => {
            expect(result.current.error).toBe('Permission denied');
        });
    });

    test('processing state changes to recording when active', async () => {
        const { result } = renderHook(() => useVoiceControl());

        await waitFor(() => {
            expect(result.current.stream).not.toBeNull();
        });

        // Wait for auto-start recording (happens after 800ms delay)
        await waitFor(() => {
            expect(result.current.processingState).toBe('recording');
        }, { timeout: 2000 });
    });
});