//this is the registration page

import React, {useState} from "react";
import { signup } from "../api/auth.js";

export default function SignupPage(){
    const [form, setForm]=useState({
        username:"",
        email:"",
        password:""
    });

    const[message,setMessage] = useState(null);
    const[error, setError]= useState(null);

    const validateForm =() => {
        if (!form.email || !form.username|| !form.password){
            return "Please fill all the fields.";
        }
        //basic email validation
        const emailRegex =/\S+@\S+\.\S+/;
        if (!emailRegex.test(form.email)){
            return "Please enter a valid email address.";
        }

        if (form.password.length <6 ){
            return "Password must be at least 6 characters.";
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
            const result =await signup(form);
            setMessage(result.message);
        } catch (e){
            setError("Registration failed");
        }

    };

    return(
        <div style={{maxWidth: 400, margin:"50px auto"}}>
            <h2>Create an Account</h2>

            {error && <p style={{color:"red"}}>{error}</p>}
            {message && <p style={{ color: "green" }}>{message}</p>}

            <form onSubmit={handleSubmit}>
                <input
                    placeholder="Email"
                    type="email"
                    value={form.email}
                    onChange={e=> setForm({...form, email:e.target.value})}/>
                <br/>
                <input
                placeholder="Username"
                value={form.username}
                onChange={e=> setForm({...form,username:e.target.value})}/>
                <br/>

                <input
                placeholder="Password"
                type="password"
                value={form.password}
                onChange={e=> setForm({...form, password:e.target.value})}
                />
                <br/>

                <button type="submit">Sign Up!</button>
            </form>
        </div>
    );
}