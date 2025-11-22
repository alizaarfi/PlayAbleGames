import React, { useEffect, useState } from "react";
import "./style.css";
import "./Dashboard.css"

const API_BASE = "http://localhost:8081/api";

export default function AdminDashboard(){

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [dashboard, setDashboard] = useState({
        totalFlaggedItems: 0,
        totalLiveGames: 0,
        totalUsers: 0
    })

    useEffect(() => {
        const controller = new AbortController();

        async function fetchDashboard() {
            setLoading(true);
            setError(null);
            try {
                const res = await fetch(`${API_BASE}/admin`, {
                    signal: controller.signal,
                });
                if (!res.ok) throw new Error('Failed to fetch admin dashboard data.');
                const data = await res.json();
                setDashboard({
                    totalFlaggedItems: data.dashboard.totalFlaggedItems ?? 0,
                    totalLiveGames: data.dashboard.totalGames ?? 0,
                    totalUsers: data.dashboard.totalUsers ?? 0,
                });
            } catch (e) {
                if (e.name !== 'AbortError') setError(e.message);
            } finally {
                setLoading(false);
            }
        }
        fetchDashboard();
        return () => controller.abort();
    }, []);

    if (loading) return <div className="page"> <div className="content"> Loading... </div></div>;
    if (error) return <div className="page"> <div className="content">  Error: {error} </div> </div>;


    // Admin Dashboard Page html code here
    return(
        <div className="page">
            <div className="content">
                <h1 className="title"> Welcome back admin</h1>
                <div className="dashboard">
                        <div className="dashboard-item">
                            <h2 className="black-text"> Flagged items </h2>
                            <p className="black-text"> {dashboard.totalFlaggedItems} </p>
                        </div>
                        <div className="dashboard-item">
                            <h2 className="black-text"> Live Games </h2>
                            <p className="black-text"> {dashboard.totalLiveGames} </p>
                        </div>
                        <div className="dashboard-item">
                            <h2 className="black-text"> Users </h2>
                            <p className="black-text"> {dashboard.totalUsers} </p>
                        </div>
                </div>
                <div className="signOut">
                    <button className="sign-out-button"> Sign Out </button>
                </div>
            </div>
        </div>
    )
}