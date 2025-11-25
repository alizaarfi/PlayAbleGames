import React, { useState, useEffect } from 'react';
import Navbar from './NavBar';
import './settings_styles.css';

function SettingsPage() {
    // Load settings from localStorage - initialize missing values to 'false'
    const [settings, setSettings] = useState(() => {
        const savedSettings = {
            darkMode: localStorage.getItem('darkMode') === 'true',
            blueLight: localStorage.getItem('blueLight') === 'true',
            textToSpeech: localStorage.getItem('textToSpeech') === 'true',
            visualCaptions: localStorage.getItem('visualCaptions') === 'true',
            voiceCommands: localStorage.getItem('voiceCommands') === 'true',
            screenReader: localStorage.getItem('screenReader') === 'true'
        };

        // Initialize any undefined values in localStorage
        Object.keys(savedSettings).forEach(key => {
            if (localStorage.getItem(key) === null) {
                localStorage.setItem(key, 'false');
            }
        });

        return savedSettings;
    });

    // Apply settings when they change
    useEffect(() => {
        // Save to localStorage
        Object.keys(settings).forEach(key => {
            localStorage.setItem(key, settings[key]);
        });

        // Apply dark mode
        if (settings.darkMode) {
            document.body.classList.add('dark-mode');
        } else {
            document.body.classList.remove('dark-mode');
        }

        // Apply blue light filter
        if (settings.blueLight) {
            document.body.classList.add('blue-light-filter');
        } else {
            document.body.classList.remove('blue-light-filter');
        }

        // Update TTS setting and notify navbar
        document.body.setAttribute('data-tts-enabled', settings.textToSpeech);
        window.dispatchEvent(new CustomEvent('tts-setting-changed', {
            detail: { enabled: settings.textToSpeech }
        }));
    }, [settings]);

    const toggleSetting = (settingName) => {
        setSettings(prev => ({
            ...prev,
            [settingName]: !prev[settingName]
        }));
    };

    const resetSettings = () => {
        const defaultSettings = {
            darkMode: false,
            blueLight: false,
            textToSpeech: false,
            visualCaptions: false,
            voiceCommands: false,
            screenReader: false
        };
        setSettings(defaultSettings);
        localStorage.clear();
    };

    return (
        <div className="app">
            <Navbar />

            <main className="settings-page">
                <div className="settings-hero">
                    <h1>Accessibility Settings</h1>
                    <p>Customise the platform to meet your accessibility needs</p>
                    <div className="wcag-badge-settings">
                        <svg viewBox="0 0 24 24" fill="currentColor" className="info-icon">
                            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z"/>
                        </svg>
                        <span>This platform follows WCAG 2.1 AA standards</span>
                    </div>
                </div>

                <div className="settings-container">
                    {/* Visual Settings */}
                    <div className="settings-section">
                        <h2>Visual Settings</h2>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Dark Mode</h3>
                                <p>Switch to a dark colour scheme to reduce eye strain</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.darkMode}
                                    onChange={() => toggleSetting('darkMode')}
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Blue Light Filter</h3>
                                <p>Adds a warm tint to reduce blue light and eye strain at night</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.blueLight}
                                    onChange={() => toggleSetting('blueLight')}
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>
                    </div>

                    {/* Audio & Speech Settings */}
                    <div className="settings-section">
                        <h2>Audio & Speech Settings</h2>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Text-to-Speech</h3>
                                <p>Have content read aloud as you navigate</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.textToSpeech}
                                    onChange={() => toggleSetting('textToSpeech')}
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Visual Captions</h3>
                                <p>Display captions for audio content</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.visualCaptions}
                                    onChange={() => toggleSetting('visualCaptions')}
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>
                    </div>

                    {/* Input Settings */}
                    <div className="settings-section">
                        <h2>Input Settings</h2>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Voice Commands</h3>
                                <p>Microphone for hands-free navigation (always enabled)</p>
                                <div className="voice-commands-info">
                                    <svg viewBox="0 0 24 24" fill="currentColor" className="mic-icon-small">
                                        <path d="M12 14c1.66 0 2.99-1.34 2.99-3L15 5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3z"/>
                                        <path d="M17 11c0 2.76-2.24 5-5 5s-5-2.24-5-5H5c0 3.53 2.61 6.43 6 6.92V21h2v-3.08c3.39-.49 6-3.39 6-6.92h-2z"/>
                                    </svg>
                                    <div className="voice-commands-list">
                                        <p>Click the microphone icon in the navigation bar to activate voice commands. Try saying:</p>
                                        <ul>
                                            <li>"Go home"</li>
                                            <li>"Open community"</li>
                                            <li>"Search puzzle games"</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={true}
                                    disabled
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Screen Reader Support</h3>
                                <p>Optimised for screen readers (always enabled)</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={true}
                                    disabled
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>
                    </div>


                    {/* Reset Settings */}
                    <div className="settings-section reset-section">
                        <h2>Reset Settings</h2>
                        <p>Restore default accessibility settings</p>
                        <button className="reset-button" onClick={resetSettings}>
                            <svg viewBox="0 0 24 24" fill="currentColor" className="reset-icon">
                                <path d="M12 5V1L7 6l5 5V7c3.31 0 6 2.69 6 6s-2.69 6-6 6-6-2.69-6-6H4c0 4.42 3.58 8 8 8s8-3.58 8-8-3.58-8-8-8z"/>
                            </svg>
                            Reset to Defaults
                        </button>
                    </div>
                </div>
            </main>

            <footer className="page-footer">
                <p>Making Games Accessible for Every Player</p>
                <p>
                    Platform designed with{' '}
                    <a
                        href="https://accessibility-manual.dwp.gov.uk/accessibility-law/web-content-accessibility-guidelines"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        WCAG 2.1 Level AA
                    </a>{' '}
                    compliance in mind
                </p>
            </footer>
        </div>
    );
}

export default SettingsPage;