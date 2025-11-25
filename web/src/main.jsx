import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SignupPage from "./pages/SignupPage.jsx";
import LoginPage from "./pages/LoginPage";
import SettingsPage from "./SettingsPage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import useSpeech from "./UseSpeech.jsx";

import "./style.css";
import {AuthProvider} from "./hooks/AuthProvider.jsx";


ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <AuthProvider>
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

                {/* Load AdminDashboard for /admin */}
                <Route path="/admin" element={<AdminDashboard />} />

                {/*login page*/}
                <Route path="/login" element={<LoginPage/>} />
                {/* Settings page */}
                <Route path="/settings" element={<SettingsPage />} />

            </Routes>
            </BrowserRouter>
        </AuthProvider>
    </React.StrictMode>
);