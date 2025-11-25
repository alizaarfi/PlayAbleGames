import React, { useEffect, useState } from "react";
import "./style.css";
import "./FlaggedReviews.css";

const API_BASE = "http://localhost:8081/api";

export default function FlaggedReviews() {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        const controller = new AbortController();

        async function fetchFlaggedReviews() {
            setLoading(true);
            setError(null);
            try {
                const res = await fetch(`${API_BASE}/flaggedReviews`, {
                    signal: controller.signal,
                });
                if (!res.ok) throw new Error("Failed to fetch flagged reviews data.");
                const data = await res.json();

                // Normalize response into an array of review objects
                let normalized = [];
                if (Array.isArray(data)) {
                    normalized = data;
                } else if (Array.isArray(data.reviews)) {
                    normalized = data.reviews;
                } else if (data.review) {
                    normalized = [data.review];
                }

                // Ensure each review has expected fields with defaults
                const mapped = normalized.map((r) => ({
                    id: r.id ?? null,
                    userId: r.userId ?? null,
                    user: r.user ?? "user",
                    game: r.game ?? "game",
                    hearingRating: r.hearingRating ?? 0,
                    visionRating: r.visionRating ?? 0,
                    cognitiveRating: r.cognitiveRating ?? 0,
                    speechRating: r.speechRating ?? 0,
                    motorRating: r.motorRating ?? 0,
                    gamePlayRating: r.gamePlayRating ?? 0,
                    reviewText: r.reviewText ?? "reviewText",
                    reviewDate: r.reviewDate ?? "reviewDate",
                }));

                setReviews(mapped);
            } catch (e) {
                if (e.name !== "AbortError") setError(e.message);
            } finally {
                setLoading(false);
            }
        }

        fetchFlaggedReviews();
        return () => controller.abort();
    }, []);

    if (loading)
        return (
            <div className="page">
                <div className="content"> Loading... </div>
            </div>
        );
    if (error)
        return (
            <div className="page">
                <div className="content"> Error: {error} </div>
            </div>
        );

    return (
        <div className="page">
            <div className="content">
                <h1 className="title"> Flagged Reviews </h1>

                {reviews.length === 0 ? (
                    <div className="no-reviews">No flagged reviews found.</div>
                ) : (
                    reviews.map((review, idx) => (
                        <div className="reviews-box" key={review.id ?? idx}>
                            <div className="review-item">
                                <div className="titles">
                                    <h2 className="black-text"> User: {review.user} </h2>
                                    <h2 className="black-text"> Game: {review.game} </h2>
                                    <h3 className="black-text"> Review Date: {review.reviewDate} </h3>
                                </div>
                                <div className="ratings">
                                    <h3 className="black-text"> Ratings: </h3>
                                    <p className="black-text"> Hearing Rating: {review.hearingRating} </p>
                                    <p className="black-text"> Vision Rating: {review.visionRating} </p>
                                    <p className="black-text"> Cognitive Rating: {review.cognitiveRating} </p>
                                    <p className="black-text"> Speech Rating: {review.speechRating} </p>
                                    <p className="black-text"> Motor Rating: {review.motorRating} </p>
                                    <p className="black-text"> Gameplay Rating: {review.gamePlayRating} </p>
                                </div>
                                <div className="review-text">
                                    <p className="black-text"> Review Text: {review.reviewText} </p>
                                </div>
                            </div>
                            <div className="options-box">
                                <h2 className="black-text"> Options </h2>
                                <div className="button-box">
                                    <button> See user's profile </button>
                                    <button> Approve review </button>
                                    <button> Remove review </button>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
