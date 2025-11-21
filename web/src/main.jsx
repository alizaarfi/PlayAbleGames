import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";

import "./style.css";

ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                {/* Homepage */}
                <Route path="/" element={<HomePage />} />

                {/* Game detail page */}
                <Route path="/games/:id" element={<GamePage />} />

                {/* Accessibility page */}
                <Route path="/accessibility" element={<AccessibilityPage />} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);