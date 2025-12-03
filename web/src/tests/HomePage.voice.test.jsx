import React from 'react';
import { render, screen, waitFor, act } from '@testing-library/react';
import '@testing-library/jest-dom';
import HomePage from '../HomePage';
import { FavoritesProvider } from '../FavoritesContext';

/**
 * HomePage.voice.test tests the voice command functionality on the home page
 * Tests the event listeners on HomePage that are dispatched when triggered from Navbar :
 * - SEARCH, FILTER, SORT and FAVOURITE.
 */


jest.mock('../UseHoverSpeech', () => ({
    __esModule: true,
    default: jest.fn(),
}));

const Wrapper = ({ children }) => (
    <FavoritesProvider>
        {children}
    </FavoritesProvider>
);

describe('HomePage - Voice Command Event Listeners', () => {
    beforeEach(() => {
        localStorage.clear();
        jest.clearAllMocks();
    });

    test('SEARCH voice command updates search input', async () => {
        render(<HomePage />, { wrapper: Wrapper });

        const searchInput = screen.getByPlaceholderText('Search games by title, genre, keyword...');
        expect(searchInput).toHaveValue('');

        act(() => {
            const searchEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'SEARCH',
                    searchQuery: 'Minecraft'
                }
            });
            window.dispatchEvent(searchEvent);
        });

        await waitFor(() => {
            expect(searchInput).toHaveValue('Minecraft');
        });

        expect(screen.getByText('Minecraft')).toBeInTheDocument();
        expect(screen.queryByText('Among Us')).not.toBeInTheDocument();
    });

    test('FILTER voice command toggles Vision Support filter', async () => {
        render(<HomePage />, { wrapper: Wrapper });

        const checkboxes = screen.getAllByRole('checkbox');
        const visionCheckbox = checkboxes[0]; // First checkbox is Vision Support

        expect(visionCheckbox).not.toBeChecked();

        act(() => {
            const filterEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'FILTER',
                    filterKey: 'visionSupport',
                    filterName: 'Vision Support'
                }
            });
            window.dispatchEvent(filterEvent);
        });

        await waitFor(() => {
            expect(visionCheckbox).toBeChecked();
        });
    });

    test('FILTER voice command toggles Puzzle genre filter', async () => {
        render(<HomePage />, { wrapper: Wrapper });

        const checkboxes = screen.getAllByRole('checkbox');
        const puzzleCheckbox = checkboxes[7];

        expect(puzzleCheckbox).not.toBeChecked();

        act(() => {
            const filterEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'FILTER',
                    filterKey: 'puzzle',
                    filterName: 'Puzzle'
                }
            });
            window.dispatchEvent(filterEvent);
        });

        await waitFor(() => {
            expect(puzzleCheckbox).toBeChecked();
        });

        // Verify filtering works - Monument Valley is a puzzle game
        expect(screen.getByText('Monument Valley')).toBeInTheDocument();
        expect(screen.queryByText('Call Of Duty®: Mobile')).not.toBeInTheDocument();
    });

    test('SORT voice command changes sort dropdown to Title A-Z', async () => {
        render(<HomePage />, { wrapper: Wrapper });

        // default sort opiton is most popular therefore need to change
        const dropdown = screen.getByRole('combobox');
        expect(dropdown).toHaveValue('ratingDesc');

        act(() => {
            const sortEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'SORT',
                    sortValue: 'titleAsc',
                    sortName: 'Title (A → Z)'
                }
            });
            window.dispatchEvent(sortEvent);
        });

        await waitFor(() => {
            expect(dropdown).toHaveValue('titleAsc');
        });

        // Verify sorting works - first game should be "A Blind Legend"
        const gameCards = screen.getAllByRole('article');
        const firstGameTitle = gameCards[0].querySelector('h3').textContent;
        expect(firstGameTitle).toBe('A Blind Legend');
    });

    test('FAVOURITE voice command toggles favourite for first game', async () => {
        localStorage.setItem('token', 'mock-auth-token');

        render(<HomePage />, { wrapper: Wrapper });

        const favoriteButtons = screen.getAllByLabelText('Add to favorites');
        expect(favoriteButtons.length).toBeGreaterThan(0);

        // Dispatch FAVOURITE voice command for first game (Minecraft, id: 1)
        act(() => {
            const favouriteEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'FAVOURITE',
                    gameId: 1,
                    gameName: 'Minecraft'
                }
            });
            window.dispatchEvent(favouriteEvent);
        });

        await waitFor(() => {
            expect(screen.getByLabelText('Remove from favorites')).toBeInTheDocument();
        });
    });
});