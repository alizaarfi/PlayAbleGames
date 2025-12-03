import React, { useEffect, useState } from "react";
import { useAuth } from "./hooks/AuthProvider.jsx";
import "./style.css";
import "./FlaggedReviews.css";

const API_BASE = "http://localhost:8081/api";

export default function FlaggedReviews() {

    const { token } = useAuth();
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
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                if (!res.ok) throw new Error("Failed to fetch flagged reviews data.");
                const data = await res.json();

                let normalized = [];
                if (Array.isArray(data)) {
                    normalized = data;
                } else if (Array.isArray(data.reviews)) {
                    normalized = data.reviews;
                } else if (data.review) {
                    normalized = [data.review];
                }

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
    }, [token]);

    // Accept review handler
    const handleAccept = async (reviewId) => {
        try {
            const res = await fetch(`${API_BASE}/reviews/${reviewId}/accept`, {
                method: 'PUT'
            });

            if (res.ok) {
                const result = await res.json();
                if (result.success) {
                    setReviews(reviews.filter(r => r.id !== reviewId));
                }
            } else {
                setError('Failed to accept review');
            }
        } catch (e) {
            setError('Failed to accept review: ' + e.message);
        }
    };

    // Decline review handler
    const handleDecline = async (reviewId) => {
        try {
            const res = await fetch(`${API_BASE}/reviews/${reviewId}/decline`, {
                method: 'PUT'
            });

            if (res.ok) {
                const result = await res.json();
                if (result.success) {
                    setReviews(reviews.filter(r => r.id !== reviewId));
                }
            } else {
                setError('Failed to decline review');
            }
        } catch (e) {
            setError('Failed to decline review: ' + e.message);
        }
    };

    const goToProfile = (userId) => {
        window.location.href = `/users/${userId}`;
    }


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

    // Flagged Reviews Page html code here
    return (
            <div className="content">
                <div className="admin-nav">
                    <button><a className="white-text" href="/admin"> Back to Dashboard </a></button>
                </div>
                <h1 className="title black-text"> Flagged Reviews </h1>

                <div className="reviews-container">
                    {reviews.length === 0 ? (
                        <div className="no-reviews black-text">No flagged reviews found.</div>
                    ) : (
                        reviews.map((review, idx) => (
                            <div className="reviews-box" key={review.id ?? idx}>
                                <div className="review-item">
                                    <div className="titles">
                                        <h2 className="black-text"> {review.user} · {review.game}</h2>
                                        <h3 className="black-text"> {review.reviewDate[8] + review.reviewDate[9] + '/' + review.reviewDate[5] + review.reviewDate[6] + '/' +  review.reviewDate[0] + review.reviewDate[1] + review.reviewDate[2] + review.reviewDate[3] } </h3>
                                    </div>
                                    <div className="ratings">
                                        <p className="black-text rating-text"> Hearing: {review.hearingRating}/5 </p>
                                        <p className="black-text rating-text"> Vision: {review.visionRating}/5 </p>
                                        <p className="black-text rating-text"> Cognitive: {review.cognitiveRating}/5 </p>
                                        <p className="black-text rating-text"> Speech: {review.speechRating}/5 </p>
                                        <p className="black-text rating-text"> Motor: {review.motorRating}/5 </p>
                                        <p className="black-text rating-text"> Gameplay: {review.gamePlayRating}/5 </p>
                                    </div>
                                    <div className="review-text">
                                        <p className="black-text reviewText"> {review.reviewText} </p>
                                    </div>
                                </div>
                                <div className="options-box">
                                    <h2 className="black-text"> Options </h2>
                                    <div className="button-box">
                                        <button onClick={() => goToProfile(review.userId)}> See user's profile </button>
                                        <button onClick={() => handleAccept(review.id)}> Approve review </button>
                                        <button onClick={() => handleDecline(review.id)}> Remove review </button>
                                    </div>
                                </div>
                            </div>

                        ))
                    )}
                </div>
            </div>
    );
}
