import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [token, setToken] = useState(null);

    useEffect(() => {
        const saved = localStorage.getItem("token");
        if (saved) setToken(saved);
    }, []);

    const login = (jwt) => {
        localStorage.setItem("token", jwt);
        setToken(jwt);
    };

    const logout = async () => {
        try {
            if (token) {
                await fetch("/user/logout", {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
            }
        } catch (err) {
            console.error("Logout error:", err);
        }

        localStorage.removeItem("token");
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ token, isAuthenticated: !!token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
