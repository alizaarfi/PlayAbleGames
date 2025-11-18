// GamePage.jsx
// -------------------------------
// Game details page with reviews, accessibility sections, and gallery.
// This component is reused for all games (1–15) based on the :id URL parameter.
// -------------------------------

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

/**
 * Base URL for the Micronaut backend API.
 * The backend is currently running on port 8081.
 */
const API_BASE = "http://localhost:8081";

export default function GamePage() {
    /**
     * The game id is taken from the URL pattern /games/:id.
     * If no id is present, we default to game 1.
     */
    const { id } = useParams();
    const gameId = Number(id || 1);

    /**
     * Data retrieved from the backend for this game.
     * This includes: basic game details, reviews, and gallery images.
     */
    const [data, setData] = useState(null);

    /**
     * Simple loading and error states for basic user feedback.
     */
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    /**
     * Local UI state to record which reviews the user has marked as “reported”.
     * This is currently only visual and not persisted to the backend.
     */
    const [flagged, setFlagged] = useState({});

    /**
     * Local UI state for the text content of a review
     * that the user is writing in the form.
     */
    const [reviewText, setReviewText] = useState("");

    /**
     * Local UI state for interactive star ratings in the review form.
     * These values are later sent to the backend when submitting a review.
     */
    const [formRatings, setFormRatings] = useState({
        hearing: 0,
        motor: 0,
        cognitive: 0,
        vision: 0,
        gameplay: 0,
    });

    // ---------------------------------------------------------------------------
    // Fetch game details from the Micronaut backend when the game id changes
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
    // Seed the “flagged” state based on reviews returned from the backend
    // ---------------------------------------------------------------------------

    useEffect(() => {
        if (!data?.reviews) return;
        const initial = {};
        data.reviews.forEach((r) => {
            if (r.id != null) {
                initial[r.id] = !!r.flagged;
            }
        });
        setFlagged(initial);
    }, [data]);

    /**
     * Toggles the reported status for a given review in local UI state.
     * This is only a front-end effect at present (no backend update).
     */
    const toggleFlag = (reviewId) => {
        setFlagged((prev) => ({ ...prev, [reviewId]: !prev[reviewId] }));
    };

    /**
     * Updates a single rating entry in the combined rating state
     * used by the review submission form.
     */
    const updateFormRating = (key, value) => {
        setFormRatings((prev) => ({ ...prev, [key]: value }));
    };

    /**
     * Simple scroll helper to allow “Jump to section” links to move
     * the viewport to different semantic sections on the page.
     */
    const scrollToId = (sectionId) => {
        const el = document.getElementById(sectionId);
        if (el) {
            el.scrollIntoView({ behavior: "smooth", block: "start" });
        }
    };

    /**
     * Handles submission of a new review to the backend.
     * At this stage the system does not use real authentication,
     * so the backend temporarily associates the review with a fixed test user.
     */
    const handleSubmitReview = async () => {

        if (!reviewText.trim()) {
            alert("Review text cannot be empty.");
            return;
        }

        if (reviewText.length > 500) {
            alert("Review text cannot exceed 500 characters.");
            return;
        }

        //preventing HTML injections
        const safeText = reviewText.replace(/[<>]/g, "");

        const payload = {
            hearingRating: formRatings.hearing || 0,
            motorRating: formRatings.motor || 0,
            cognitiveRating: formRatings.cognitive || 0,
            visualRating: formRatings.vision || 0,
            gameplayRating: formRatings.gameplay || 0,
            reviewText: reviewText,
        };

        try {
            const res = await fetch(`${API_BASE}/api/games/${gameId}/reviews`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    // In future work: include an authentication token here.
                    // Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            if (!res.ok) {
                const text = await res.text();
                console.error("Failed to submit review:", text);
                alert("Could not submit review (server error).");
                return;
            }

            const saved = await res.json();

            // Update local state so that the new review appears immediately.
            setData((prev) => ({
                ...prev,
                reviews: [...(prev.reviews || []), saved],
            }));

            // Reset the form state to give clear feedback to the user.
            setFormRatings({
                hearing: 0,
                motor: 0,
                cognitive: 0,
                vision: 0,
                gameplay: 0,
            });
            setReviewText("");
        } catch (err) {
            console.error("Failed to submit review:", err);
            alert("Network error while submitting review.");
        }
    };

    // ---------------------------------------------------------------------------
    // Loading and error feedback to the user
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

    // ---------------------------------------------------------------------------
    // Destructure the domain-specific properties supplied by the backend
    // ---------------------------------------------------------------------------

    const {
        name,
        developer,
        ageRating,
        description,
        releaseDate,
        iconUrl, // currently unused in the hero; retained for future enhancement
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

    /**
     * Overall rating is derived from the average gameplay rating.
     */
    const overallRating = avgGameplayRating ?? 0;

    // ---------------------------------------------------------------------------
    // Main render of the game page
    // ---------------------------------------------------------------------------

    return (
        <div className="page">
            <main className="content">
                {/* -------------------------------------------------------------------
             Section: title, overall rating, and download link
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
                    Jump Navigation Section
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
                    Overview of the game, developer, and release date
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
                    Accessibility information grouped by category
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
                    Reviews list and review submission form
           ------------------------------------------------------------------- */}
                <section id="reviews" className="section-block reviews-layout">
                    {/* Column that displays existing reviews */}
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

                                            {/* Local “report” control for coursework demonstration */}
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

                    {/* Column that contains the review form for user input */}
                    <aside className="review-form">
                        <p className="login-hint">
                            You must be logged in to post a review.
                        </p>

                        <button
                            className="login-btn"
                            type="button"
                            onClick={() => alert("Login system coming soon…")}
                        >
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
                            value={reviewText}
                            onChange={(e) => setReviewText(e.target.value)}
                        />

                        <button
                            className="submit-btn"
                            type="button"
                            onClick={handleSubmitReview}
                        >
                            Submit
                        </button>
                    </aside>
                </section>

                {/* -------------------------------------------------------------------
                    Gallery of images for the game
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
                                    <img
                                        src={img.imageUrl}
                                        alt={img.altText || "Screenshot from the game"}
                                    />
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
   Helper component: AccessibilityCards
--------------------------------------------------------------------------- */

function AccessibilityCard({ title, text }) {
    if (!text) return null;
    return (
        <div className="accessibility-card">
            <h3 className="accessibility-title">{title}</h3>
            <p className="accessibility-text">{text}</p>
        </div>
    );
}

/* ---------------------------------------------------------------------------
   Helper component: StarRating
--------------------------------------------------------------------------- */

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
