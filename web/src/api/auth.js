export async function signup(data){
    const res= await fetch("http://localhost:8081/user/register",{
        method: "POST",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify(data)});

    if (!res.ok){
        const text = await res.text();
        throw new Error( text || "Signup Failed");
    }

    return res.json();
}