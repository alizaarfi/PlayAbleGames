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

    const handleSubmit = async (e) => {
        e.preventDefault();

        try{
            const result =await signup(form);
            setMessage(result.message);
        } catch (e){
            setMessage("Registration failed");
        }

    };

    return(
        <div style={{maxWidth: 400, margin:"50px auto"}}>
            <h2>Create an Account</h2>

            {message && <p>{message}</p>}

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