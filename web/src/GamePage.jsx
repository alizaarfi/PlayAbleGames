import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
// import Navbar from "./NavBar.jsx"; // this should not be here

import "./style.css";

// Base URL for the Micronaut backend API.
const API_BASE = "http://localhost:8081";

// Reads the JWT token from localStorage.
const getToken = () =>
    localStorage.getItem("authToken") ||
    localStorage.getItem("token") ||
    localStorage.getItem("jwt");

// toast notification
const showToast = (msg) => {
    const el = document.createElement("div");
    el.className = "toast-notice";
    el.textContent = msg;

    document.body.appendChild(el);

    requestAnimationFrame(() => {
        el.classList.add("visible");
    });

    setTimeout(() => {
        el.classList.remove("visible");
        setTimeout(() => el.remove(), 300);
    }, 2500);
};

export default function GamePage() {
    // Game id from URL /games/:id, defaulting to 1.
    const { id } = useParams();
    const gameId = Number(id || 1);

    // Game details received from the backend.
    const [data, setData] = useState(null);

    // Loading and error state.
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Tracks which reviews are locally marked as "reported".
    const [flagged, setFlagged] = useState({});

    // Text for the review form.
    const [reviewText, setReviewText] = useState("");

    // Ratings selected in the review form.
    const [formRatings, setFormRatings] = useState({
        hearing: 0,
        motor: 0,
        cognitive: 0,
        vision: 0,
        gameplay: 0,
        speech: 0,
    });

    // Logged-in state derived from token.
    const [isLoggedIn, setIsLoggedIn] = useState(!!getToken());

    // Maximum length for a review.
    const maxChars = 500;


    // check login state on mount and when token changes in other windows
    useEffect(() => {
        const handleStorage = (event) => {
            if (!event.key) return;
            if (
                event.key === "authToken" ||
                event.key === "token" ||
                event.key === "jwt"
            ) {
                const hasToken = !!getToken();
                setIsLoggedIn(hasToken);
                if (hasToken) {
                    showToast("Login detected. You can now leave a review.");
                }
            }
        };

        setIsLoggedIn(!!getToken());
        window.addEventListener("storage", handleStorage);

        return () => {
            window.removeEventListener("storage", handleStorage);
        };
    }, []);

    // fetch game data
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

    // seed flagged state
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

    // toggle local report flag
    const toggleFlag = (reviewId) => {
        setFlagged((prev) => ({ ...prev, [reviewId]: !prev[reviewId] }));
    };

    // update rating field in form
    const updateFormRating = (key, value) => {
        setFormRatings((prev) => ({ ...prev, [key]: value }));
    };

    // scroll helper
    const scrollToId = (sectionId) => {
        const el = document.getElementById(sectionId);
        if (el) {
            el.scrollIntoView({ behavior: "smooth", block: "start" });
        }
    };

    // review submission handler
    const handleSubmitReview = async () => {
        setIsAccumulatingText(false);
        setReviewMode(false);
        setVoicePromptMessage('');

        const token = getToken();
        if (!token) {
            showToast("You must log in to leave a review.");
            setIsLoggedIn(false);
            return;
        }
        setIsLoggedIn(true);

        if (!reviewText.trim()) {
            showToast("Review text cannot be empty.");
            return;
        }

        if (reviewText.length > maxChars) {
            showToast(`Review text cannot exceed ${maxChars} characters.`);
            return;
        }

        // prevent basic HTML injection
        const safeText = reviewText.replace(/[<>]/g, "");

        const payload = {
            hearingRating: formRatings.hearing || 0,
            motorRating: formRatings.motor || 0,
            cognitiveRating: formRatings.cognitive || 0,
            visionRating: formRatings.vision || 0,
            speechRating: formRatings.speech || 0,
            gameplayRating: formRatings.gameplay || 0,
            reviewText: safeText,
        };

        try {
            const res = await fetch(`${API_BASE}/api/games/${gameId}/reviews`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            if (!res.ok) {
                const text = await res.text();
                console.error("Failed to submit review:", text);
                showToast("Could not submit review (server error).");
                return;
            }

            const saved = await res.json();

            // append new review locally
            setData((prev) => ({
                ...prev,
                reviews: [...(prev.reviews || []), saved],
            }));

            // reset form
            setFormRatings({
                hearing: 0,
                motor: 0,
                cognitive: 0,
                vision: 0,
                gameplay: 0,
                speech: 0,

            });
            setReviewText("");

            showToast("Review submitted successfully.");
        } catch (err) {
            console.error("Failed to submit review:", err);
            showToast("Network error while submitting review.");
        }
    };

    // VOICE COMMANDS FOR REVIEW
    // voice reviewing need for states
    const [reviewMode, setReviewMode] = useState(false);
    const [voicePromptMessage, setVoicePromptMessage] = useState('');
    const [isInTextMode, setIsInTextMode] = useState(false);
    const [isAccumulatingText, setIsAccumulatingText] = useState(false);

    useEffect(() => {
        const handleVoiceReviewCommand = (event) => {
            const { type, target } = event.detail;

            if (!isLoggedIn && type.startsWith('REVIEW_')) {
                showToast('Please log in to write a review');
                return;
            }

            switch(type) {
                case 'REVIEW_START':
                    startReviewMode();
                    break;

                case 'REVIEW_RATING':
                    handleVoiceRating(target); // "hearing:5"
                    break;

                case 'REVIEW_COMMENT':
                    handleVoiceText(target);
                    break;

                case 'REVIEW_SUBMIT':
                    handleSubmitReview();
                    break;

                case 'REVIEW_CANCEL':
                    exitReviewMode();
                    break;
            }
        };

        window.addEventListener('voiceCommand', handleVoiceReviewCommand);
        return () => window.removeEventListener('voiceCommand', handleVoiceReviewCommand);
    }, [isLoggedIn, reviewMode, formRatings, reviewText]);


    useEffect(() => {
        const handleTranscriptionAccumulation = (event) => {
            const { transcription, commandType } = event.detail;

            // Only accumulate if in text mode AND it's raw speech (not a command)
            if (isAccumulatingText && transcription && commandType === 'UNKNOWN') {
                // This is raw speech that didn't match a command - add it to review
                const newText = reviewText
                    ? `${reviewText} ${transcription}`
                    : transcription;

                setReviewText(newText.slice(0, maxChars));
                console.log("✅ Accumulated text:", transcription);
            }

            // Stop accumulating if user says a recognized command
            if (isAccumulatingText && commandType &&
                commandType !== 'NO_SPEECH' &&
                commandType !== 'UNKNOWN') {
                setIsAccumulatingText(false);
                console.log("⏹️ Stopped accumulating - command detected:", commandType);
            }
        };

        window.addEventListener('transcriptionUpdate', handleTranscriptionAccumulation);
        return () => window.removeEventListener('transcriptionUpdate', handleTranscriptionAccumulation);
    }, [isAccumulatingText, reviewText, maxChars]);

    //All helper functions needed for voice commanded reviews in useEffect aboce

    // Start review mode
    const startReviewMode = () => {
        if (!isLoggedIn) {
            showToast('Please log in to write a review');
            return;
        }

        setReviewMode(true);
        setVoicePromptMessage('Say a category and rating, like "rate hearing 5"');
        showToast('Review mode active - Start rating');

        // Auto-scroll to review section
        scrollToId('reviews');
    };

    // Handle ratings
    const handleVoiceRating = (target) => {
        if (!reviewMode) return;

        const [category, ratingStr] = target.split(':');
        const rating = parseInt(ratingStr);

        if (rating >= 1 && rating <= 5) {
            updateFormRating(category, rating);
            showToast(`${category} rated ${rating}/5`);
            setVoicePromptMessage('Rate another category, or say "review text" to add comments');
        }
    };

    // Handle voice text input
    const handleVoiceText = (text) => {
        if (!reviewMode) return;

        // for accumulating approach to capture recording chunks
        setIsAccumulatingText(true);
        setIsInTextMode(true);

        // Append to existing review text
        const newText = reviewText
            ? `${reviewText} ${text}`
            : text;

        setReviewText(newText.slice(0, maxChars));
        setVoicePromptMessage('Continue speaking, or say "submit review" when done');
    };

    // Exit review mode
    const exitReviewMode = () => {
        setReviewMode(false);
        setVoicePromptMessage('');
        setIsAccumulatingText(false);
        setIsInTextMode(false);
        showToast('Review mode cancelled');
    };



    // loading and error states
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

    // destructure backend data
    const {
        name,
        developer,
        ageRating,
        description,
        releaseDate,
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

    // render
    return (
        <div className="page">
            {/*<Navbar />*/}
            <main className="content">
                {/* header: title and rating */}
                <section className="hero-row">
                    <div className="hero-left">
                        <h1 className="game-title">{name}</h1>

                        <div className="top-rating-row">
                            <StarRating value={overallRating} />
                            <span className="rating-number">
                                {overallRating.toFixed(1)} / 5
                            </span>
                            {ageRating && (
                                <span
                                    className="rating-number"
                                    style={{ marginLeft: "8px" }}
                                >
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

                {/* jump navigation */}
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

                {/* overview section */}
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

                {/* accessibility section */}
                <section id="accessibility" className="section-block">
                    <div className="section-heading-row">
                        <h2 className="section-heading underline">
                            Accessibility Features
                        </h2>
                        <button
                            type="button"
                            className="up-arrow"
                            onClick={() => scrollToId("overview")}
                            aria-label="Back to overview"
                        >↑</button>
                    </div>

                    <div className="accessibility-list">
                        <AccessibilityCard title="Vision Support" text={aboutVision} />
                        <AccessibilityCard title="Hearing Support" text={aboutHearing} />
                        <AccessibilityCard title="Speech Support" text={aboutSpeech} />
                        <AccessibilityCard title="Motor Support" text={aboutMotor} />
                        <AccessibilityCard
                            title="Cognitive Support"
                            text={aboutCognitive}
                        />
                    </div>
                </section>

                {/* reviews section */}
                <section id="reviews" className="section-block reviews-layout">
                    {/* existing reviews */}
                    <div className="reviews-column">
                        <div className="section-heading-row">
                            <h2 className="section-heading underline">Reviews</h2>
                            <button
                                type="button"
                                className="up-arrow"
                                onClick={() => scrollToId("accessibility")}
                                aria-label="Back to accessibility section"
                            >↑</button>
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
                                                        ? new Date(
                                                            r.reviewDate
                                                        ).toLocaleDateString()
                                                        : ""}
                                                </div>
                                            </div>

                                            <button
                                                className={`report-btn ${
                                                    isFlagged ? "active" : ""
                                                }`}
                                                type="button"
                                                onClick={() => toggleFlag(r.id)}
                                            >
                                                {isFlagged ? "🚩 Reported" : "⚑ Report"}
                                            </button>
                                        </header>

                                        <div className="review-ratings">
                                            <div>Vision: {r.visionRating ?? "-"} / 5</div>
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

                    {/* review form */}
                    <aside className="review-form">
                        {/* Voice prompt display */}
                        {reviewMode && voicePromptMessage && (
                            <div className="voice-prompt-banner">
                                🎤 {voicePromptMessage}
                            </div>
                        )}
                        {!isLoggedIn && (
                            <>
                                <p className="login-hint">
                                    You must be logged in to post a review.
                                </p>
                                <button
                                    className="login-btn"
                                    type="button"
                                    onClick={() => {
                                        // remember the page the user came from
                                        const redirectPath = `/games/${gameId}`;
                                        localStorage.setItem(
                                            "redirectAfterLogin",
                                            redirectPath
                                        );

                                        // feedback
                                        showToast("Redirecting to login…");

                                        // full-page login
                                        window.location.href = "/login";
                                    }}
                                >
                                    Login
                                </button>
                            </>
                        )}

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
                            <span>Speech</span>
                            <StarRating
                                value={formRatings.speech}
                                interactive
                                onChange={(v) => updateFormRating("speech", v)}
                            />
                            <span>Gameplay</span>
                            <StarRating
                                value={formRatings.gameplay}
                                interactive
                                onChange={(v) => updateFormRating("gameplay", v)}
                            />
                        </div>

                        {/*<textarea*/}
                        {/*    className="review-textarea"*/}
                        {/*    rows={6}*/}
                        {/*    placeholder="Share details of your experience with this game"*/}
                        {/*    value={reviewText}*/}
                        {/*    onChange={(e) => {*/}
                        {/*        const value = e.target.value.slice(0, maxChars);*/}
                        {/*        setReviewText(value);*/}
                        {/*    }}*/}
                        {/*/>*/}
                        {/*/!* Textarea with voice indicator *!/*/}
                        {/*<textarea*/}
                        {/*    className={`review-textarea ${reviewMode ? 'voice-active' : ''}`}*/}
                        {/*    rows={6}*/}
                        {/*    placeholder={*/}
                        {/*        reviewMode*/}
                        {/*            ? "🎤 Speak to add text..."*/}
                        {/*            : "Share details of your experience with this game"*/}
                        {/*    }*/}
                        {/*    value={reviewText}*/}
                        {/*    onChange={(e) => setReviewText(e.target.value.slice(0, maxChars))}*/}
                        {/*/>*/}

                        <textarea
                            className={`review-textarea ${reviewMode ? 'voice-active' : ''}`}
                            rows={6}
                            placeholder={
                                reviewMode
                                    ? "🎤 Speak to add text..."
                                    : "Share details of your experience with this game"
                            }
                            value={reviewText}
                            onChange={(e) => {
                                const value = e.target.value.slice(0, maxChars);
                                setReviewText(value);
                            }}
                        />

                        {/* live character counter */}
                        <div className="char-counter">
                            {reviewText.length} / {maxChars} characters
                        </div>

                        <button
                            className="submit-btn"
                            type="button"
                            onClick={handleSubmitReview}
                        >
                            Submit
                        </button>
                    </aside>
                </section>

                {/* gallery section */}
                <section id="gallery" className="section-block">
                    <div className="section-heading-row">
                        <h2 className="section-heading underline">Gallery</h2>
                        <button
                            type="button"
                            className="up-arrow"
                            onClick={() => scrollToId("reviews")}
                            aria-label="Back to reviews section"
                        >↑</button>
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

// Accessibility card for each support category.
function AccessibilityCard({ title, text }) {
    if (!text) return null;
    return (
        <div className="accessibility-card">
            <h3 className="accessibility-title">{title}</h3>
            <p className="accessibility-text">{text}</p>
        </div>
    );
}

// Star rating display and interactive control.
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
