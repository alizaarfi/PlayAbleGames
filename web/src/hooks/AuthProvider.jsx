import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [token, setToken] = useState(null);
    const [loading, setLoading] = useState(true);
    const [role, setRole]= useState(null);


    useEffect(() => {
        const saved = localStorage.getItem("token");
        if (saved){
            setToken(saved);
        }
        setLoading(false);

        const savedToken = localStorage.getItem("token");
        const savedRole = localStorage.getItem("role");
        if (savedToken) {
            setToken(savedToken);
            setRole(savedRole);
        }
    }, []);

    const login = (newToken, newRole) => {
        setToken(newToken);
        setRole(newRole);
        localStorage.setItem("token", newToken);
        localStorage.setItem("role", newRole);
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
            localStorage.removeItem("role");
            localStorage.removeItem("redirectAfterLogin"); // cleaning up redirect
            setToken(null);
            setRole(null);

            return Promise.resolve();
        }
    };

    const isAdmin = () =>{
        return role === "ADMIN"
    }

    return (
        <AuthContext.Provider value={{ token,loading, isAuthenticated: !!token, login, logout }}>
        <AuthContext.Provider value={{
            token,
            role,
            isAuthenticated: !!token,
            isAdmin,
            login,
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
