export async function signup(data) {
    try {
        const res = await fetch("http://localhost:8081/user/register", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });


        const raw = await res.text();

        let parsed;
        try {
            parsed = raw ? JSON.parse(raw) : null;
        } catch (parseErr) {
            parsed = null;
        }

        if (!res.ok) {
            // common fields, fallback to raw test
            const errorMessage =
                (parsed && (parsed.error || parsed.message)) ||
                raw ||
                `Request failed with status ${res.status}`;
            return {success: false, error: errorMessage, status: res.status, raw, parsed};
        }
        //success
        return {success: true, data: parsed ?? raw, status: res.status, raw, parsed};
    } catch (networkErr) {
        //network error/other issues
        return {success: false, error: networkErr.message || String(networkErr)};
    }
}


export async function login(credentials){
    try{
        const res = await fetch("http://localhost:8081/user/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(credentials)
        });

        const data= await res.json();

        if (!res.ok) {
            return {error: data.message || 'Login failed'}
            // Return the specific error message from the backend
        }

            //return both token and role:
        return{
            token: data.token,
            role: data.role,
            username: data.username,
            message: data.message
        };
    } catch (err){
        return{ error: 'Network error. Please try again'}
    }
}

export async function logout(){
    const token= localStorage.getItem("token");

    const res = await fetch("http://localhost:8081/user/logout", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });
    localStorage.removeItem("token");
    return await res.json();
}