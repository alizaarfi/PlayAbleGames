import React, { useEffect, useState } from "react";
import "./style.css";
import "./IndividualUser.css"
import "./FlaggedReviews.css"
import {useParams} from "react-router-dom";

const API_BASE = "http://localhost:8081/api";

export default function IndividualUser(){

    const { userId } = useParams();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(
        {
            userId: userId,
            username: "Unknown User",
            enabled: false,
        }
    );
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        const controller = new AbortController();

        async function fetchUserData() {
            setLoading(true);
            setError(null);
            try {
                const res = await fetch(`${API_BASE}/users/${userId}`, {
                    signal: controller.signal,
                });

                if (!res.ok) throw new Error("Failed to fetch user data.");
                const data = await res.json();

                setUser({
                    userId: data.userId ?? userId,
                    username: data.username ?? "Unknown User",
                    enabled: data.enabled ?? false,
                });

                let normalized = [];
                if (Array.isArray(data.reviews)) {
                    normalized = data.reviews;
                }

                const mapped = normalized.map((reviews) => ({
                    gameID: reviews.id ?? null,
                    game: reviews.game ?? "game",
                    hearingRating: reviews.hearingRating ?? 0,
                    visionRating: reviews.visionRating ?? 0,
                    cognitiveRating: reviews.cognitiveRating ?? 0,
                    speechRating: reviews.speechRating ?? 0,
                    motorRating: reviews.motorRating ?? 0,
                    gamePlayRating: reviews.gamePlayRating ?? 0,
                    reviewText: reviews.reviewText ?? "",
                    reviewDate: reviews.reviewDate ?? "",
                    flagged: reviews.flagged ?? false,
                    removed: reviews.removed ?? false,
                }));

                setReviews(mapped);
            } catch (e) {
                if (e.name !== "AbortError") setError(e.message);
            } finally {
                setLoading(false);
            }
        }

        fetchUserData();
        return () => controller.abort();
    }, [userId]);

    const banUser = async (userId) => {
        try {
            const res = await fetch(`${API_BASE}/users/${userId}/ban`, {
                method: 'POST',
            });
            if (!res.ok) throw new Error("Failed to ban user.");
            alert("User has been banned successfully.");
            window.location.reload();
        } catch (e) {
            alert(`Error: ${e.message}`);
        }
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

    // Individual User Page html code here
    return (
        <div className="content">
            <div className="admin-nav">
                <button><a className="white-text" href="/admin"> Back to Dashboard </a></button>
                {user.enabled && (
                    <button className="banButton white-text" onClick={() => banUser(user.userId)}> Ban User </button>
                )}
            </div>
            <div className="userInfoSection">
                <h1 className="title black-text"> {user.username}'s profile </h1>
                <h2 className="status black-text"> Account Status: {user.enabled ? "Enabled" : "Disabled"} </h2>
            </div>
            <div className="reviews-container">
                <h2 className="title black-text"> Reviews  </h2>
                {reviews.length === 0 ? (
                    <div className="no-reviews black-text">No reviews found.</div>
                ) : (
                    reviews.map((review, idx) => (
                        // this changes color if flagged or removed
                        <div className={`reviews-box ${review.flagged || review.removed ? 'flagged-review' : ''}`} key={review.id ?? idx}>
                            <div className="review-item">
                                <div className="topBox">
                                    <div className="titles">
                                        <h2 className="black-text"> {review.game}</h2>
                                        <h3 className="black-text"> {review.reviewDate[8] + review.reviewDate[9] + '/' + review.reviewDate[5] + review.reviewDate[6] + '/' +  review.reviewDate[0] + review.reviewDate[1] + review.reviewDate[2] + review.reviewDate[3] } </h3>
                                    </div>
                                    <div className="statusBox">
                                        <h3 className="black-text"> {review.removed ? "Hidden from Users" : "Active"} </h3>
                                        <h3 className="black-text"> {review.flagged ? "Flagged" : ""} </h3>

                                    </div>
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
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}