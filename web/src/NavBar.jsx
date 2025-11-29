import React, { useState, useRef, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import "./style.css";
import useVoiceControl from "./useVoiceControl.jsx";
import { executeCommand } from './commandRegistry';

function Navbar() {
    const [profileDropdownOpen, setProfileDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);
    const navigate = useNavigate();
    const { stream, isRecording, isMicActive, processingState,setProcessingState, transcription, commandData, error, toggleRecording } = useVoiceControl();

    // when commandData is received
    useEffect(() => {
        if (commandData) {
            processVoiceCommand(commandData);
        } else if (transcription && transcription.length > 0) {
            // Speech was detected but couldn't be parsed into a command
            console.warn("Speech detected but command not recognized:", transcription);
        }
    }, [commandData, transcription]);

    // Function to handle voice commands
    const processVoiceCommand = (commandData) => {
        try {
            if (!commandData || !commandData.type) {
                console.warn("Invalid command data:", commandData);
                return; // Don't crash, just exit gracefully
            }

            // extracts from the backend response
            const { type, target } = commandData;
            console.log("Full commandData:", commandData);
            console.log("Target:", target);

            //switch case for each command type then relevant target
            switch (type) {
                case 'NAVIGATE':
                    console.log("NAVIGATE TYPE")
                    if (!target) {
                        console.warn("Navigate called without target");
                        break;
                    }

                    try {
                        if (target === 'accessibility') {
                            navigate('/accessibility');
                            setTimeout(() => {
                                setProcessingState('success');
                                setTimeout(() => setProcessingState('idle'), 1500);
                            }, 100);
                        } else if (target === 'home') {
                            navigate('/');
                            setTimeout(() => {
                                setProcessingState('success');
                                setTimeout(() => setProcessingState('idle'), 1500);
                            }, 100);
                        } else {
                            console.log("Unknown route:", target);
                            setProcessingState('error');
                            setTimeout(() => setProcessingState('idle'), 2000);
                        }
                    } catch (navError) {
                        console.error("Navigation failed:", navError);
                        setProcessingState('error');
                        setTimeout(() => setProcessingState('idle'), 2000);
                    }
                    break;

                case 'CLICK':
                    console.log(`Clicking: ${target}`);
                    // Example: find and click element
                    break;

                case 'SEARCH':
                    console.log(`Searching for: ${target}`);

                    // Event pattern
                    // Dispatch search command
                    window.dispatchEvent(new CustomEvent('voiceCommand', {
                        detail: {
                            type: 'SEARCH',
                            searchQuery: target
                        }
                    }));

                    console.log(`Dispatched SEARCH event for: ${target}`);

                    setTimeout(() => {
                        setProcessingState('success');
                        setTimeout(() => setProcessingState('idle'), 1500);
                    }, 100);
                    break;

                case 'SCROLL':
                    console.log(`Scrolling: ${target}`);

                    const scrollActions = {
                        'up': -500,
                        'down': 500,
                        'top': 'top',
                        'bottom': 'bottom'
                    };

                    const scrollAmount = scrollActions[target.toLowerCase()];

                    if (scrollAmount !== undefined) {
                        // Execute scroll directly instead of dispatching event
                        if (scrollAmount === 'top') {
                            window.scrollTo({ top: 0, behavior: 'smooth' });
                        } else if (scrollAmount === 'bottom') {
                            window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
                        } else {
                            window.scrollBy({ top: scrollAmount, behavior: 'smooth' });
                        }

                        console.log(`Executed SCROLL: ${target}`);

                        setTimeout(() => {
                            setProcessingState('success');
                            setTimeout(() => setProcessingState('idle'), 1500);
                        }, 100);
                    } else {
                        console.warn(`Unknown scroll direction: ${target}`);
                        setProcessingState('error');
                        setTimeout(() => setProcessingState('idle'), 2000);
                    }
                    break;

                case 'UNKNOWN':
                    console.warn(`Unknown command: ${transcription}`);
                    setProcessingState('error');
                    setTimeout(() => setProcessingState('idle'), 2000);
                    break;

                case 'FAVOURITE':
                    console.log(`Favouriting game: ${target}`);

                    // Simple game name to ID mapping
                    const gameNameToId = {
                        'minecraft': 1,
                        'monument valley': 2,
                        'among us': 3,
                        'candy crush saga': 4,
                        'call of duty': 5,
                        'pokemon go': 6,
                        'subway surfers': 7,
                        'clash of clans': 8,
                        'jigsawscapes': 9,
                        'my oasis': 10,
                        'altos odyssey': 11,
                        'kami 2': 12,
                        'sky children of light': 13,
                        'stardew valley': 14,
                        'a blind legend': 15
                    };

                    const gameName = target.toLowerCase();
                    const gameId = gameNameToId[gameName];

                    if (gameId) {
                        // Dispatch custom event that HomePage will listen to
                        window.dispatchEvent(new CustomEvent('voiceCommand', {
                            detail: {
                                type: 'FAVOURITE',
                                gameId: gameId,
                                gameName: target
                            }
                        }));

                        console.log(`Dispatched FAVOURITE event for ${target} (ID: ${gameId})`);

                        setTimeout(() => {
                            setProcessingState('success');
                            setTimeout(() => setProcessingState('idle'), 1500);
                        }, 100);
                    } else {
                        console.warn(`Game not found: ${target}`);
                        setProcessingState('error');
                        setTimeout(() => setProcessingState('idle'), 2000);
                    }
                    break;

                case 'FILTER':
                    console.log(`Filtering by: ${target}`);

                    // Map spoken filter names to your filter state keys
                    const filterMap = {
                        'vision support': 'visionSupport',
                        'vision': 'visionSupport',
                        'hearing support': 'hearingSupport',
                        'hearing': 'hearingSupport',
                        'speech support': 'speechSupport',
                        'speech': 'speechSupport',
                        'motor support': 'motorSupport',
                        'motor': 'motorSupport',
                        'cognitive support': 'cognitiveSupport',
                        'cognitive': 'cognitiveSupport',
                        'action': 'action',
                        'adventure': 'adventure',
                        'puzzle': 'puzzle',
                        'strategy': 'strategy',
                        'casual': 'casual'
                    };

                    const filterKey = filterMap[target.toLowerCase()];

                    if (filterKey) {
                        window.dispatchEvent(new CustomEvent('voiceCommand', {
                            detail: {
                                type: 'FILTER',
                                filterKey: filterKey,
                                filterName: target
                            }
                        }));

                        console.log(`Dispatched FILTER event for ${target}`);

                        setTimeout(() => {
                            setProcessingState('success');
                            setTimeout(() => setProcessingState('idle'), 1500);
                        }, 100);
                    } else {
                        console.warn(`Filter not found: ${target}`);
                        setProcessingState('error');
                        setTimeout(() => setProcessingState('idle'), 2000);
                    }
                    break;

                case 'SORT':
                    console.log(`Sorting by: ${target}`);

                    // Map spoken sort options to your sortOption values
                    const sortMap = {
                        'rating': 'ratingDesc',
                        'highest rating': 'ratingDesc',
                        'lowest rating': 'ratingAsc',
                        'most popular': 'ratingDesc',
                        'most reviewed': 'reviewsDesc',
                        'least reviewed': 'reviewsAsc',
                        'title': 'titleAsc',
                        'alphabetical': 'titleAsc',
                        'reverse alphabetical': 'titleDesc'
                    };

                    const sortValue = sortMap[target.toLowerCase()];

                    if (sortValue) {
                        window.dispatchEvent(new CustomEvent('voiceCommand', {
                            detail: {
                                type: 'SORT',
                                sortValue: sortValue,
                                sortName: target
                            }
                        }));

                        console.log(`Dispatched SORT event for ${target}`);

                        setTimeout(() => {
                            setProcessingState('success');
                            setTimeout(() => setProcessingState('idle'), 1500);
                        }, 100);
                    } else {
                        console.warn(`Sort option not found: ${target}`);
                        setProcessingState('error');
                        setTimeout(() => setProcessingState('idle'), 2000);
                    }
                    break;

                default:
                    console.log(`Command: ${type}, Target: ${target}`);
                    break;
            }

        } catch (error) {
            console.error("Command processing error:", error);
            setProcessingState('error');
            setTimeout(() => setProcessingState('idle'), 2000);
        }



    };

    // Display error messages
    useEffect(() => {
        if (error) {
            console.error("Error:", error);
            // Optionally show error to user
        }
    }, [error]);


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

    return (
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

                        <div className={"visual-feedback-container"}>
                            {/* Show example command when idle */}
                            {(!processingState || processingState === 'idle') && (
                                <span className="feedback-text idle">
                                    Ready - Speak to start
                                </span>
                            )}

                            {/*Show recording state when audio wave*/}
                            {/* Audio wave during recording */}
                            {processingState === 'recording' && (
                                <div className="feedback-recording">
                                    <div className="audio-wave">
                                        <span className="wave-bar"></span>
                                        <span className="wave-bar"></span>
                                        <span className="wave-bar"></span>
                                        <span className="wave-bar"></span>
                                        <span className="wave-bar"></span>
                                        <span className="wave-bar"></span>
                                    </div>
                                    <span className="feedback-text recording">Listening</span>
                                </div>
                            )}

                            {/*/!*Need for listening *!/*/}
                            {/*{processingState === 'recording' && (*/}
                            {/*    <span className="feedback-text recording">*/}
                            {/*        Listening...*/}
                            {/*    </span>*/}
                            {/*)}*/}
                            {/*Need for processing */}
                            {processingState === 'processing' && (
                                <span className="feedback-text processing">
                                    <span className="spinner"></span> Processing...
                                </span>
                            )}
                            {/*/!*Need for executing*!/*/}
                            {/*{processingState === 'executing' && (*/}
                            {/*    <span className="feedback-text executing">*/}
                            {/*        <span className="spinner"></span> Executing...*/}
                            {/*    </span>*/}
                            {/*)}*/}

                            {/*Need for successful */}
                            {processingState === 'success' && (
                                <span className="feedback-text success">
                                    Done!
                                </span>
                            )}
                            {/*Need for try again */}
                            {processingState === 'error' && (
                                <span className="feedback-text error-state">
                                    Try Again
                                </span>
                            )}

                        </div>

                        {/*VoiceControlButton*/}
                        <button
                            className={`nav-btn accessibility-btn ${
                                !stream ? 'mic-error' :
                                    isMicActive ? 'mic-recording' :  // Use isMicActive instead of isRecording
                                        'mic-inactive'
                            }`}
                            onClick={toggleRecording}
                            // disabled={!stream}  // Disable if no mic access
                            title={
                                !stream ? 'Microphone not available' :
                                    isRecording ? 'Recording - Click to stop' :
                                        'Click to start recording'
                            }
                        >
                            {/*Button Icon*/}
                            {/*Updates  to show based on isMicActive:*/}
                            <svg viewBox="0 0 24 24" fill="currentColor" className="nav-icon">
                                {isMicActive ? (
                                    // Active mic icon
                                    <>
                                        <path d="M12 14c1.66 0 3-1.34 3-3V5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3z"/>
                                        <path d="M17 11c0 2.76-2.24 5-5 5s-5-2.24-5-5H5c0 3.53 2.61 6.43 6 6.92V21h2v-3.08c3.39-.49 6-3.39 6-6.92h-2z"/>
                                    </>
                                ) : (
                                    // Muted mic icon
                                    <path d="M19 11h-1.7c0 .74-.16 1.43-.43 2.05l1.23 1.23c.56-.98.9-2.09.9-3.28zm-4.02.17c0-.06.02-.11.02-.17V5c0-1.66-1.34-3-3-3S9 3.34 9 5v.18l5.98 5.99zM4.27 3L3 4.27l6.01 6.01V11c0 1.66 1.33 3 2.99 3 .22 0 .44-.03.65-.08l1.66 1.66c-.71.33-1.5.52-2.31.52-2.76 0-5.3-2.1-5.3-5.1H5c0 3.41 2.72 6.23 6 6.72V21h2v-3.28c.91-.13 1.77-.45 2.54-.9L19.73 21 21 19.73 4.27 3z"/>
                                )}
                            </svg>
                        </button>



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
                                    <button className="dropdown-item">
                                        <svg viewBox="0 0 24 24" fill="currentColor" className="dropdown-icon">
                                            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
                                        </svg>
                                        <span>My Account</span>
                                    </button>

                                    <button className="dropdown-item">
                                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="dropdown-icon">
                                            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
                                        </svg>
                                        <span>Favourites</span>
                                    </button>

                                    <button className="dropdown-item">
                                        <svg viewBox="0 0 24 24" fill="currentColor" className="dropdown-icon">
                                            <path d="M19.14,12.94c0.04-0.3,0.06-0.61,0.06-0.94c0-0.32-0.02-0.64-0.07-0.94l2.03-1.58c0.18-0.14,0.23-0.41,0.12-0.61 l-1.92-3.32c-0.12-0.22-0.37-0.29-0.59-0.22l-2.39,0.96c-0.5-0.38-1.03-0.7-1.62-0.94L14.4,2.81c-0.04-0.24-0.24-0.41-0.48-0.41 h-3.84c-0.24,0-0.43,0.17-0.47,0.41L9.25,5.35C8.66,5.59,8.12,5.92,7.63,6.29L5.24,5.33c-0.22-0.08-0.47,0-0.59,0.22L2.74,8.87 C2.62,9.08,2.66,9.34,2.86,9.48l2.03,1.58C4.84,11.36,4.8,11.69,4.8,12s0.02,0.64,0.07,0.94l-2.03,1.58 c-0.18,0.14-0.23,0.41-0.12,0.61l1.92,3.32c0.12,0.22,0.37,0.29,0.59,0.22l2.39-0.96c0.5,0.38,1.03,0.7,1.62,0.94l0.36,2.54 c0.05,0.24,0.24,0.41,0.48,0.41h3.84c0.24,0,0.44-0.17,0.47-0.41l0.36-2.54c0.59-0.24,1.13-0.56,1.62-0.94l2.39,0.96 c0.22,0.08,0.47,0,0.59-0.22l1.92-3.32c0.12-0.22,0.07-0.47-0.12-0.61L19.14,12.94z M12,15.6c-1.98,0-3.6-1.62-3.6-3.6 s1.62-3.6,3.6-3.6s3.6,1.62,3.6,3.6S13.98,15.6,12,15.6z"/>
                                        </svg>
                                        <span>Settings</span>
                                    </button>

                                    <div className="dropdown-divider"></div>

                                    <button className="dropdown-item logout-item">
                                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="dropdown-icon">
                                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                                            <polyline points="16 17 21 12 16 7"/>
                                            <line x1="21" y1="12" x2="9" y2="12"/>
                                        </svg>
                                        <span>Logout</span>
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Navbar;