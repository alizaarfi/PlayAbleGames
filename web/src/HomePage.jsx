import React, { useState } from 'react';
import "./style.css";

const games = [
    {
        id: 1,
        title: "Minecraft",
        image: "https://www.minecraft.net/content/dam/minecraftnet/games/badger/key-art/Homepage_Discover-our-games_MC-Legends-KeyArt_864x864.jpg",
        description: "Game Description",
        rating: 4.5,
        reviews: 1231,
        tags: ["Sandbox", "Adventure"]
    },
    {
        id: 2,
        title: "Monument Valley",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.monumentvalleygame.com%2F&psig=AOvVaw15jcWRDg7wiixJ86mp8Lb0&ust=1762973067161000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCIDYxc7g6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.9,
        reviews: 3205,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 3,
        title: "Among Us",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.innersloth.com%2Fgames%2Famong-us%2F&psig=AOvVaw3i-cOXIxjTE-HZPdFFBv5p&ust=1762973459028000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOC7ioji6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.6,
        reviews: 2456,
        tags: ["Multiplayer", "Strategy"]
    },
    {
        id: 4,
        title: "Candy Crush Saga",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2Fcandy-crush-saga-iphone-gameplay-19-youtube--630926229032214580%2F&psig=AOvVaw2U76qOflQa-1ei2sGLbK2K&ust=1762974456409000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCOig3-fl6pADFQAAAAAdAAAAABAS",
        description: "Game Description",
        rating: 4.8,
        reviews: 1504,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 5,
        title: "Call Of Duty®: Mobile",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.indiatoday.in%2Ftechnology%2Ffeatures%2Fstory%2Fcall-of-duty-mobile-launches-tomorrow-new-gameplay-modes-better-graphics-and-why-pubg-players-should-try-it-1604723-2019-09-30&psig=AOvVaw16SkNR8-RtOKl8vmYq9V9O&ust=1762974813178000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCODJ7Y7n6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.5,
        reviews: 2721,
        tags: ["Action", "Multiplayer"]
    },
    {
        id: 6,
        title: "Pokemon Go",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Ftechcrunch.com%2F2017%2F12%2F06%2Fpokemon-go-weather%2F&psig=AOvVaw33z4DCnfS0qZmzIQSxOOIq&ust=1762975099900000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCNjzsZbo6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.7,
        reviews: 1832,
        tags: ["Adventure", "RPG"]
    },
    {
        id: 7,
        title: "Subway Surfers",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fmarksangryreview.com%2Fsubway-surfers-guide%2F&psig=AOvVaw0CV7Fm0llw_dLlIZ2F7-wO&ust=1762975282259000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLiBle7o6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.8,
        reviews: 3642,
        tags: ["Arcade", "Casual"]
    },
    {
        id: 8,
        title: "Clash of Clans",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.reddit.com%2Fr%2FClashOfClans%2Fcomments%2Fyhb6b8%2Finsane_coc_gameplay%2F&psig=AOvVaw0KuWDGiUvehXeHohTQOxZ5&ust=1762975521276000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCLC16OHp6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.6,
        reviews: 2100,
        tags: ["Strategy", "Multiplayer"]
    },
    {
        id: 9,
        title: "Jigsawscapes® - Jigsaw Puzzles",
        image: "https://play.google.com/store/apps/details/Jigsawscapes+-+Jigsaw+Puzzles?id=jigsaw.puzzle.game.banana&hl=en_CA",
        description: "Game Description",
        rating: 4.9,
        reviews: 5200,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 10,
        title: "My Oasis: Relaxing, Satisfying",
        image: "https://play.google.com/store/apps/details?id=com.buffstudio.myoasis",
        description: "Game Description",
        rating: 4.7,
        reviews: 3100,
        tags: ["Simulation", "Casual"]
    },
    {
        id: 11,
        title: "Alto's Odyssey",
        image: "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.hardcoregaming101.net%2Faltos-odyssey%2F&psig=AOvVaw3kZQYhU_b0sefLyHveD8gf&ust=1762976465534000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCICS7q_t6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.8,
        reviews: 2800,
        tags: ["Action", "Casual"]
    },
    {
        id: 12,
        title: "KAMI 2",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.appunwrapper.com%2F2017%2F03%2F31%2Fkami-2-walkthrough-guide-and-solutions%2F&psig=AOvVaw10OEE9bl47ZJjateQEB3lF&ust=1762976182187000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCJCF9Z7s6pADFQAAAAAdAAAAABAE",
        description: "Game Description",
        rating: 4.6,
        reviews: 1900,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 13,
        title: "Sky: Children of the Light",
        image: "https://play.google.com/store/apps/details?id=com.tgc.sky.android",
        description: "Game Description",
        rating: 4.9,
        reviews: 4500,
        tags: ["Adventure", "Multiplayer"]
    },
    {
        id: 14,
        title: "Stardew Valley",
        image: "https://play.google.com/store/apps/details?id=com.chucklefish.stardewvalley&hl=en_GB",
        description: "Game Description",
        rating: 4.8,
        reviews: 3800,
        tags: ["Simulation", "RPG"]
    },
    {
        id: 15,
        title: "A Blind Legend",
        image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Frawg.io%2Fgames%2Fa-blind-legend&psig=AOvVaw1yed98RHXORnbe4xwEjcoy&ust=1762977130248000&source=images&cd=vfe&opi=89978449&ved=0CBYQjRxqFwoTCKCN1d_v6pADFQAAAAAdAAAAABAL",
        description: "Game Description",
        rating: 4.7,
        reviews: 2200,
        tags: ["Action", "Adventure"]
    }
];

