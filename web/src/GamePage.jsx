// Game details page.
// This component is reused for all games (1–15) based on the :id URL param.

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const API_BASE = "http://localhost:8081";

export default function GamePage() {
    // Read the game id from the URL: /games/:id
    const { id } = useParams();
    const gameId = Number(id || 1);

    // Remote data from the API
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Local UI state for “Report” flagging per review
    const [flagged, setFlagged] = useState({});

    // Local UI state for interactive star ratings in the review form (no POST yet)
    const [formRatings, setFormRatings] = useState({
        hearing: 0,
        motor: 0,
        cognitive: 0,
        vision: 0,
        gameplay: 0,
    });

    // ---------------------------------------------------------------------------
    // Fetch game details (Micronaut API) whenever the game id changes
    // ---------------------------------------------------------------------------
    useEffect(() => {
        setLoading(true);
        setError(null);

        fetch(`${API_BASE}/api/games/${gameId}`)
            .then(async (res) => {
                if (!res.ok) {
                    const text = await res.text();
                    throw new Error(`HTTP ${res.status}: ${text}`);
                }
                return res.json();
            })
            .then((json) => setData(json))
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [gameId]);

    // ---------------------------------------------------------------------------
    // Seed the “flagged” UI state from the reviews when data is loaded
    // ---------------------------------------------------------------------------
    useEffect(() => {
        if (!data?.reviews) return;
        const initial = {};
        data.reviews.forEach((r) => {
            if (r.id != null) initial[r.id] = !!r.flagged;
        });
        setFlagged(initial);
    }, [data]);

    // Toggle flag button for a specific review
    const toggleFlag = (id) => {
        setFlagged((prev) => ({ ...prev, [id]: !prev[id] }));
    };

    // Update local star rating values for the review form
    const updateFormRating = (key, value) => {
        setFormRatings((prev) => ({ ...prev, [key]: value }));
    };

    // Scroll helper for “Jump to” navigation
    const scrollToId = (sectionId) => {
        const el = document.getElementById(sectionId);
        if (el) {
            el.scrollIntoView({ behavior: "smooth", block: "start" });
        }
    };

    // ---------------------------------------------------------------------------
    // Loading / error states
    // ---------------------------------------------------------------------------
    if (loading) {
        return (
            <div className="page">
                <div className="loading">Loading game…</div>
            </div>
        );
    }

    if (error || !data) {
        return (
            <div className="page">
                <div className="error">Error: {error ?? "Unknown error"}</div>
            </div>
        );
    }

    // Destructure data returned from the Micronaut backend
    const {
        name,
        developer,
        ageRating,
        description,
        releaseDate,
        iconUrl, // kept for later use if needed in the hero section
        linkToStore,
        aboutVision,
        aboutHearing,
        aboutCognitive,
        aboutSpeech,
        aboutMotor,
        reviews,
        gallery,
        avgGameplayRating,
    } = data;

    const overallRating = avgGameplayRating ?? 0;

    // ---------------------------------------------------------------------------
    // Main render
    // ---------------------------------------------------------------------------
    return (
        <div className="page">
            <main className="content">
                {/* -------------------------------------------------------------------
           TITLE + AVERAGE RATING + DOWNLOAD BUTTON
           ------------------------------------------------------------------- */}
                <section className="hero-row">
                    <div className="hero-left">
                        <h1 className="game-title">{name}</h1>

                        <div className="top-rating-row">
                            <StarRating value={overallRating} />
                            <span className="rating-number">
                {overallRating.toFixed(1)} / 5
              </span>
                            {ageRating && (
                                <span className="rating-number" style={{ marginLeft: "8px" }}>
                  • {ageRating}
                </span>
                            )}
                        </div>
                    </div>

                    {linkToStore && (
                        <button
                            className="download-btn"
                            type="button"
                            onClick={() => window.open(linkToStore, "_blank")}
                        >
                            ⬇ Download Game
                        </button>
                    )}
                </section>

                {/* -------------------------------------------------------------------
           JUMP NAVIGATION (scrolls to sections on the page)
           ------------------------------------------------------------------- */}
                <nav className="jump-nav">
                    <span className="jump-label">Jump to:</span>
                    <button
                        className="jump-link"
                        type="button"
                        onClick={() => scrollToId("accessibility")}
                    >
                        Accessibility Features
                    </button>
                    <button
                        className="jump-link"
                        type="button"
                        onClick={() => scrollToId("reviews")}
                    >
                        Reviews
                    </button>
                    <button
                        className="jump-link"
                        type="button"
                        onClick={() => scrollToId("gallery")}
                    >
                        Gallery
                    </button>
                </nav>

                {/* -------------------------------------------------------------------
           OVERVIEW: about this game, developer, release date
           ------------------------------------------------------------------- */}
                <section id="overview" className="section-block">
                    <h2 className="section-heading underline">Overview</h2>

                    <h3 className="section-subheading">About This Game</h3>
                    <p className="body-text">{description}</p>

                    <hr className="divider" />

                    <h3 className="section-subheading">Developer</h3>
                    <p className="body-text">{developer}</p>

                    <hr className="divider" />

                    <h3 className="section-subheading">Release Date</h3>
                    <p className="body-text">
                        {releaseDate ?? "Release date not available"}
                    </p>
                </section>

                {/* -------------------------------------------------------------------
           ACCESSIBILITY FEATURES
           ------------------------------------------------------------------- */}
                <section id="accessibility" className="section-block">
                    <div className="section-heading-row">
                        <h2 className="section-heading underline">Accessibility Features</h2>
                        <span className="up-arrow">↑</span>
                    </div>

                    <div className="accessibility-list">
                        <AccessibilityCard title="Vision Support" text={aboutVision} />
                        <AccessibilityCard title="Hearing Support" text={aboutHearing} />
                        <AccessibilityCard title="Speech Support" text={aboutSpeech} />
                        <AccessibilityCard title="Motor Support" text={aboutMotor} />
                        <AccessibilityCard title="Cognitive Support" text={aboutCognitive} />
                    </div>
                </section>

                {/* -------------------------------------------------------------------
           REVIEWS: existing reviews + rating form
           ------------------------------------------------------------------- */}
                <section id="reviews" className="section-block reviews-layout">
                    {/* Existing reviews column */}
                    <div className="reviews-column">
                        <div className="section-heading-row">
                            <h2 className="section-heading underline">Reviews</h2>
                            <span className="up-arrow">↑</span>
                        </div>

                        {reviews && reviews.length > 0 ? (
                            reviews.map((r) => {
                                const isFlagged = flagged[r.id];
                                return (
                                    <article key={r.id} className="review-card">
                                        <header className="review-card-header">
                                            <div>
                                                <div className="review-username">
                                                    {r.userId ? `User${r.userId}` : "Anonymous"}
                                                </div>
                                                <div className="review-date">
                                                    {r.reviewDate
                                                        ? new Date(r.reviewDate).toLocaleDateString()
                                                        : ""}
                                                </div>
                                            </div>

                                            {/* Report  */}
                                            <button
                                                className={`report-btn ${isFlagged ? "active" : ""}`}
                                                type="button"
                                                onClick={() => toggleFlag(r.id)}
                                            >
                                                {isFlagged ? "🚩 Reported" : "⚑ Report"}
                                            </button>
                                        </header>

                                        <div className="review-ratings">
                                            <div>Vision: {r.visualRating ?? "-"} / 5</div>
                                            <div>Hearing: {r.hearingRating ?? "-"} / 5</div>
                                            <div>Cognitive: {r.cognitiveRating ?? "-"} / 5</div>
                                            <div>Speech: {r.speechRating ?? "-"} / 5</div>
                                            <div>Motor: {r.motorRating ?? "-"} / 5</div>
                                            <div>Gameplay: {r.gameplayRating ?? "-"} / 5</div>
                                        </div>

                                        <hr className="divider thin" />

                                        <p className="review-text">“{r.reviewText}”</p>
                                    </article>
                                );
                            })
                        ) : (
                            <p className="body-text">No reviews yet.</p>
                        )}
                    </div>

                    {/* Review form  */}
                    <aside className="review-form">
                        <p className="login-hint">Login to post a review…</p>
                        <button className="login-btn" type="button">
                            Login
                        </button>

                        <div className="rating-input-row">
                            <span>Hearing</span>
                            <StarRating
                                value={formRatings.hearing}
                                interactive
                                onChange={(v) => updateFormRating("hearing", v)}
                            />
                            <span>Motor</span>
                            <StarRating
                                value={formRatings.motor}
                                interactive
                                onChange={(v) => updateFormRating("motor", v)}
                            />
                        </div>

                        <div className="rating-input-row">
                            <span>Cognitive</span>
                            <StarRating
                                value={formRatings.cognitive}
                                interactive
                                onChange={(v) => updateFormRating("cognitive", v)}
                            />
                            <span>Vision</span>
                            <StarRating
                                value={formRatings.vision}
                                interactive
                                onChange={(v) => updateFormRating("vision", v)}
                            />
                        </div>

                        <div className="rating-input-row single">
                            <span>Gameplay</span>
                            <StarRating
                                value={formRatings.gameplay}
                                interactive
                                onChange={(v) => updateFormRating("gameplay", v)}
                            />
                        </div>

                        <textarea
                            className="review-textarea"
                            rows={6}
                            placeholder="Share details of your experience with this game"
                        />

                        <button className="submit-btn" type="button">
                            Submit
                        </button>
                    </aside>
                </section>

                {/* -------------------------------------------------------------------
           GALLERY: grid of images
           ------------------------------------------------------------------- */}
                <section id="gallery" className="section-block">
                    <div className="section-heading-row">
                        <h2 className="section-heading underline">Gallery</h2>
                        <span className="up-arrow">↑</span>
                    </div>

                    <div className="gallery-grid">
                        {gallery && gallery.length > 0 ? (
                            gallery.map((img) => (
                                <figure key={img.id} className="gallery-item">
                                    <img src={img.imageUrl} alt={img.altText} />
                                </figure>
                            ))
                        ) : (
                            <p className="body-text">No screenshots available.</p>
                        )}
                    </div>
                </section>
            </main>
        </div>
    );
}

/* ---------------------------------------------------------------------------
   Helper components
   ------------------------------------------------------------------------- */

/**
 * AccessibilityCard
 */
function AccessibilityCard({ title, text }) {
    if (!text) return null;
    return (
        <div className="accessibility-card">
            <h3 className="accessibility-title">{title}</h3>
            <p className="accessibility-text">{text}</p>
        </div>
    );
}

/**
 * StarRating
 * - Displays a 5-star rating.
 * - When interactive=true, stars are clickable and report a value (1–5)
 *   via the onChange callback. Hover preview is handled with local state.
 */
function StarRating({ value, interactive = false, onChange }) {
    const [hovered, setHovered] = useState(null);
    const display = hovered ?? value ?? 0;

    return (
        <div className={`star-row ${interactive ? "interactive" : ""}`}>
            {Array.from({ length: 5 }).map((_, i) => {
                const filled = i < display;
                return (
                    <span
                        key={i}
                        className={filled ? "star filled" : "star"}
                        onClick={() => interactive && onChange && onChange(i + 1)}
                        onMouseEnter={() => interactive && setHovered(i + 1)}
                        onMouseLeave={() => interactive && setHovered(null)}
                    >
            ★
          </span>
                );
            })}
        </div>
    );
}
