import React from 'react';
import { render, screen, waitFor, act } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import Navbar from '../NavBar';

/**
 * NavBar.test tests the navbar component
 *
 * Tests for the voice control features:
 * - Basic rendering for logo, links, buttons
 * - Voice command processing e.g NAVIGATE, SEARCH, SCROLL
 * - Visual feedback states (processing states)
 * - Navigation to different pages via voice commands (type NAVIGATE)
 * - Custom event dispatching for page-specific commands like (go to [gameName] page)
 */


// Mock the hooks
jest.mock('../useVoiceControl', () => {
    return jest.fn(() => ({
        stream: { getTracks: () => [] },
        isRecording: false,
        isMicActive: true,
        processingState: 'idle',
        setProcessingState: jest.fn(),
        transcription: '',
        commandData: null,
        error: null,
        toggleRecording: jest.fn()
    }));
});

jest.mock('../UseSpeech', () => {
    return jest.fn(() => ({
        isSpeaking: false,
        stop: jest.fn(),
        readPage: jest.fn()
    }));
});

jest.mock('../hooks/AuthProvider', () => ({
    useAuth: () => ({
        isAuthenticated: false,
        logout: jest.fn()
    })
}));

// Mock CSS
jest.mock('../style.css', () => ({}));

// Mock ConfirmModal
jest.mock('../ConfirmModal', () => {
    return function ConfirmModal({ isOpen, message }) {
        if (!isOpen) return null;
        return <div data-testid="confirm-modal">{message}</div>;
    };
});

// Mock react-router-dom navigate
const mockedNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedNavigate,
}));

beforeEach(() => {
    jest.clearAllMocks();
    mockedNavigate.mockClear();
    localStorage.clear();
});

describe('Navbar - Basic Tests', () => {
    test('renders navbar with logo', () => {
        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('PlayAble Games')).toBeInTheDocument();
        expect(screen.getByText('All players welcome')).toBeInTheDocument();
    });

    test('renders microphone button', () => {
        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        const micButton = screen.getByRole('button', { name: /click to start recording/i });
        expect(micButton).toBeInTheDocument();
    });

    test('renders accessibility link', () => {
        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('About Accessibility')).toBeInTheDocument();
    });

    test('renders community link', () => {
        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Community')).toBeInTheDocument();
    });

    test('displays visual feedback with idle state', () => {
        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Ready - Speak to start')).toBeInTheDocument();
    });
});

describe('Navbar - Voice Command Processing', () => {
    test('processes NAVIGATE command to home', async () => {
        const useVoiceControl = require('../useVoiceControl');

        // Mock command data
        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'go home',
            commandData: {
                type: 'NAVIGATE',
                target: 'home',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(mockedNavigate).toHaveBeenCalledWith('/');
        });
    });

    test('processes NAVIGATE command to accessibility', async () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'go to accessibility',
            commandData: {
                type: 'NAVIGATE',
                target: 'accessibility',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(mockedNavigate).toHaveBeenCalledWith('/accessibility');
        });
    });

    test('processes NAVIGATE command to community', async () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'open community',
            commandData: {
                type: 'NAVIGATE',
                target: 'community',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(mockedNavigate).toHaveBeenCalledWith('/community');
        });
    });

    test('dispatches SEARCH voice command event', async () => {
        const useVoiceControl = require('../useVoiceControl');

        // Listen for the custom event
        const eventListener = jest.fn();
        window.addEventListener('voiceCommand', eventListener);

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'search puzzle games',
            commandData: {
                type: 'SEARCH',
                target: 'puzzle games',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(eventListener).toHaveBeenCalled();
        });

        // Check event details
        const event = eventListener.mock.calls[0][0];
        expect(event.detail.type).toBe('SEARCH');
        expect(event.detail.searchQuery).toBe('puzzle games');

        window.removeEventListener('voiceCommand', eventListener);
    });

    test('handles SCROLL down command', async () => {
        const useVoiceControl = require('../useVoiceControl');
        const scrollBySpy = jest.spyOn(window, 'scrollBy').mockImplementation(() => {});

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'scroll down',
            commandData: {
                type: 'SCROLL',
                target: 'down',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(scrollBySpy).toHaveBeenCalledWith({ top: 500, behavior: 'smooth' });
        });

        scrollBySpy.mockRestore();
    });

    test('handles SCROLL up command', async () => {
        const useVoiceControl = require('../useVoiceControl');
        const scrollBySpy = jest.spyOn(window, 'scrollBy').mockImplementation(() => {});

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'idle',
            setProcessingState: jest.fn(),
            transcription: 'scroll up',
            commandData: {
                type: 'SCROLL',
                target: 'up',
                confidence: 0.9,
                timestamp: Date.now()
            },
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(scrollBySpy).toHaveBeenCalledWith({ top: -500, behavior: 'smooth' });
        });

        scrollBySpy.mockRestore();
    });
});

describe('Navbar - Visual Feedback States', () => {
    test('shows recording state when recording', () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: true,
            isMicActive: true,
            processingState: 'recording',
            setProcessingState: jest.fn(),
            transcription: '',
            commandData: null,
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Listening')).toBeInTheDocument();
    });

    test('shows processing state when processing', () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'processing',
            setProcessingState: jest.fn(),
            transcription: '',
            commandData: null,
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Processing...')).toBeInTheDocument();
    });

    test('shows success state when command executed', () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'success',
            setProcessingState: jest.fn(),
            transcription: '',
            commandData: null,
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Done!')).toBeInTheDocument();
    });

    test('shows error state on command failure', () => {
        const useVoiceControl = require('../useVoiceControl');

        useVoiceControl.mockImplementation(() => ({
            stream: { getTracks: () => [] },
            isRecording: false,
            isMicActive: true,
            processingState: 'error',
            setProcessingState: jest.fn(),
            transcription: '',
            commandData: null,
            error: null,
            toggleRecording: jest.fn()
        }));

        render(
            <BrowserRouter>
                <Navbar />
            </BrowserRouter>
        );

        expect(screen.getByText('Try Again')).toBeInTheDocument();
    });
});