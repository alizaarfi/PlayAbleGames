import React, { useState, useRef, useEffect } from 'react';
import { Link } from 'react-router-dom';
import useSpeech from './UseSpeech';
import "./style.css";
import {useAuth} from "./hooks/AuthProvider.jsx";
import ConfirmModal from "./ConfirmModal.jsx";

function Navbar() {
    const [profileDropdownOpen, setProfileDropdownOpen] = useState(false);
    const [ttsEnabled, setTtsEnabled] = useState(false);
    const [showConfirmModal, setShowConfirmModal]= useState(false);
    const [showSuccessToast, setShowSuccessToast]= useState(false);
    const dropdownRef = useRef(null);
    const { isSpeaking, stop, readPage } = useSpeech();

    const { isAuthenticated, logout } = useAuth();//for login/logout button


    // Close dropdown when clicking outside
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setProfileDropdownOpen(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    // Check Text-to-Speech setting
    useEffect(() => {
        const checkTTS = () => {
            setTtsEnabled(localStorage.getItem('textToSpeech') === 'true');
        };

        checkTTS();
        const interval = setInterval(checkTTS, 100);

        return () => clearInterval(interval);
    }, []);

    const handleSpeakerClick = () => {
        if (isSpeaking) {
            stop();
        } else {
            readPage();
        }
    };

    /**
     * Called when the user clicks the logout button in the navbar.
     * Shows a confirmation modal before logging out.
     */
    const handleLogoutClick = () => {
        setProfileDropdownOpen(false); // close profile dropdown
        setShowConfirmModal(true); // open confirmation modal
    }

    /**
     * Called when the user confirms logout.
     * Logs the user out via API, shows success toast, and redirects.
     */
    const handleConfirmLogout = async ()=>{
        setShowConfirmModal(false);
        try {
            await logout(); // call api to invalidate token
            setShowSuccessToast(true); // show success notifications

            // redirect to homepage after 1.5 seconds
            setTimeout(() => {
                window.location.href = "/";
            }, 1500);
        } catch (e) {
            console.error("Logout failed:", e);
            alert("Logout failed. Please try again.");

        }
    };

    return (
        <>
        <header className="main-header">
            <div className="container">
                <div className="header-content">
                    <div className="logo">
                        <Link to="/" className="logo-link-wrapper">
                            <img
                                src="/gamecontroller.png"
                                alt="Game controller icon - Click to go home"
                                className="controller-icon"
                            />
                        </Link>
                        <div className="logo-text">
                            <h1>PlayAble Games</h1>
                            <p className="logo-subtitle">All players welcome</p>
                        </div>
                    </div>

                    <div className="header-nav">
                        <button className="nav-btn accessibility-btn">
                            <svg viewBox="0 0 24 24" fill="currentColor" className="nav-icon">
                                <path d="M12 14c1.66 0 3-1.34 3-3V5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3z"/>
                                <path d="M17 11c0 2.76-2.24 5-5 5s-5-2.24-5-5H5c0 3.53 2.61 6.43 6 6.92V21h2v-3.08c3.39-.49 6-3.39 6-6.92h-2z"/>
                            </svg>
                        </button>

                        {ttsEnabled && (
                            <button
                                className={`nav-btn accessibility-btn ${isSpeaking ? 'speaking' : ''}`}
                                onClick={handleSpeakerClick}
                                aria-label={isSpeaking ? "Stop reading page" : "Read page aloud"}
                            >
                                <svg viewBox="0 0 24 24" fill="currentColor" className="nav-icon">
                                    {isSpeaking ? (
                                        <>
                                            <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02z"/>
                                            <path d="M14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/>
                                        </>
                                    ) : (
                                        <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/>
                                    )}
                                </svg>
                            </button>
                        )}

                        <Link to="/accessibility" className="nav-btn info-btn">
                            <svg viewBox="0 0 24 24" fill="currentColor" className="nav-icon">
                                <circle cx="12" cy="4" r="2"/>
                                <path d="M15.89,8.11C15.5,7.72,14.83,7,13.53,7c-0.21,0-1.42,0-2.54,0C8.24,6.99,6,4.75,6,2H4c0,3.16,2.11,5.84,5,6.71V22h2v-6h2 v6h2V10.05L18.95,14l1.41-1.41L15.89,8.11z"/>
                            </svg>
                            <span>About Accessibility</span>
                        </Link>

                        <button className="nav-btn community-btn">
                            <svg viewBox="0 0 24 24" fill="currentColor" className="nav-icon">
                                <path d="M12 14c1.66 0 3-1.34 3-3V5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3z"/>
                                <path d="M17 11c0 2.76-2.24 5-5 5s-5-2.24-5-5H5c0 3.53 2.61 6.43 6 6.92V21h2v-3.08c3.39-.49 6-3.39 6-6.92h-2z"/>
                            </svg>
                            <span>Community</span>
                        </button>

                        <div className="profile-dropdown-wrapper" ref={dropdownRef}>
                            <button
                                className="profile-btn"
                                onClick={() => setProfileDropdownOpen(!profileDropdownOpen)}
                                aria-expanded={profileDropdownOpen}
                                aria-label="User menu"
                            >
                                <svg viewBox="0 0 24 24" fill="currentColor" className="profile-icon">
                                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
                                </svg>
                            </button>

                            {profileDropdownOpen && (
                                <div className="profile-dropdown">
                                    <Link to="/settings" className="dropdown-item">
                                        <svg viewBox="0 0 24 24" fill="currentColor" className="dropdown-icon">
                                            <path d="M19.14,12.94c0.04-0.3,0.06-0.61,0.06-0.94c0-0.32-0.02-0.64-0.07-0.94l2.03-1.58c0.18-0.14,0.23-0.41,0.12-0.61 l-1.92-3.32c-0.12-0.22-0.37-0.29-0.59-0.22l-2.39,0.96c-0.5-0.38-1.03-0.7-1.62-0.94L14.4,2.81c-0.04-0.24-0.24-0.41-0.48-0.41 h-3.84c-0.24,0-0.43,0.17-0.47,0.41L9.25,5.35C8.66,5.59,8.12,5.92,7.63,6.29L5.24,5.33c-0.22-0.08-0.47,0-0.59,0.22L2.74,8.87 C2.62,9.08,2.66,9.34,2.86,9.48l2.03,1.58C4.84,11.36,4.8,11.69,4.8,12s0.02,0.64,0.07,0.94l-2.03,1.58 c-0.18,0.14-0.23,0.41-0.12,0.61l1.92,3.32c0.12,0.22,0.37,0.29,0.59,0.22l2.39-0.96c0.5,0.38,1.03,0.7,1.62,0.94l0.36,2.54 c0.05,0.24,0.24,0.41,0.48,0.41h3.84c0.24,0,0.44-0.17,0.47-0.41l0.36-2.54c0.59-0.24,1.13-0.56,1.62-0.94l2.39,0.96 c0.22,0.08,0.47,0,0.59-0.22l1.92-3.32c0.12-0.22,0.07-0.47-0.12-0.61L19.14,12.94z M12,15.6c-1.98,0-3.6-1.62-3.6-3.6 s1.62-3.6,3.6-3.6s3.6,1.62,3.6,3.6S13.98,15.6,12,15.6z"/>
                                        </svg>
                                        <span>Settings</span>
                                    </Link>

                                    <div className="dropdown-divider"></div>

                                    {/* LOGGED OUT → Show LOGIN */}
                                    {!isAuthenticated && (
                                        <Link to="/login" className="dropdown-item login-item">
                                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="dropdown-icon">
                                                <path d="M15 3H21V21H15" />
                                                <polyline points="10 17 15 12 10 7" />
                                                <line x1="15" y1="12" x2="3" y2="12" />
                                            </svg>
                                            <span>Login</span>
                                        </Link>
                                    )}

                                    {/* LOGGED IN → Show LOGOUT */}
                                    {isAuthenticated && (
                                        <>
                                            <Link to="/myaccount" className="dropdown-item">
                                                <svg viewBox="0 0 24 24"
                                                     fill="none"
                                                     stroke="currentColor"
                                                     strokeWidth="2"
                                                     className="dropdown-icon">
                                                    <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
                                                </svg>
                                                <span>My Account</span>
                                            </Link>

                                            <Link to="/favorites" className="dropdown-item">
                                                <svg viewBox="0 0 24 24"
                                                     fill="none"
                                                     stroke="currentColor"
                                                     strokeWidth="2"
                                                     className="dropdown-icon">
                                                    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
                                                </svg>
                                                <span>Favorites</span>
                                            </Link>
                                            <button className="dropdown-item logout-item" onClick={handleLogoutClick}>
                                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="dropdown-icon">
                                                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                                                    <polyline points="16 17 21 12 16 7" />
                                                    <line x1="21" y1="12" x2="9" y2="12" />
                                                </svg>
                                                <span>Logout</span>
                                            </button>
                                        </>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </header>
    {/*Confirmation model*/}
    <ConfirmModal
        isOpen={showConfirmModal}
        message="Are you sure you want to Log Out?"
        onConfirm={handleConfirmLogout}
        onCancel={()=> setShowConfirmModal(false)}
    />
    {/* Success Toast */}
    {showSuccessToast && (
        <div className="success-toast">
            You have been logged out successfully!
        </div>
    )}
    </>

    );
}

export default Navbar;