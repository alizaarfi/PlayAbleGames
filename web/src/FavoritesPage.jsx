import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";
import { games } from "./HomePage";
import "./style.css";

const USER_ID = 1; // same as HomePage for now

function FavoritesPage() {
    const [favorites, setFavorites] = useState([]);

    // Load favourites from backend when the page loads
    useEffect(() => {
        const loadFavorites = async () => {
            try {
                const res = await fetch(`/api/users/${USER_ID}/favorites`, {
                    credentials: "include",
                });

                console.log("GET /favorites status:", res.status, res.ok);

                if (!res.ok) {
                    return;
                }

                const data = await res.json();
                console.log("favorites raw data from API:", data);

                // ⚠ Cast everything to numbers so includes(game.id) works
                const ids = Array.isArray(data)
                    ? data.map((id) => Number(id))
                    : [];
                setFavorites(ids);
            } catch (err) {
                console.error("Failed to load favorites", err);
            }
        };

        loadFavorites();
    }, []);

    // Remove a favourite from this page (and backend)
    const toggleFavorite = async (gameId, event) => {
        event.stopPropagation();

        // optimistic update
        setFavorites((prev) => prev.filter((id) => id !== gameId));

        try {
            const res = await fetch(`/api/users/${USER_ID}/favorites/${gameId}`, {
                method: "DELETE",
                credentials: "include",
            });

            console.log("DELETE /favorites status:", res.status, res.ok);

            if (!res.ok) {
                // rollback if API failed
                setFavorites((prev) =>
                    prev.includes(gameId) ? prev : [...prev, gameId]
                );
            }
        } catch (err) {
            console.error("Failed to delete favorite", err);
            // rollback on error
            setFavorites((prev) =>
                prev.includes(gameId) ? prev : [...prev, gameId]
            );
        }
    };

    // Only keep games whose id is in favorites
    const favoriteGames = games.filter((game) =>
        favorites.includes(game.id)
    );

    const renderStars = () => "★★★★★";

    const handleCardClick = (gameId) => {
        window.location.href = `/games/${gameId}`;
    };

    return (
        <div className="app">
            <Navbar />
            <main className="container">
                <section className="hero">
                    <h2>Your Favourite Games</h2>
                    <p>Games you’ve marked with a heart.</p>
                </section>

                {favoriteGames.length === 0 ? (
                    <p>You haven’t favourited any games yet.</p>
                ) : (
                    <section className="games-grid">
                        {favoriteGames.map((game) => (
                            <article
                                key={game.id}
                                className="game-card"
                                onClick={() => handleCardClick(game.id)}
                            >
                                <img
                                    src={game.image}
                                    alt={`${game.title} game cover`}
                                />
                                <div className="card-content">
                                    <div className="card-header">
                                        <h3>{game.title}</h3>
                                        <button
                                            className="favorite-btn"
                                            aria-label="Remove from favorites"
                                            onClick={(e) =>
                                                toggleFavorite(game.id, e)
                                            }
                                        >
                                            <svg
                                                viewBox="0 0 24 24"
                                                fill={
                                                    favorites.includes(game.id)
                                                        ? "#ff3b30"
                                                        : "none"
                                                }
                                            >
                                                <path
                                                    d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
                                                    stroke={
                                                        favorites.includes(
                                                            game.id
                                                        )
                                                            ? "#ff3b30"
                                                            : "currentColor"
                                                    }
                                                    strokeWidth="2"
                                                />
                                            </svg>
                                        </button>
                                    </div>

                                    <p className="game-description">
                                        {game.description}
                                    </p>

                                    <div className="game-meta">
                                        <div className="rating">
                                            <div
                                                className="stars"
                                                aria-label={`Rating: ${game.rating} out of 5 stars`}
                                            >
                                                {renderStars(game.rating)}
                                            </div>
                                            <span>
                                                {game.rating} (
                                                {game.reviews.toLocaleString()})
                                            </span>
                                        </div>
                                    </div>

                                    <div className="tags">
                                        {game.tags.map((tag) => (
                                            <span
                                                key={tag}
                                                className="tag"
                                            >
                                                {tag}
                                            </span>
                                        ))}
                                    </div>
                                </div>
                            </article>
                        ))}
                    </section>
                )}
            </main>
        </div>
    );
}

export default FavoritesPage;
