import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ProtectedRoute from './components/ProtectedRoute.jsx';

import HomePage from "./HomePage.jsx";
import GamePage from "./GamePage.jsx";
import AccessibilityPage from "./AccessibilityPage.jsx";
import SignupPage from "./pages/SignupPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import SettingsPage from "./SettingsPage.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import FlaggedReviews from "./FlaggedReviews.jsx";
import useSpeech from "./UseSpeech.jsx";
import { FavoritesProvider } from "./FavoritesContext.jsx";
import FavoritesPage from "./FavoritesPage.jsx";   // imported as FavouritesPage (UK) :contentReference[oaicite:0]{index=0}
import AllUsersPage from "./AllUsers.jsx";
import UserPage from "./IndividualUser.jsx";
import MyAccountPage from './pages/MyAccountPage.jsx';

import "./style.css";
import Navbar from "./NavBar.jsx";
import {AuthProvider} from "./hooks/AuthProvider.jsx";


ReactDOM.createRoot(document.getElementById("app")).render(
    // <React.StrictMode> // disabled for the mic to work
        <BrowserRouter>
            <AuthProvider>
                <FavoritesProvider>
                    {/* Navbar outside Routes - persistent across all pages for voice control */}
                    <Navbar />

                    <Routes>
                        {/* Homepage */}
                        <Route path="/" element={<HomePage />} />

                        {/* Game detail page */}
                        <Route path="/games/:id" element={<GamePage />} />

                        {/* Accessibility page */}
                        <Route path="/accessibility" element={<AccessibilityPage />} />

                        {/*signup page*/}
                        <Route path="/signup" element={<SignupPage/>} />

                        {/* AdminDashboard */}
                        <Route path="/admin" element={<AdminDashboard />} />

                        {/* Flagged Reviews Page */}
                        <Route path="/flaggedReviews" element={<FlaggedReviews />} />


                        {/*login page*/}
                        <Route path="/login" element={<LoginPage/>} />

                        {/* Settings page */}
                        <Route path="/settings" element={<SettingsPage />} />

                        {/* Favorite page */}
                        <Route path="/favorites" element={<FavoritesPage />} />

                        {/* All Users Page */}
                        <Route path="/users" element={<AllUsersPage />} />

                        {/* Individual User Page */}
                        <Route path="/users/:userId" element={<UserPage />} />

                        <Route
                            path="/myaccount"
                            element={
                                    <MyAccountPage />
                            }
                        />
                    </Routes>
                </FavoritesProvider>
            </AuthProvider>
        </BrowserRouter>
    </React.StrictMode>
);
