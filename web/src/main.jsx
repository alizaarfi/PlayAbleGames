import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GamePage from "./GamePage.jsx";
import SignupPage from "./pages/SignupPage.jsx";

import "./style.css";

/**
 * /games/:id Route, which displays a game's page.
 */
ReactDOM.createRoot(document.getElementById("app")).render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                {/* Load GamePage for any /games/:id */}
                <Route path="/games/:id" element={<GamePage />} />

                {/*signup page*/}
                <Route path="/signup" element={<SignupPage/>} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
