import React from 'react';
import { render, screen } from '@testing-library/react';
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
});