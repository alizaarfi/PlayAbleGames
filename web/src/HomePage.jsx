import React, { useState } from 'react';
import "./style.css";

function HomePage() {
    const [searchQuery, setSearchQuery] = useState('');

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

                <div className="search-section">
                    <div className="search-box">
                        <input
                            type="search"
                            placeholder="Search games by title, genre, keyword..."
                            aria-label="Search games"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                        />
                        <button type="button" aria-label="Search">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
                            </svg>
                        </button>
                    </div>
                    <div className="sort-controls">
                        <button className="sort-btn">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M3 18h6v-2H3v2zM3 6v2h18V6H3zm0 7h12v-2H3v2z"/>
                            </svg>
                            Sort by
                        </button>
                        <button className="dropdown-btn">
                            Most Popular
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M7 10l5 5 5-5z"/>
                            </svg>
                        </button>
                    </div>
                </div>
            </main>
        </div>
    );
}

export default HomePage;