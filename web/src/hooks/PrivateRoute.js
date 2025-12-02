import React from "react";
import { useAuth } from "../hooks/AuthProvider.jsx"; // Fix: use useAuth, not authProvider
import { Navigate } from "react-router-dom";

function PrivateRoute({ children }) {
    const { token } = useAuth(); // Fix: destructure token from useAuth()

    if (!token) {
        localStorage.setItem("redirectAfterLogin", window.location.pathname);
        return <Navigate to="/login" replace />;
    }

    return children;
}

export default PrivateRoute;