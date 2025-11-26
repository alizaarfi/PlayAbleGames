import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SignupPage from "./pages/SignupPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import SettingsPage from "./SettingsPage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import useSpeech from "./UseSpeech.jsx";
import { FavoritesProvider } from "./FavoritesContext.jsx";
import FavoritesPage from "./FavoritesPage.jsx";   // imported as FavouritesPage (UK) :contentReference[oaicite:0]{index=0}

import "./style.css";
import {AuthProvider} from "./hooks/AuthProvider.jsx";


ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <FavoritesProvider>
                    <Routes>
                        {/* Homepage */}
                        <Route path="/" element={<HomePage />} />

                        {/* Game detail page */}
                        <Route path="/games/:id" element={<GamePage />} />

                        {/* Accessibility page */}
                        <Route path="/accessibility" element={<AccessibilityPage />} />

                        {/*signup page*/}
                        <Route path="/signup" element={<SignupPage/>} />

                        {/* Load AdminDashboard for /admin */}
                        <Route path="/admin" element={<AdminDashboard />} />

                        {/*login page*/}
                        <Route path="/login" element={<LoginPage/>} />

                        {/* Settings page */}
                        <Route path="/settings" element={<SettingsPage />} />

                        {/* Favorite page */}
                        <Route path="/favorites" element={<FavoritesPage />} />

                    </Routes>
                </FavoritesProvider>
            </AuthProvider>
        </BrowserRouter>
    </React.StrictMode>
);