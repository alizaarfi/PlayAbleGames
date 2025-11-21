import React, { useState } from 'react';
import Navbar from './NavBar.jsx';
import "./style.css";
import AccessibilityPage from './AccessibilityPage';

const games = [
    {
        id: 1,
        title: "Minecraft",
        image: "https://play-lh.googleusercontent.com/gfNz1N2GNi5piz24IB08RQ4ZGfUnN_kOH8Edhh7uCiotI2P7IBWBXdHzR8gC01ppNnU=w720-h405-rw",
        description: "A creative sandbox game with flexible controls, adjustable difficulty, visual accessibility options, and a calm mode that reduces hostile mobs.",
        rating: 4.5,
        reviews: 1231,
        tags: ["Sandbox", "Adventure"]
    },
    {
        id: 2,
        title: "Monument Valley",
        image: "https://www.nintendo-insider.com/wp-content/uploads/2025/03/monument_valley_banner-1160x653.jpg",
        description: "A relaxing puzzle game with simple tap controls, clean visuals, and minimal text, making it accessible to a wide range of players.",
        rating: 4.9,
        reviews: 3205,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 3,
        title: "Among Us",
        image: "https://i.ytimg.com/vi/0YKjFoGxbec/maxresdefault.jpg",
        description: "A social deduction game with colorblind mode, clear UI, and simple movement controls, supporting accessible team play.",
        rating: 4.6,
        reviews: 2456,
        tags: ["Multiplayer", "Strategy"]
    },
    {
        id: 4,
        title: "Candy Crush Saga",
        image: "https://store-images.s-microsoft.com/image/apps.52397.13510798882606697.1816f804-e7fd-4295-9275-23dec3563baf.2ef7ec2d-2e37-489e-b6ac-b5f5e44d429c?q=90&w=480&h=270",
        description: "A match-3 puzzle game with one-touch controls, optional hints, and bright visuals for easy, accessible play at any pace.",
        rating: 4.8,
        reviews: 1504,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 5,
        title: "Call Of Duty®: Mobile",
        image: "https://tse2.mm.bing.net/th/id/OIP.R2HC-iT8-hP3fA85MOzMAwHaEK?rs=1&pid=ImgDetMain&o=7&rm=3",
        description: "A fast-paced shooter with customizable controls, aim assist, adjustable sensitivity, and UI scaling for improved accessibility.",
        rating: 4.5,
        reviews: 2721,
        tags: ["Action", "Multiplayer"]
    },
    {
        id: 6,
        title: "Pokemon Go",
        image: "https://assets.nintendo.eu/image/upload/f_auto,q_auto/v1624945924/NAL/Mobile/Pok%C3%A9mon%20GO/H2x1_SmartDevice_PokemonGo_London_enGB_image1600w.jpg",
        description: "An outdoor adventure game offering large UI icons, vibration cues, and simplified controls to support diverse physical abilities.",
        rating: 4.7,
        reviews: 1832,
        tags: ["Adventure", "RPG"]
    },
    {
        id: 7,
        title: "Subway Surfers",
        image: "https://calendar.cx/wp-content/uploads/2025/05/subwaysurfers.png",
        description: "A quick-reaction runner with simple swipe controls, strong color contrast, and optional assist features for ease of play.",
        rating: 4.8,
        reviews: 3642,
        tags: ["Arcade", "Casual"]
    },
    {
        id: 8,
        title: "Clash of Clans",
        image: "https://tse1.mm.bing.net/th/id/OIP.p4-VI-iEejx8YhFbnWopkwHaEK?rs=1&pid=ImgDetMain&o=7&rm=3",
        description: "A strategy game with slow-paced, tap-based controls and adjustable UI elements, making gameplay manageable for many players.",
        rating: 4.6,
        reviews: 2100,
        tags: ["Strategy", "Multiplayer"]
    },
    {
        id: 9,
        title: "Jigsawscapes® - Jigsaw Puzzles",
        image: "https://m.media-amazon.com/images/I/A1bxq4y1RXL.png",
        description: "A relaxing jigsaw puzzle app with zoom options, piece outlines, and soothing visuals designed for stress-free, accessible play.",
        rating: 4.9,
        reviews: 5200,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 10,
        title: "My Oasis: Relaxing, Satisfying",
        image: "https://cdn.aptoide.com/imgs/6/f/2/6f234be40d6930c031f8bcc99275416c_fgraphic.png",
        description: "A calming idle game with soft visuals, minimal input, and gentle soundscapes that support relaxation and low-pressure interaction.",
        rating: 4.7,
        reviews: 3100,
        tags: ["Simulation", "Casual"]
    },
    {
        id: 11,
        title: "Alto's Odyssey",
        image: "https://i.ytimg.com/vi/GcB7c1eHb6M/hqdefault.jpg",
        description: "A serene endless-runner with one-touch mechanics, high-contrast visuals, and an optional zen mode for stress-free play.",
        rating: 4.8,
        reviews: 2800,
        tags: ["Action", "Casual"]
    },
    {
        id: 12,
        title: "KAMI 2",
        image: "https://images.squarespace-cdn.com/content/v1/57b5d1f0d482e93404415213/1490788832173-0RY3VJV0MOS89WICFOGB/image-asset.jpeg",
        description: "A visually clean puzzle game with simple tap interactions and color-based challenges designed with calm, focused gameplay in mind.",
        rating: 4.6,
        reviews: 1900,
        tags: ["Puzzle", "Casual"]
    },
    {
        id: 13,
        title: "Sky: Children of the Light",
        image: "https://wallpaperaccess.com/full/5017316.jpg",
        description: "An emotional adventure with gentle controls, scalable visuals, cooperative play, and accessibility-friendly social mechanics.",
        rating: 4.9,
        reviews: 4500,
        tags: ["Adventure", "Multiplayer"]
    },
    {
        id: 14,
        title: "Stardew Valley",
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzag19Vq5eT2IZPNZZYU-PxjM1xLc6mEJEnQ&s",
        description: "A peaceful farming RPG offering remappable controls, no strict time pressure, and a play-at-your-own-pace structure ideal for accessibility.",
        rating: 4.8,
        reviews: 3800,
        tags: ["Simulation", "RPG"]
    },
    {
        id: 15,
        title: "A Blind Legend",
        image: "https://images.launchbox-app.com/4580cd6a-d441-4745-ac9d-4cc3df4df468.png",
        description: "A fully audio-based adventure designed for blind and visually impaired players, using binaural sound for navigation instead of visuals.",
        rating: 4.7,
        reviews: 2200,
        tags: ["Action", "Adventure"]
    }
];

