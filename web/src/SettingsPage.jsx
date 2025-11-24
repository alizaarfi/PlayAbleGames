import React from 'react';
import Navbar from './Navbar';
import './settings_styles.css';

function SettingsPage() {
    return (
        <div className="app">
            <Navbar />

            <main className="settings-page">
                <div className="settings-hero">
                    <h1>Accessibility Settings</h1>
                    <p>Customise the platform to meet your accessibility needs</p>
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
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Reduce Motion</h3>
                                <p>Minimises animations and transitions</p>
                            </div>
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
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Visual Captions</h3>
                                <p>Display captions for audio content</p>
                            </div>
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
                        </div>

                        <div className="setting-item">
                            <div className="setting-info">
                                <h3>Screen Reader Support</h3>
                                <p>Optimised for screen readers (always enabled)</p>
                            </div>
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