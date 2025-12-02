import React from "react";
import {
    render,
    screen,
    waitFor,
    fireEvent,
} from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import GamePage from "../GamePage"; // change path if needed

// Helper: render GamePage with router so useParams works.
function renderGamePageWithRoute(route = "/games/1") {
    return render(
        <MemoryRouter initialEntries={[route]}>
            <Routes>
                <Route path="/games/:id" element={<GamePage />} />
            </Routes>
        </MemoryRouter>
    );
}

// Polyfill used by showToast
beforeAll(() => {
    if (!window.requestAnimationFrame) {
        window.requestAnimationFrame = (cb) => setTimeout(cb, 0);
    }
});

beforeEach(() => {
    jest.resetAllMocks();
    localStorage.clear();
    document.body.innerHTML = "";
    global.fetch = jest.fn();
});

test("loads game data and renders it after showing loading state", async () => {
    // Integration test: component + router + mocked backend.

    const fakeGame = {
        id: 1,
        name: "Test Game",
        developer: "Test Studio",
        ageRating: "12+",
        description: "A test game used for automated UI testing.",
        releaseDate: "2024-01-01",
        linkToStore: "https://example.com/game",
        aboutVision: "Vision info",
        aboutHearing: "Hearing info",
        aboutCognitive: "Cognitive info",
        aboutSpeech: "Speech info",
        aboutMotor: "Motor info",
        reviews: [],
        gallery: [],
        avgGameplayRating: 4,
    };

    fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => fakeGame,
    });

    renderGamePageWithRoute("/games/1");

    // Initial loading state
    expect(screen.getByText(/Loading game…/i)).toBeInTheDocument();

    // Then renders game title from API
    await waitFor(() => {
        expect(screen.getByText("Test Game")).toBeInTheDocument();
    });

    expect(fetch).toHaveBeenCalledWith(
        "http://localhost:8081/api/games/1"
    );
});

test("shows error UI when the game fetch fails", async () => {
    // Integration test: error-handling branch for backend failure.

    fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        text: async () => "Server error",
    });

    renderGamePageWithRoute("/games/1");

    await waitFor(() => {
        expect(screen.getByText(/Error:/i)).toBeInTheDocument();
    });

    expect(screen.getByText(/HTTP 500/i)).toBeInTheDocument();
});

test("prevents review submission when user is not logged in and shows toast", async () => {
    // Unit test: auth guard in handleSubmitReview.

    const fakeGame = {
        id: 1,
        name: "Test Game",
        developer: "Test Studio",
        ageRating: "12+",
        description: "A test game used for automated UI testing.",
        releaseDate: "2024-01-01",
        linkToStore: null,
        aboutVision: null,
        aboutHearing: null,
        aboutCognitive: null,
        aboutSpeech: null,
        aboutMotor: null,
        reviews: [],
        gallery: [],
        avgGameplayRating: 3,
    };

    fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => fakeGame,
    });

    //Simulate "not logged in"
    localStorage.removeItem("authToken");
    localStorage.removeItem("token");
    localStorage.removeItem("jwt");

    renderGamePageWithRoute("/games/1");

    await waitFor(() => {
        expect(screen.getByText("Test Game")).toBeInTheDocument();
    });

    const submitButton = screen.getByRole("button", { name: /submit/i });

    fireEvent.click(submitButton);

    // Only initial GET should have been called; no POST.
    expect(fetch).toHaveBeenCalledTimes(1);

    const toast = document.querySelector(".toast-notice");
    expect(toast).toBeInTheDocument();
    expect(toast).toHaveTextContent("You must log in to leave a review.");
});