function HomePage() {
    const [searchQuery, setSearchQuery] = useState('');
    const [favorites, setFavorites] = useState([]);
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

    const toggleFavorite = (gameId, event) => {
        event.stopPropagation();
        setFavorites(prev =>
            prev.includes(gameId)
                ? prev.filter(id => id !== gameId)
                : [...prev, gameId]
        );
    };

    const handleCardClick = (gameId) => {
        window.location.href = `/games/${gameId}`;
    };

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
            <Navbar />

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
                            <article
                                key={game.id}
                                className="game-card"
                                onClick={() => handleCardClick(game.id)}
                            >
                                <img src={game.image} alt={`${game.title} game cover`} />
                                <div className="card-content">
                                    <div className="card-header">
                                        <h3>{game.title}</h3>
                                        <button
                                            className="favorite-btn"
                                            aria-label="Add to favorites"
                                            onClick={(e) => toggleFavorite(game.id, e)}
                                        >
                                            <svg viewBox="0 0 24 24" fill={favorites.includes(game.id) ? "#ff3b30" : "none"}>
                                                <path
                                                    d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
                                                    stroke={favorites.includes(game.id) ? "#ff3b30" : "currentColor"}
                                                    strokeWidth="2"
                                                />
                                            </svg>
                                        </button>
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

            </main>
        </div>
    );
}

export default HomePage;