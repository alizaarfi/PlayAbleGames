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
    });}