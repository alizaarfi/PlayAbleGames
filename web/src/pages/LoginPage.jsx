//this is the Login Page
import React, { useState } from "react";
import { login as loginUser} from "../api/auth.js";
import {useAuth} from "../hooks/AuthProvider.jsx";

/**
 * Login page components
 * Handles user login with form validation and JWT authentication
 */

export default function LoginPage() {

    //get login function from auth context
    const { login: saveToken } = useAuth();
    const [form, setForm] = useState({
        username: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    //updates from state when user types into input fields
    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    //frontend validation for login
    const validateLogin = ()=>{
        if (!form.username || !form.password) {
            return "Please fill all the fields.";
        }
        if (form.password.length<8){
            return"Password must be at least 8 characters.";
        }
        return null;
    };

    //handles form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        //run validation
        const validationError= validateLogin();
        if(validationError){
            setError(validationError);
            setLoading(false);
            return;
        }
        try {
            //call backend login api
            const result = await loginUser(form);

            if (result.error) {
                setError(result.error);
            } else {
                //auth context
                saveToken(result.token);
                //redirect after login
                window.location.href = "/";
            }
        } catch (err) {
            //handle backend errors
            const backendError = err.response?.data?.error || "Login failed";
            setError(backendError);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">
            <div className="login-card">
                <button
                    className="login-card-close"
                    onClick={() => window.location.href = "/"}
                >
                    ✕
                </button>
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
