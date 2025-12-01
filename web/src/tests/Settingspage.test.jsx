import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import SettingsPage from '../SettingsPage';

// Mock the custom hooks
jest.mock('../UseHoverSpeech', () => ({
    __esModule: true,
    default: jest.fn(),
}));

describe('SettingsPage', () => {
    beforeEach(() => {
        localStorage.clear();
        jest.clearAllMocks();
    });

    test('renders the page title', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Accessibility Settings')).toBeInTheDocument();
    });

    test('renders the page subtitle', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Customise the platform to meet your accessibility needs')).toBeInTheDocument();
    });

    test('renders WCAG 2.1 AA standards text', () => {
        render(<SettingsPage />);
        expect(screen.getByText('This platform follows WCAG 2.1 AA standards')).toBeInTheDocument();
    });

    test('renders Visual Settings section', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Visual Settings')).toBeInTheDocument();
    });

    test('renders Audio & Speech Settings section', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Audio & Speech Settings')).toBeInTheDocument();
    });

    test('renders Input Settings section', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Input Settings')).toBeInTheDocument();
    });

    test('renders Reset Settings section', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Reset Settings')).toBeInTheDocument();
    });

    test('renders footer text', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Making Games Accessible for Every Player')).toBeInTheDocument();
    });

    test('renders Dark Mode setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Dark Mode')).toBeInTheDocument();
        expect(screen.getByText('Switch to a dark colour scheme to reduce eye strain')).toBeInTheDocument();
    });

    test('renders Blue Light Filter setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Blue Light Filter')).toBeInTheDocument();
        expect(screen.getByText('Adds a warm tint to reduce blue light and eye strain at night')).toBeInTheDocument();
    });

    test('renders Text-to-Speech setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Text-to-Speech')).toBeInTheDocument();
        expect(screen.getByText('Have content read aloud as you navigate')).toBeInTheDocument();
    });

    test('renders Auto-Read on Hover setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Auto-Read on Hover')).toBeInTheDocument();
        expect(screen.getByText('Automatically read text when hovering over content')).toBeInTheDocument();
    });

    test('renders Voice Commands setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Voice Commands')).toBeInTheDocument();
        expect(screen.getByText('Microphone for hands-free navigation (always enabled)')).toBeInTheDocument();
    });

    test('renders Screen Reader Support setting', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Screen Reader Support')).toBeInTheDocument();
        expect(screen.getByText('Optimised for screen readers (always enabled)')).toBeInTheDocument();
    });

    test('renders Reset to Defaults button', () => {
        render(<SettingsPage />);
        expect(screen.getByText('Reset to Defaults')).toBeInTheDocument();
    });

    test('renders WCAG compliance link in footer', () => {
        render(<SettingsPage />);
        const link = screen.getByText('WCAG 2.1 Level AA');
        expect(link).toBeInTheDocument();
        expect(link).toHaveAttribute('href', 'https://accessibility-manual.dwp.gov.uk/accessibility-law/web-content-accessibility-guidelines');
    });

    test('renders voice command examples', () => {
        render(<SettingsPage />);
        expect(screen.getByText('"Go home"')).toBeInTheDocument();
        expect(screen.getByText('"Open community"')).toBeInTheDocument();
        expect(screen.getByText('"Search puzzle games"')).toBeInTheDocument();
    });

    test('dark mode toggle starts unchecked by default', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const darkModeToggle = toggles[0];
        expect(darkModeToggle).not.toBeChecked();
    });

    test('voice commands toggle is disabled (always on)', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const voiceCommandsToggle = toggles[4];
        expect(voiceCommandsToggle).toBeDisabled();
        expect(voiceCommandsToggle).toBeChecked();
    });

    test('screen reader toggle is disabled (always on)', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const screenReaderToggle = toggles[5];
        expect(screenReaderToggle).toBeDisabled();
        expect(screenReaderToggle).toBeChecked();
    });

    test('can toggle dark mode setting', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const darkModeToggle = toggles[0];

        expect(darkModeToggle).not.toBeChecked();

        fireEvent.click(darkModeToggle);
        expect(darkModeToggle).toBeChecked();
    });

    test('can toggle blue light filter setting', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const blueLightToggle = toggles[1];

        expect(blueLightToggle).not.toBeChecked();

        fireEvent.click(blueLightToggle);
        expect(blueLightToggle).toBeChecked();
    });

    test('can toggle text-to-speech setting', () => {
        render(<SettingsPage />);
        const toggles = screen.getAllByRole('checkbox');
        const ttsToggle = toggles[2];

        expect(ttsToggle).not.toBeChecked();

        fireEvent.click(ttsToggle);
        expect(ttsToggle).toBeChecked();
    });
});