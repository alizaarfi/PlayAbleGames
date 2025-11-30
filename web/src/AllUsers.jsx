import React, { useEffect, useState } from "react";
import "./style.css";
import "./AllUsers.css"

const API_BASE = "http://localhost:8081/api";

export default function AllUsers(){

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [user, setUsers] = useState([]);

    useEffect(() => {
        const controller = new AbortController();
        async function fetchAllUsers() {
            setLoading(true);
            setError(null);
            try {
                const res = await fetch(`${API_BASE}/users`, {
                    signal: controller.signal,
                });
                if (!res.ok) throw new Error("Failed to fetch users data.");
                const data = await res.json();

                let normalised = [];
                if (Array.isArray(data)) {
                    normalised = data;
                } else if (Array.isArray(data.user)) {
                    normalised = data.user;
                } else if (data.user) {
                    normalised = [data.user];
                }

                const mapped = normalised.map((r) => ({
                    userId: r.userId ?? null,
                    user: r.user ?? "user",
                    numReviews: r.numReviews ?? 0,
                    enabled: r.enabled ?? true,
                }));
                setUsers(mapped);
            } catch (e) {
                if (e.name !== "AbortError") setError(e.message);
            } finally {
                setLoading(false);
            }
        }

        fetchAllUsers();
        return () => controller.abort();
    }, []);

    function goToUserPage(userId) {
        window.location.href = `/users/${userId}`;
    }

    if (loading)
        return (
            <div className="page">
                <div className="content"> Loading... </div>
            </div>
        );
    if (error)
        return (
            <div className="page">
                <div className="content"> Error: {error} </div>
            </div>
        );


    // All Users Page html code here
    return (
        <div className="content">
            <div className="admin-nav">
                <button><a className="white-text" href="/admin"> Back to Dashboard </a></button>
            </div>
            <h1 className="title black-text"> All Users </h1>

            <div className="users-container">
                {user.length === 0 ? (
                    <div className="no-reviews black-text">No users found.</div>
                ) : (
                    user.map((user, idx) => (
                        <div className="userBox" key={user.userId ?? idx} onClick={() => goToUserPage(user.userId)}>
                            <h3 className="black-text"> {user.user} {user.enabled ? " (active)" : " (banned)"} </h3>
                            <p className="black-text"> {user.numReviews} reviews</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}