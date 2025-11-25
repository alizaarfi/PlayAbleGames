import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SignupPage from "./pages/SignupPage.jsx";
import FlaggedReviews from "./FlaggedReviews.jsx";


import "./style.css";

ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                {/* Homepage */}
                <Route path="/" element={<HomePage />} />

                {/* Game detail page */}
                <Route path="/games/:id" element={<GamePage />} />

                {/*signup page*/}
                <Route path="/signup" element={<SignupPage/>} />

                {/* Accessibility page */}
                <Route path="/accessibility" element={<AccessibilityPage />} />

                {/* AdminDashboard */}
                <Route path="/admin" element={<AdminDashboard />} />

                {/* Flagged Reviews Page */}
                <Route path="/flaggedReviews" element={<FlaggedReviews />} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
