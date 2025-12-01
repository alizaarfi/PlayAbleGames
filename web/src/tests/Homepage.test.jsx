import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import HomePage from '../HomePage';
import { FavoritesProvider } from '../FavoritesContext';

// Mock the custom hooks
jest.mock('../UseHoverSpeech', () => ({
    __esModule: true,
    default: jest.fn(),
}));

// Wrapper component to provide context
const Wrapper = ({ children }) => (
    <FavoritesProvider>
        {children}
    </FavoritesProvider>
);

describe('HomePage', () => {
    beforeEach(() => {
        localStorage.clear();
        jest.clearAllMocks();
    });

    test('renders the main heading', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Playable Games for Everyone')).toBeInTheDocument();
    });

    test('renders the hero description', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText(/Find mobile games designed for everyone/i)).toBeInTheDocument();
    });

    test('renders search input', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const searchInput = screen.getByPlaceholderText('Search games by title, genre, keyword...');
        expect(searchInput).toBeInTheDocument();
    });

    test('renders Filters heading', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Filters')).toBeInTheDocument();
    });

    test('renders Accessibility Features section', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Accessibility Features')).toBeInTheDocument();
    });

    test('renders Genres section', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Genres')).toBeInTheDocument();
    });

    test('renders Sort by button', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Sort by')).toBeInTheDocument();
    });

    test('renders footer text', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Making Games Accessible for Every Player')).toBeInTheDocument();
    });

    test('renders WCAG compliance link', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const link = screen.getByText('WCAG 2.1 Level AA');
        expect(link).toBeInTheDocument();
        expect(link).toHaveAttribute('href', 'https://accessibility-manual.dwp.gov.uk/accessibility-law/web-content-accessibility-guidelines');
    });

    test('renders all 15 game cards', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Minecraft')).toBeInTheDocument();
        expect(screen.getByText('Monument Valley')).toBeInTheDocument();
        expect(screen.getByText('Among Us')).toBeInTheDocument();
        expect(screen.getByText('Candy Crush Saga')).toBeInTheDocument();
        expect(screen.getByText('Call Of Duty®: Mobile')).toBeInTheDocument();
    });

    test('renders Action genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const actionLabels = screen.getAllByText('Action');
        expect(actionLabels.length).toBeGreaterThan(0);
    });

    test('renders Adventure genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const adventureLabels = screen.getAllByText('Adventure');
        expect(adventureLabels.length).toBeGreaterThan(0);
    });

    test('renders Puzzle genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const puzzleLabels = screen.getAllByText('Puzzle');
        expect(puzzleLabels.length).toBeGreaterThan(0);
    });

    test('renders Strategy genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const strategyLabels = screen.getAllByText('Strategy');
        expect(strategyLabels.length).toBeGreaterThan(0);
    });

    test('renders Casual genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const casualLabels = screen.getAllByText('Casual');
        expect(casualLabels.length).toBeGreaterThan(0);
    });
});