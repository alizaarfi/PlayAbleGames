import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SettingsPage from "./SettingsPage.jsx";
import useSpeech from "./UseSpeech.jsx";
import { FavoritesProvider } from "./FavoritesContext.jsx";
import FavoritesPage from "./FavoritesPage.jsx";   // imported as FavouritesPage (UK) :contentReference[oaicite:0]{index=0}

import "./style.css";

ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <BrowserRouter>
            <FavoritesProvider>
                <Routes>
                    {/* Homepage */}
                    <Route path="/" element={<HomePage />} />

                    {/* Game detail page */}
                    <Route path="/games/:id" element={<GamePage />} />

                    {/* Accessibility page */}
                    <Route path="/accessibility" element={<AccessibilityPage />} />

                    {/* Settings page */}
                    <Route path="/settings" element={<SettingsPage />} />

                    {/* Favorite page */}
                    <Route path="/favorites" element={<FavoritesPage />} />

                 </Routes>
             </FavoritesProvider>
        </BrowserRouter>
    </React.StrictMode>
);