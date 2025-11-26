//this is the registration page

import React, {useState} from "react";
import { signup } from "../api/auth.js";

export default function SignupPage(){
    const [form, setForm]=useState({
        username:"",
        email:"",
        password:""
    });

    const [loading, setLoading]=useState(false);
    const[message,setMessage] = useState(null);
    const[error, setError]= useState(null);

    const handleChange = (e) =>{
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const validateForm =() => {
        if (!form.email || !form.username|| !form.password){
            return "Please fill all the fields.";
        }
        //basic email validation
        const emailRegex =/\S+@\S+\.\S+/;
        if (!emailRegex.test(form.email)){
            return "Please enter a valid email address.";
        }

        if (form.password.length <8 ){
            return "Password must be at least 8 characters.";
        }
        if (!/[A-Z]/.test(form.password)) {
            return "Password must contain at least one uppercase letter.";
        }

        if (!/[0-9]/.test(form.password)) {
            return "Password must contain at least one number.";
        }
        return null
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setMessage(null);

        //frontend validation first
        const validationError = validateForm();
        if (validationError){
            setError(validationError);
            return;
        }

        try{
            setLoading(true)
            const result =await signup(form);
            if(result.error){
                setError(result.error);
            } else{
                setMessage(result.message || "Registration Successful!" );
                // Redirect after 1 second
                setTimeout(() => {
                    window.location.href = "/login";
                }, 1000);
            }
        } catch (e){
            const backendError = e.response?.data?.error || "Registration failed";
            setError(backendError);

        } finally{
            setLoading(false);
        }
    };

    return(
        <div className="signup-page">
            <button
            className="login-card-close"
            onClick={() => window.location.href = "/"}
        >
            ✕
        </button>
            <div className="signup-card">
                <h2>Create an Account</h2>
                <p className="signup-subtitle">
                    Join the community and start exploring games, reviews, and more.
                </p>
                <form onSubmit={handleSubmit} className="signup-form">

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
                        <label>Email</label>
                        <input
                            type="email"
                            name="email"
                            placeholder="you@example.com"
                            value={form.email}
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
                    {message && <p className="success-msg">{message}</p>}

                    <button
                        type="submit"
                        className={`signup-btn ${loading ? "loading" : ""}`}
                        disabled={loading}
                    >
                        {loading ? "Creating Account..." : "Sign Up"}
                    </button>

                    <p className="login-redirect">
                        Already have an account?{" "}
                        <a href="/login" className="login-link">Log in</a>
                    </p>
                </form>
            </div>
        </div>

    );
}