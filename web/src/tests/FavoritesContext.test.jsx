import React from "react";
import { render, waitFor, act, screen } from "@testing-library/react";
import { FavoritesProvider, useFavorites } from "../FavoritesContext.jsx";

// Exposes the context value via a callback.
function ContextConsumer({ onValue }) {
    const value = useFavorites();
    React.useEffect(() => {
        onValue(value);
    }, [value, onValue]);
    return <div data-testid="consumer">Context consumer</div>;
}

describe("FavoritesContext", () => {
    let originalFetch;
    let originalRAF;

    // Preserve global references for later restoration.
    beforeAll(() => {
        originalFetch = global.fetch;
        originalRAF = global.requestAnimationFrame;
    });

    // Provide a controlled environment for each test.
    beforeEach(() => {
        localStorage.clear();
        jest.clearAllMocks();

        global.fetch = jest.fn();

        // Ensure requestAnimationFrame is deterministic in tests.
        global.requestAnimationFrame = jest.fn((cb) => {
            if (typeof cb === "function") cb();
            return 0;
        });
    });

    // Remove incidental mocks created during a test.
    afterEach(() => {
        jest.clearAllMocks();
    });

    // Restore global state after the suite completes.
    afterAll(() => {
        global.fetch = originalFetch;
        if (originalRAF) {
            global.requestAnimationFrame = originalRAF;
        } else {
            delete global.requestAnimationFrame;
        }
    });

    // Verifies that initial data is retrieved when a token exists.
    test("loads favourites from backend when token is present", async () => {
        localStorage.setItem("token", "test-token");

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => [1, 3, 5],
        });

        let contextValue = null;

        render(
            <FavoritesProvider>
                <ContextConsumer onValue={(v) => { contextValue = v; }} />
            </FavoritesProvider>
        );

        await screen.findByTestId("consumer");

        await waitFor(() => {
            expect(contextValue).not.toBeNull();
            expect(contextValue.loading).toBe(false);
            expect(contextValue.favorites).toEqual([1, 3, 5]);
        });

        expect(global.fetch).toHaveBeenCalledWith(
            "http://localhost:8081/api/favorites",
            {
                method: "GET",
                headers: { Authorization: "Bearer test-token" },
            }
        );
    });

    // Ensures a new favourite triggers optimistic local update and a POST request.
    test("toggleFavorite adds id and sends POST when not already favourited", async () => {
        localStorage.setItem("token", "test-token");

        global.fetch
            .mockResolvedValueOnce({ ok: true, json: async () => [] })
            .mockResolvedValueOnce({ ok: true, text: async () => "" });

        let contextValue = null;

        render(
            <FavoritesProvider>
                <ContextConsumer onValue={(v) => { contextValue = v; }} />
            </FavoritesProvider>
        );

        await screen.findByTestId("consumer");

        await waitFor(() => {
            expect(contextValue).not.toBeNull();
            expect(contextValue.loading).toBe(false);
        });

        await act(async () => {
            await contextValue.toggleFavorite(7, { stopPropagation: () => {} });
        });

        expect(contextValue.favorites).toContain(7);
        expect(global.fetch).toHaveBeenLastCalledWith(
            "http://localhost:8081/api/favorites/7",
            {
                method: "POST",
                headers: { Authorization: "Bearer test-token" },
            }
        );
    });

    // Confirms that unauthenticated users cannot issue server updates.
    test("toggleFavorite does nothing server-side when user is not logged in", async () => {
        let contextValue = null;

        render(
            <FavoritesProvider>
                <ContextConsumer onValue={(v) => { contextValue = v; }} />
            </FavoritesProvider>
        );

        await screen.findByTestId("consumer");

        await waitFor(() => {
            expect(contextValue).not.toBeNull();
        });

        const previous = contextValue.favorites.slice();

        await act(async () => {
            await contextValue.toggleFavorite(5, { stopPropagation: () => {} });
        });

        expect(global.fetch).not.toHaveBeenCalled();
        expect(contextValue.favorites).toEqual(previous);
    });
});
