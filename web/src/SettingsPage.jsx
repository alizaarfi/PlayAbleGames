import React, { useState } from 'react';
import Navbar from './Navbar';
import './settings_styles.css';

function SettingsPage() {
    const [settings, setSettings] = useState({
        lowSaturation: false,
        reduceMotion: false,
        textToSpeech: false,
        visualCaptions: false,
        voiceCommands: false,
        screenReader: false
    });

    const toggleSetting = (settingName) => {
        setSettings(prev => ({
            ...prev,
            [settingName]: !prev[settingName]
        }));
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