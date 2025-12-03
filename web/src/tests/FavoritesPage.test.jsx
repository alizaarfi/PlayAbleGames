import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import FavoritesPage from "../FavoritesPage.jsx";
import { useFavorites } from "../FavoritesContext.jsx";

// Mock games list used by the component
jest.mock("../HomePage.jsx", () => ({
    games: [
        {
            id: 1,
            title: "Game One",
            description: "First test game",
            image: "img1.png"
        },
        {
            id: 2,
            title: "Game Two",
            description: "Second test game",
            image: "img2.png"
        }
    ]
}));

// Mock the favorites context hook
jest.mock("../FavoritesContext.jsx", () => ({
    useFavorites: jest.fn()
}));

describe("FavoritesPage", () => {
    beforeEach(() => {
        // Mock window.location.href to prevent navigation errors
        delete window.location;
        window.location = { href: jest.fn() };
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    // Verifies that the component displays a loading indicator while backend favourites are being retrieved.
    test("displays loading state when loading is true", () => {
        useFavorites.mockReturnValue({
            favorites: [],
            loading: true,
            toggleFavorite: jest.fn()
        });

        render(<FavoritesPage />);

        expect(screen.getByText(/Loading favorites/i)).toBeInTheDocument();
    });

    // Verifies that the component displays helper text when the user has no favourite games.
    test("shows empty state message when no favorites exist", () => {
        useFavorites.mockReturnValue({
            favorites: [],
            loading: false,
            toggleFavorite: jest.fn()
        });

        render(<FavoritesPage />);

        // Use a more flexible text matcher that handles special characters
        expect(
            screen.getByText((content, element) => {
                return content.includes("haven") && content.includes("t added any favorites yet");
            })
        ).toBeInTheDocument();
    });

    // Verifies that only games whose IDs appear in the favorites list are rendered as favourite game cards.
    test("renders favourite game cards when favorites exist", () => {
        useFavorites.mockReturnValue({
            favorites: [1],
            loading: false,
            toggleFavorite: jest.fn()
        });

        render(<FavoritesPage />);

        expect(screen.getByText("Game One")).toBeInTheDocument();
        expect(screen.queryByText("Game Two")).not.toBeInTheDocument();
    });

    // Verifies that clicking the heart button removes a game by invoking the toggleFavorite callback with the correct ID.
    test("invokes toggleFavorite when remove button is clicked", () => {
        const toggle = jest.fn();

        useFavorites.mockReturnValue({
            favorites: [2],
            loading: false,
            toggleFavorite: toggle
        });

        render(<FavoritesPage />);

        const removeButton = screen.getByRole("button", {
            name: "Remove from favorites"
        });

        fireEvent.click(removeButton);

        expect(toggle).toHaveBeenCalledWith(2, expect.any(Object));
    });
});