import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const API_BASE = "http://localhost:8081";

export default function AdminDashboard(){

    // Admin Dashboard Page html code here
    return(
        <div className="page">
            <div className="content">
                <h1> Welcome back admin</h1>
                <div className="dashboard">
                    <div className="topRow">
                        <div className="dashboard-item">
                            <h2> Flagged items </h2>
                            <p> 5 </p> // Example data
                        </div>
                        <div className="dashboard-item">
                            <h2> Live Games </h2>
                            <p> 12 </p> // Example data
                        </div>
                        <div className="dashboard-item">
                            <h2> Users </h2>
                            <p> 3 </p> // Example data
                        </div>
                    </div>
                    <div className="lowerRow">
                        <div className="dashboard-item">
                            <h2> Flagged Items </h2>
                            <h4> Today </h4>
                            <p> 2 </p> // Example data
                            <h4> In the last week </h4>
                            <p> 5 </p> // Example data
                            <h4> In the last month </h4>
                            <p> 20 </p> // Example data
                        </div>
                        <div className="dashboard-item">
                            <h2> Games added </h2>
                            <h4> Today </h4>
                            <p> 2 </p> // Example data
                            <h4> In the last week </h4>
                            <p> 5 </p> // Example data
                            <h4> In the last month </h4>
                            <p> 20 </p> // Example data
                        </div>
                        <div className="dashboard-item">
                            <h2> Users added </h2>
                            <h4> Today </h4>
                            <p> 2 </p> // Example data
                            <h4> In the last week </h4>
                            <p> 5 </p> // Example data
                            <h4> In the last month </h4>
                            <p> 20 </p> // Example data
                        </div>
                    </div>
                </div>
                <div className="signOut">
                    <button> Sign Out </button>
                </div>
            </div>
        </div>
    )
}