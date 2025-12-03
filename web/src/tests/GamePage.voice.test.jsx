import React from "react";
import { render, screen, waitFor, act } from "@testing-library/react";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import "@testing-library/jest-dom";
import GamePage from "../GamePage";

/**
 * GamePage.voice.test tests the voice command event listeners for reviewing games (start, rate each accessibility rating, comment and submit and cancel)
  It tests that from Navbar the custom events are dispatched to the right command and action:
 * - REVIEW_START, REVIEW_RATING, REVIEW_COMMENT, REVIEW_SUBMIT and REVIEW_CANCEL
 */


function renderGamePageWithRoute(route = "/games/1") {
    return render(
        <MemoryRouter initialEntries={[route]}>
            <Routes>
                <Route path="/games/:id" element={<GamePage />} />
            </Routes>
        </MemoryRouter>
    );
}

beforeAll(() => {
    if (!window.requestAnimationFrame) {
        window.requestAnimationFrame = (cb) => setTimeout(cb, 0);
    }
});

describe('GamePage - Voice Command Event Listeners', () => {
    beforeEach(() => {
        jest.resetAllMocks();
        localStorage.clear();
        document.body.innerHTML = "";

        // Mock scrollIntoView as JSDOM doesn't support it as got an error before
        Element.prototype.scrollIntoView = jest.fn();

        global.fetch = jest.fn(() =>
            Promise.resolve({
                ok: true,
                json: async () => ({
                    id: 1,
                    name: "Test Game",
                    developer: "Test Studio",
                    ageRating: "12+",
                    description: "A test game",
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
                }),
            })
        );
    });

    test('REVIEW_START activates review mode when logged in', async () => {
        localStorage.setItem('token', 'mock-auth-token');
        renderGamePageWithRoute("/games/1");

        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        // Dispatch REVIEW_START command first
        act(() => {
            const reviewStartEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_START'
                }
            });
            window.dispatchEvent(reviewStartEvent);
        });

        await waitFor(() => {
            expect(screen.getByText(/Say a category and rating/i)).toBeInTheDocument();
        });
    });

    test('REVIEW_START shows toast if not logged in', async () => {
        renderGamePageWithRoute("/games/1");

        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        // Dispatch REVIEW_START without auth
        act(() => {
            const reviewStartEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_START'
                }
            });
            window.dispatchEvent(reviewStartEvent);
        });

        await waitFor(() => {
            // Should show toast requiring login
            const toast = document.querySelector('.toast-notice');
            expect(toast).toBeInTheDocument();
            expect(toast).toHaveTextContent(/log in to write a review/i);
        });
    });

    test('REVIEW_RATING updates hearing rating', async () => {
        localStorage.setItem('token', 'mock-auth-token');
        renderGamePageWithRoute("/games/1");

        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        act(() => {
            window.dispatchEvent(new CustomEvent('voiceCommand', {
                detail: { type: 'REVIEW_START' }
            }));
        });

        await waitFor(() => {
            expect(screen.getByText(/Say a category and rating/i)).toBeInTheDocument();
        });

        act(() => {
            const ratingEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_RATING',
                    target: 'hearing:5'
                }
            });
            window.dispatchEvent(ratingEvent);
        });

        await waitFor(() => {
            const toasts = document.querySelectorAll('.toast-notice');
            const latestToast = toasts[toasts.length - 1];
            expect(latestToast).toHaveTextContent(/hearing rated 5/i);
        });
    });

    test('REVIEW_COMMENT updates review text', async () => {
        localStorage.setItem('token', 'mock-auth-token');
        renderGamePageWithRoute("/games/1");
        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        act(() => {
            window.dispatchEvent(new CustomEvent('voiceCommand', {
                detail: { type: 'REVIEW_START' }
            }));
        });

        await waitFor(() => {
            expect(screen.getByText(/Say a category and rating/i)).toBeInTheDocument();
        });

        act(() => {
            const commentEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_COMMENT',
                    target: 'This is a great game'
                }
            });
            window.dispatchEvent(commentEvent);
        });

        await waitFor(() => {
            const textarea = screen.getByPlaceholderText(/Speak to add text/i);
            expect(textarea).toHaveValue('This is a great game');
        });
    });

    test('REVIEW_CANCEL exits review mode', async () => {
        localStorage.setItem('token', 'mock-auth-token');
        renderGamePageWithRoute("/games/1");
        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        act(() => {
            window.dispatchEvent(new CustomEvent('voiceCommand', {
                detail: { type: 'REVIEW_START' }
            }));
        });

        await waitFor(() => {
            expect(screen.getByText(/Say a category and rating/i)).toBeInTheDocument();
        });

        act(() => {
            const cancelEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_CANCEL'
                }
            });
            window.dispatchEvent(cancelEvent);
        });

        await waitFor(() => {
            expect(screen.queryByText(/Say a category and rating/i)).not.toBeInTheDocument();
            const toasts = document.querySelectorAll('.toast-notice');
            const latestToast = toasts[toasts.length - 1];
            expect(latestToast).toHaveTextContent(/Review mode cancelled/i);
        });
    });

    test('REVIEW_SUBMIT prevents submission when not logged in', async () => {
        renderGamePageWithRoute("/games/1");
        await waitFor(() => {
            expect(screen.getByText("Test Game")).toBeInTheDocument();
        });

        // Here we try to submit without being logged in
        act(() => {
            const submitEvent = new CustomEvent('voiceCommand', {
                detail: {
                    type: 'REVIEW_SUBMIT'
                }
            });
            window.dispatchEvent(submitEvent);
        });

        await waitFor(() => {
            const toast = document.querySelector('.toast-notice');
            expect(toast).toBeInTheDocument();
            expect(toast).toHaveTextContent(/Please log in to write a review/i);
        });
        expect(fetch).toHaveBeenCalledTimes(1); // Only the initial GET
    });
});