function HomePage() {
    const [searchQuery, setSearchQuery] = useState('');
    const [filters, setFilters] = useState({
        visionSupport: false,
        hearingSupport: false,
        speechSupport: false,
        motorSupport: false,
        cognitiveSupport: false,
        action: false,
        adventure: false,
        puzzle: false,
        strategy: false,
        casual: false
    });

    const handleFilterChange = (filterName) => {
        setFilters(prev => ({
            ...prev,
            [filterName]: !prev[filterName]
        }));
    };

    const renderStars = (rating) => {
        return '★★★★★';
    };

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

                <div className="content-layout">
                    <aside className="filters-sidebar">
                        <h3>Filters</h3>

                        <div className="filter-group">
                            <h4>Accessibility Features</h4>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.visionSupport}
                                    onChange={() => handleFilterChange('visionSupport')}
                                />
                                <span>Vision Support</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.hearingSupport}
                                    onChange={() => handleFilterChange('hearingSupport')}
                                />
                                <span>Hearing Support</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.speechSupport}
                                    onChange={() => handleFilterChange('speechSupport')}
                                />
                                <span>Speech Support</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.motorSupport}
                                    onChange={() => handleFilterChange('motorSupport')}
                                />
                                <span>Motor Support</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.cognitiveSupport}
                                    onChange={() => handleFilterChange('cognitiveSupport')}
                                />
                                <span>Cognitive Support</span>
                            </label>
                        </div>

                        <div className="filter-group">
                            <h4>Genres</h4>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.action}
                                    onChange={() => handleFilterChange('action')}
                                />
                                <span>Action</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.adventure}
                                    onChange={() => handleFilterChange('adventure')}
                                />
                                <span>Adventure</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.puzzle}
                                    onChange={() => handleFilterChange('puzzle')}
                                />
                                <span>Puzzle</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.strategy}
                                    onChange={() => handleFilterChange('strategy')}
                                />
                                <span>Strategy</span>
                            </label>
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={filters.casual}
                                    onChange={() => handleFilterChange('casual')}
                                />
                                <span>Casual</span>
                            </label>
                        </div>
                    </aside>

                    <section className="games-grid">
                        {games.map(game => (
                            <article key={game.id} className="game-card">
                                <img src={game.image} alt={`${game.title} game cover`} />
                                <div className="card-content">
                                    <div className="card-header">
                                        <h3>{game.title}</h3>
                                    </div>
                                    <p className="game-description">{game.description}</p>
                                    <div className="game-meta">
                                        <div className="rating">
                                            <div className="stars" aria-label={`Rating: ${game.rating} out of 5 stars`}>
                                                {renderStars(game.rating)}
                                            </div>
                                            <span>{game.rating} ({game.reviews.toLocaleString()})</span>
                                        </div>
                                    </div>
                                    <div className="tags">
                                        {game.tags.map(tag => (
                                            <span key={tag} className="tag">{tag}</span>
                                        ))}
                                    </div>
                                </div>
                            </article>
                        ))}
                    </section>
                </div>
            </main>
        </div>
    );
}

export default HomePage;