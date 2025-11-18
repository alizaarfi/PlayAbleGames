export async function signup(data){
    const res= await fetch("http://localhost:8081/user/register",{
        method: "POST",
        headers:{"Content-Type":"application/json"},
        body: JSON.stringify(data)});

    if (!res.ok){
        throw new Error("Signup Failed");
    }

    return res.json();
}