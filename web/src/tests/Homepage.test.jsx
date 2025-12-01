import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
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

    test('renders sort dropdown with default "Most Popular" selected', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const dropdown = screen.getByRole('combobox');
        expect(dropdown).toHaveValue('ratingDesc');
    });

    test('sort dropdown contains "Rating (low → high)" option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Rating (low → high)')).toBeInTheDocument();
    });

    test('sort dropdown contains "Most reviewed" option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Most reviewed')).toBeInTheDocument();
    });

    test('sort dropdown contains "Least reviewed" option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Least reviewed')).toBeInTheDocument();
    });

    test('sort dropdown contains "Title (A → Z)" option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Title (A → Z)')).toBeInTheDocument();
    });

    test('sort dropdown contains "Title (Z → A)" option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Title (Z → A)')).toBeInTheDocument();
    });

    test('search input starts empty', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const searchInput = screen.getByPlaceholderText('Search games by title, genre, keyword...');
        expect(searchInput).toHaveValue('');
    });

    test('can type in search input', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const searchInput = screen.getByPlaceholderText('Search games by title, genre, keyword...');

        fireEvent.change(searchInput, { target: { value: 'Minecraft' } });
        expect(searchInput).toHaveValue('Minecraft');
    });

    test('search filters games by title', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const searchInput = screen.getByPlaceholderText('Search games by title, genre, keyword...');

        fireEvent.change(searchInput, { target: { value: 'Minecraft' } });

        expect(screen.getByText('Minecraft')).toBeInTheDocument();
        expect(screen.queryByText('Among Us')).not.toBeInTheDocument();
    });

    test('can toggle Vision Support filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const checkboxes = screen.getAllByRole('checkbox');
        const visionCheckbox = checkboxes[0];

        expect(visionCheckbox).not.toBeChecked();

        fireEvent.click(visionCheckbox);
        expect(visionCheckbox).toBeChecked();
    });

    test('can toggle Action genre filter', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const checkboxes = screen.getAllByRole('checkbox');
        const actionCheckbox = checkboxes[5];

        expect(actionCheckbox).not.toBeChecked();

        fireEvent.click(actionCheckbox);
        expect(actionCheckbox).toBeChecked();
    });

    test('toggling filter updates displayed games', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const checkboxes = screen.getAllByRole('checkbox');
        const puzzleCheckbox = checkboxes[7];

        expect(screen.getByText('Minecraft')).toBeInTheDocument();
        expect(screen.getByText('Monument Valley')).toBeInTheDocument();

        fireEvent.click(puzzleCheckbox);

        expect(screen.getByText('Monument Valley')).toBeInTheDocument();
        expect(screen.queryByText('Call Of Duty®: Mobile')).not.toBeInTheDocument();
    });

    test('can change sort option', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const dropdown = screen.getByRole('combobox');

        fireEvent.change(dropdown, { target: { value: 'titleAsc' } });
        expect(dropdown).toHaveValue('titleAsc');
    });

    test('sorting by title A-Z changes game order', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const dropdown = screen.getByRole('combobox');

        fireEvent.change(dropdown, { target: { value: 'titleAsc' } });

        const gameCards = screen.getAllByRole('article');
        const firstGameTitle = gameCards[0].querySelector('h3').textContent;

        expect(firstGameTitle).toBe('A Blind Legend');
    });

    test('each game card displays title', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('Minecraft')).toBeInTheDocument();
    });

    test('each game card displays description', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText(/A creative sandbox game with flexible controls/i)).toBeInTheDocument();
    });

    test('each game card displays rating', () => {
        render(<HomePage />, { wrapper: Wrapper });
        expect(screen.getByText('4.5 (1,231)')).toBeInTheDocument();
    });

    test('each game card displays tags', () => {
        render(<HomePage />, { wrapper: Wrapper });
        const tags = screen.getAllByText('Sandbox');
        expect(tags.length).toBeGreaterThan(0);
    });
});