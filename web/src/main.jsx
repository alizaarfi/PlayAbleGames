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
import CommunityPage from "./CommunityPage.jsx"

import "./style.css";
import Navbar from "./NavBar.jsx";
import {AuthProvider} from "./hooks/AuthProvider.jsx";
import AdminRoute from "./components/AdminRoute.jsx";


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

                        {/*Community Page*/}
                        <Route path="/community" element={<CommunityPage />} />

                        {/*signup page*/}
                        <Route path="/signup" element={<SignupPage/>} />

                        {/* AdminDashboard */}
                        <Route path="/admin" element={
                            <AdminRoute>
                            <AdminDashboard />
                            </AdminRoute>} />

                        {/* Flagged Reviews Page */}
                        <Route path="/flaggedReviews" element={
                            <AdminRoute>
                            <FlaggedReviews />
                            </AdminRoute>
                            } />

                        {/*login page*/}
                        <Route path="/login" element={<LoginPage/>} />

                        {/* Settings page */}
                        <Route path="/settings" element={<SettingsPage />} />

                        {/* Favorite page -requires login */}
                        <Route path="/favorites" element={
                            <ProtectedRoute>
                                <FavoritesPage/>
                            </ProtectedRoute>} />

                        {/* All Users Page */}
                        <Route path="/users" element={
                            <AdminRoute>
                                <AllUsersPage />
                            </AdminRoute>} />

                        {/* Individual User Page */}
                        <Route path="/users/:userId" element={
                            <AdminRoute>
                                <UserPage />
                            </AdminRoute>} />

                        {/* My Account - requires login */}
                        <Route
                            path="/myaccount"
                            element={
                                <ProtectedRoute>
                                    <MyAccountPage />
                                </ProtectedRoute>}/>
                    </Routes>
                </FavoritesProvider>
            </AuthProvider>
        </BrowserRouter>
   // </React.StrictMode>
);
