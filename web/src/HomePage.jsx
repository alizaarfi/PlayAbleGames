import React from 'react';
import "./style.css";

function HomePage() {
    return (
        <div className="app">
            <header className="main-header">
                <div className="container">
                    <div className="header-content">
                        <div className="logo">
                            <svg className="controller-icon" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                                <path d="M6 9c-1.1 0-2 .9-2 2v2c0 1.1.9 2 2 2s2-.9 2-2v-2c0-1.1-.9-2-2-2zm12 0c-1.1 0-2 .9-2 2v2c0 1.1.9 2 2 2s2-.9 2-2v-2c0-1.1-.9-2-2-2zm-4-6C7.58 3 2 6.58 2 11c0 2.16.86 4.12 2.25 5.53L3 21h3l.75-3h10.5l.75 3h3l-1.25-4.47C21.14 15.12 22 13.16 22 11c0-4.42-5.58-8-8-8z"/>
                            </svg>
                            <h1>PlayAble Games</h1>
                        </div>
                    </div>
                </div>
            </header>

            <main className="container">
                <section className="hero">
                    <h2>Playable Games for Everyone</h2>
                    <p>Find mobile games designed for everyone. Filter by accessibility features, read reviews from the community, and discover games that work for you.</p>
                </section>
            </main>
        </div>
    );
}

export default HomePage;