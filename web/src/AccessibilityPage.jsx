import React from 'react';
import Navbar from './Navbar';
import './style.css';

function AccessibilityPage() {
    return (
        <div className="app">
            <Navbar />

            <main className="accessibility-page-simple">
                <div className="accessibility-hero">
                    <h1>Platform Accessibility Features</h1>
                    <p>This platform is designed with accessibility at its core. Here's how we support different needs:</p>
                </div>

                <div className="accessibility-cards-list">
                    {/* Hearing Impairments */}
                    <div className="accessibility-card-horizontal">
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Hearing</span> Impairments</h3>
                        </div>
                    </div>

                    {/* Vision Impairments */}
                    <div className="accessibility-card-horizontal">
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Vision</span> Impairments</h3>
                        </div>
                    </div>

                    {/* Dexterity Impairments */}
                    <div className="accessibility-card-horizontal">
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Dexterity</span> Impairments</h3>
                        </div>
                    </div>

                    {/* Cognitive Impairments */}
                    <div className="accessibility-card-horizontal">
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Cognitive</span> Impairments</h3>
                        </div>
                    </div>

                    {/* Speech Impairments */}
                    <div className="accessibility-card-horizontal">
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Speech</span> Impairments</h3>
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

export default AccessibilityPage;