import React from "react";
import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import MyAccountPage from "../pages/MyAccountPage.jsx";
import { useAuth } from "../hooks/AuthProvider.jsx";
import { describe, test, expect, beforeEach, afterEach } from "@jest/globals";

// Mock AuthProvider hook
jest.mock("../hooks/AuthProvider.jsx");

// // Mock window.location
// delete window.location;
// window.location = { href: "" };
//
// // Mock fetch
// global.fetch = jest.fn();
//
// function renderPage() {
//     return render(<MyAccountPage />);
// }

describe("MyAccountPage", () => {

    const mockLogout = jest.fn();

    beforeEach(() => {
        jest.clearAllMocks();

        useAuth.mockReturnValue({
            token: "abc123",
            logout: mockLogout
        });

        global.fetch = jest.fn();
    });

    const mockProfile = {
        username: "testuser",
        email: "test@example.com",
        userId: 1,
        status: "Active"
    };

    function renderPage() {
        return render(<MyAccountPage />);
    }

    // TEST: Successful fetch
    test("fetches and displays user profile", async () => {
        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockProfile
        });

        renderPage();

        await waitFor(() => {
            expect(screen.getAllByText("testuser").length).toBeGreaterThan(0);
            expect(screen.getAllByText("test@example.com").length).toBeGreaterThan(0);
        });
    });

    // TEST: error handling
    test("shows error message if profile fetch fails", async () => {
        fetch.mockResolvedValueOnce({
            ok: false,
            json: async () => ({ message: "Something went wrong" })
        });

        renderPage();

        await waitFor(() => {
            expect(screen.getByText(/failed/i)).toBeInTheDocument();
        });
    });

    //TEST: Logout
    test("sign-out button triggers logout and redirects", async () => {

        fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockProfile
        });

        delete window.location;
        window.location = { href: "" };

        renderPage();

        const signOutButton = await screen.findByRole("button", {
            name: /sign out/i
        });

        fireEvent.click(signOutButton);

        expect(mockLogout).toHaveBeenCalledTimes(1);
    });
});