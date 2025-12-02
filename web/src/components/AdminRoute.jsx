import React from "react";
import { useAuth } from "../hooks/AuthProvider.jsx";
import { Navigate } from "react-router-dom";

/**
 * AdminRoute - Only allows users with ADMIN role
 * Redirects to login if not authenticated
 * Redirects to home if authenticated but not admin
 */
function AdminRoute({ children }) {
    const { token, role } = useAuth();

    // Wait for auth to load
    if (loading) {
        return <div className="loading-container">Loading...</div>;
    }

    // if not logged in then redirect to login
    if (!token) {
        localStorage.setItem("redirectAfterLogin", window.location.pathname);
        return <Navigate to="/login" replace />;
    }

    // if logged in but not admin then redirect to home with message
    if (role !== "ADMIN") {
        alert("Access denied. Admin privileges required.");
        return <Navigate to="/" replace />;
    }

    //if user is admin then show the page
    return children;
}

export default AdminRoute;