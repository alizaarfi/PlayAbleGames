import React, { useState, useEffect } from 'react';
import Navbar from './Navbar';
import './settings_styles.css';

function SettingsPage() {
    // Load settings from localStorage
    const [settings, setSettings] = useState({
        lowSaturation: localStorage.getItem('lowSaturation') === 'true',
        reduceMotion: localStorage.getItem('reduceMotion') === 'true',
        textToSpeech: localStorage.getItem('textToSpeech') === 'true',
        visualCaptions: localStorage.getItem('visualCaptions') === 'true',
        voiceCommands: localStorage.getItem('voiceCommands') === 'true',
        screenReader: localStorage.getItem('screenReader') === 'true'
    });

    // Save settings to localStorage when they change
    useEffect(() => {
        Object.keys(settings).forEach(key => {
            localStorage.setItem(key, settings[key]);
        });
    }, [settings]);

    const toggleSetting = (settingName) => {
        setSettings(prev => ({
            ...prev,
            [settingName]: !prev[settingName]
        }));
    };

    const resetSettings = () => {
        const defaultSettings = {
            lowSaturation: false,
            reduceMotion: false,
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
                                <h3>Low Saturation Mode</h3>
                                <p>Reduces the intensity of colours for better visibility</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.lowSaturation}
                                    onChange={() => toggleSetting('lowSaturation')}
                                />
                                <span className="toggle-slider"></span>
                            </label>
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Reduce Motion</h3>
                                <p>Minimises animations and transitions</p>
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.reduceMotion}
                                    onChange={() => toggleSetting('reduceMotion')}
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
                                <p>Microphone for hands-free navigation</p>
                                {settings.voiceCommands && (
                                    <div className="voice-commands-info">
                                        <svg viewBox="0 0 24 24" fill="currentColor" className="mic-icon-small">
                                            <path d="M12 14c1.66 0 2.99-1.34 2.99-3L15 5c0-1.66-1.34-3-3-3S9 3.34 9 5v6c0 1.66 1.34 3 3 3z"/>
                                        </svg>
                                        <div className="voice-commands-list">
                                            <p>Click the microphone icon in the navigation to activate voice commands. Try saying:</p>
                                            <ul>
                                                <li>"Go home"</li>
                                                <li>"Open community"</li>
                                                <li>"Search puzzle games"</li>
                                            </ul>
                                        </div>
                                    </div>
                                )}
                            </div>
                            <label className="toggle-switch">
                                <input
                                    type="checkbox"
                                    checked={settings.voiceCommands}
                                    onChange={() => toggleSetting('voiceCommands')}
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
                                    checked={settings.screenReader}
                                    onChange={() => toggleSetting('screenReader')}
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