import React from "react";
// import Navbar from "./NavBar.jsx";
import { useFavorites } from "./FavoritesContext.jsx";
import { games } from "./HomePage.jsx";
import "./favourites_page.css";

function FavoritesPage() {

    // ----- Access global favorites state and toggle function
    const { favorites, toggleFavorite, loading } = useFavorites();

    // ----- Filter the full games list to only those marked as favorites
    const favoriteGames = games.filter((g) => favorites.includes(g.id));

    return (
        <div className="app">
            {/*<Navbar />*/}

            <main className="main-layout">
                <header className="page-header">
                    <h1>Your Favourites</h1>

                    {/* ----- Only show helper text when no favorites have been added yet ----- */}
                    {favoriteGames.length === 0 && (
                        <p>Add games to your favorites using the heart button.</p>
                    )}
                </header>

                {/* ----- Show loading text while backend favorites are being fetched ----- */}
                {loading ? (
                    <p>Loading favorites…</p>
                ) : favoriteGames.length === 0 ? (
                    <p>You haven’t added any favorites yet. Tap the ♥ on a game to add it.</p>
                ) : (

                    // ----- Display grid of favorite games once they exist -----
                    <section className="games-grid">
                        {favoriteGames.map((game) => (
                            <article
                                key={game.id}
                                className="game-card"
                                onClick={() => (window.location.href = `/games/${game.id}`)}
                            >
                                <img src={game.image} alt={game.title} />
                                <div className="game-card-header">
                                    <h3>{game.title}</h3>

                                    {/* ----- Heart button removes a game from favorites ----- */}
                                    <button
                                        type="button"
                                        className="favorite-btn"
                                        aria-label="Remove from favorites"
                                        onClick={(e) => toggleFavorite(game.id, e)}
                                    >
                                        ♥
                                    </button>
                                </div>
                                <p>{game.description}</p>
                            </article>
                        ))}
                    </section>
                )}
            </main>
        </div>
    );
}

export default FavoritesPage;
