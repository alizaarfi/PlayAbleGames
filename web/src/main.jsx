import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SignupPage from "./pages/SignupPage.jsx";


import "./style.css";
import Navbar from "./NavBar.jsx";

ReactDOM.createRoot(document.getElementById("app")).render(
    // <React.StrictMode> // disabled for the mic to work
        <BrowserRouter>
            {/* Moved navbar outside of routes to ensures voice control persistent across all pages*/}
            <Navbar />
            <Routes>
                {/* Homepage */}
                <Route path="/" element={<HomePage />} />

                {/* Game detail page */}
                <Route path="/games/:id" element={<GamePage />} />

                {/*signup page*/}
                <Route path="/signup" element={<SignupPage/>} />

                {/* Accessibility page */}
                <Route path="/accessibility" element={<AccessibilityPage />} />

                {/* Load AdminDashboard for /admin */}
                <Route path="/admin" element={<AdminDashboard />} />
            </Routes>
        </BrowserRouter>
    // </React.StrictMode>
);

