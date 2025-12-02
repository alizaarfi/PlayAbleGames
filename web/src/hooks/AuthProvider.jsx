import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [token, setToken] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const saved = localStorage.getItem("token");
        if (saved){
            setToken(saved);
        }
        setLoading(false);
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
        }finally {
            localStorage.removeItem("token");
            setToken(null);

            return Promise.resolve();
        }
    };

    return (
        <AuthContext.Provider value={{ token,loading, isAuthenticated: !!token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
