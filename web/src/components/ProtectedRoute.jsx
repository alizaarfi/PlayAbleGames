import React from 'react';
import { useAuth } from '../hooks/AuthProvider.jsx';
import { Navigate } from 'react-router-dom';

/**
 * Protected route - only allows logged-in users
 * Redirects to login if not authenticated
 */
export default function ProtectedRoute({ children }) {
    const { token , loading} = useAuth();

    //prevent redirect until token is loaded
    if (loading) {
        return <div>Loading...</div>;
    }

    // Not logged in - redirect to login page
    if (!token) {
        // Save current path so we can redirect back after login
        localStorage.setItem("redirectAfterLogin", window.location.pathname);
        return <Navigate to="/login" replace />;
    }

    // User is logged in - show the page
    return children;
}