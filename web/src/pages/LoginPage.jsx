import React, { useState } from "react";
import { login } from "../api/auth.js";

export default function LoginPage() {
    const [form, setForm] = useState({
        username: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const result = await login(form);

            if (result.error) {
                setError(result.error);
            } else {
                localStorage.setItem("token", result.token);
                window.location.href = "/profile";
            }
        } catch (err) {
            const backendError = err.response?.data?.error || "Login failed";
            setError(backendError);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">
            <div className="login-card">
                <h2>Welcome Back</h2>
                <p className="login-subtitle">Log in to continue exploring games and reviews.</p>

                <form onSubmit={handleSubmit} className="login-form">

                    <div className="form-group">
                        <label>Username</label>
                        <input
                            type="text"
                            name="username"
                            placeholder="Enter your username"
                            value={form.username}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            name="password"
                            placeholder="••••••••"
                            value={form.password}
                            onChange={handleChange}
                        />
                    </div>

                    {error && <p className="error-msg">{error}</p>}

                    <button
                        type="submit"
                        className={`login-btn ${loading ? "loading" : ""}`}
                        disabled={loading}
                    >
                        {loading ? "Logging in..." : "Log In"}
                    </button>

                    <p className="signup-redirect">
                        Don't have an account?{" "}
                        <a href="/signup" className="signup-link">Sign up</a>
                    </p>
                </form>
            </div>
        </div>
    );
}
