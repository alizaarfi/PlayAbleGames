import React, { useEffect, useState } from "react";
import { useAuth } from "../hooks/AuthProvider.jsx";
import "../style.css";

const API_BASE = "http://localhost:8081/api";

export default function MyAccountPage() {
    const { token, logout } = useAuth();

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [profile, setProfile] = useState({
        username: "",
        email: "",
        userId: null,
        enabled: true,
        profilePictureURL: null
    });

    const [showEditProfile, setShowEditProfile] = useState(false);
    const [showChangePassword, setShowChangePassword] = useState(false);
    const [showDeleteAccount, setShowDeleteAccount]= useState(false);

    //for change password
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");
    const [passwordError, setPasswordError] = useState(null);
    const [showOldPassword, setShowOldPassword] = useState(false);
    const [showNewPassword, setShowNewPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    //for edit profile
    const [profileError, setProfileError] = useState(null);
    const [profileSuccess, setProfileSuccess] = useState(null);

    //for detele account
    const [deleteConfirmPassword, setDeleteConfirmPassword] = useState("");
    const [showDeletePassword, setShowDeletePassword] = useState(false);
    const [deleteError, setDeleteError] = useState(null);

    useEffect(() => {
        async function fetchProfile() {
            setLoading(true);
            setError(null);

            try {
                const res = await fetch(`${API_BASE}/user/profile`, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                if (!res.ok) {
                    throw new Error('Failed to fetch profile data');
                }

                const data = await res.json();
                setProfile(data);
            } catch (e) {
                setError(e.message);
            } finally {
                setLoading(false);
            }
        }

        if (token) {
            fetchProfile();
        }
    }, [token]);

    const handleSignOut = async () => {
        await logout();
        window.location.href = "/";
    };

    const validateProfileUpdate = () => {
        if (!profile.username || !profile.email) {
            return "Username and email are required.";
        }

        if (profile.username.length < 3) {
            return "Username must be at least 3 characters long.";
        }

        // Basic email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(profile.email)) {
            return "Please enter a valid email address.";
        }

        return null;
    };

    const handleSaveProfile = async () => {
        setProfileError(null);
        setProfileSuccess(null);

        const validation = validateProfileUpdate();

        if(validation){
            setProfileError(validation);
            return
        }
        try {
            const res = await fetch(`${API_BASE}/user/profile`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(profile)
            });

            if (!res.ok) {
                let errorMessage = "Failed to update profile";
                try {
                    const data = await res.json();
                    errorMessage = data.error || data.message || errorMessage;
                } catch (e) {
                    errorMessage= `Error: ${res.status} ${res.statusText}`;
                }
                setProfileError(errorMessage);
                return;

            }
            setProfileSuccess("Profile updated successfully!");
            setTimeout(() => {
                setProfileSuccess(null);
                setShowEditProfile(false);
            }, 2000);
        } catch (e) {
            console.error("Profile update error:", e);
            setProfileError("Network error: Unable to connect to server");
        }
    };

    const handleChangePassword = async () => {
        setPasswordError(null);

        const validation = validatePasswordChange();
        if (validation){
            setPasswordError(validation);
            return;
        }

        const confirm = window.confirm("Are you sure you want to change your password?");
        if (!confirm) return;

        try {
            const res = await fetch(`${API_BASE}/user/change-password`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({ oldPassword, newPassword })
            });

            if (!res.ok) {
                // Try to get error message from response
                let errorMessage = "Failed to change password.";
                try {
                    const data = await res.json();
                    errorMessage = data.error || data.message || errorMessage;
                } catch (e) {
                    // If JSON parsing fails, use status text
                    errorMessage = `Error: ${res.status} ${res.statusText}`;
                }
                setPasswordError(errorMessage);
                return;
            }

            alert("Password updated successfully");
            setOldPassword("");
            setNewPassword("");
            setConfirmNewPassword("");
            setPasswordError(null);
            setShowChangePassword(false);
        } catch (e) {
            console.error("Password change error:", e);
            setPasswordError("Network error: Unable to connect to server");
        }
    };

    const validatePasswordChange = () => {
        if (!oldPassword || !newPassword || !confirmNewPassword) {
            return "Please fill all the fields.";
        }


        // New password length
        if (newPassword.length < 8) {
            return "New password must be at least 8 characters.";
        }

        // Must include uppercase
        if (!/[A-Z]/.test(newPassword)) {
            return "New password must contain at least one uppercase letter.";
        }

        // Must include number
        if (!/[0-9]/.test(newPassword)) {
            return "New password must contain at least one number.";
        }

        // Must match
        if (newPassword !== confirmNewPassword) {
            return "New passwords do not match.";
        }

        // Cannot reuse same password
        if (oldPassword === newPassword) {
            return "New password cannot be the same as your current password.";
        }

        return null;
    };

    const handleDeleteAccount = async () => {
        setDeleteError(null);

        if (!deleteConfirmPassword) {
            setDeleteError("Please enter your password to confirm account deletion.");
            return;
        }

        const confirmText = window.prompt(
            'This action cannot be undone. Type "DELETE" to confirm account deletion:'
        );

        if (confirmText !== "DELETE") {
            setDeleteError("Account deletion cancelled or incorrect confirmation.");
            return;
        }

        try {
            const res = await fetch(`${API_BASE}/user/delete-account`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({ password: deleteConfirmPassword })
            });

            if (!res.ok) {
                let errorMessage = "Failed to delete account.";
                try {
                    const data = await res.json();
                    errorMessage = data.error || data.message || errorMessage;
                } catch (e) {
                    errorMessage = `Error: ${res.status} ${res.statusText}`;
                }
                setDeleteError(errorMessage);
                return;
            }

            alert("Account deleted successfully. You will be logged out.");
            await logout();
            window.location.href = "/";
        } catch (e) {
            console.error("Delete account error:", e);
            setDeleteError("Network error: Unable to connect to server");
        }
    };

    if (loading) {
        return (
            <div className="app">
                <main className="container">
                    <div className="loading-container">
                        <p>Loading your account...</p>
                    </div>
                </main>
            </div>
        );
    }

    if (error) {
        return (
            <div className="app">
                <main className="container">
                    <div className="error-container">
                        <p>Error: {error}</p>
                    </div>
                </main>
            </div>
        );
    }

    return (
        <div className="app">
            <main className="container">
                <section className="hero">
                    <h2>My Account</h2>
                    <p>Manage your profile and account settings</p>
                </section>

                <div className="account-layout">

                    <div className="account-card">
                        <div className="account-profile-header">
                            <div className="profile-avatar-large">
                                {profile.profilePictureURL ? (
                                    <img src={profile.profilePictureURL} alt="Profile" />
                                ) : (
                                    <div className="avatar-placeholder-large">
                                        {profile.username?.charAt(0).toUpperCase()}
                                    </div>
                                )}
                            </div>
                            <div className="profile-header-info">
                                <h3>{profile.username}</h3>
                                <p className="user-email">{profile.email}</p>
                                <span className={`status-badge ${profile.enabled ? 'active' : 'inactive'}`}>
                                    {profile.enabled ? '● Active' : '● Inactive'}
                                </span>
                            </div>
                        </div>

                        <div className="account-details-section">
                            <h4>Account Details</h4>
                            <div className="detail-row">
                                <span className="detail-label">Username</span>
                                <span className="detail-value">{profile.username}</span>
                            </div>
                            <div className="detail-row">
                                <span className="detail-label">Email</span>
                                <span className="detail-value">{profile.email}</span>
                            </div>
                            <div className="detail-row">
                                <span className="detail-label">User ID</span>
                                <span className="detail-value">#{profile.userId}</span>
                            </div>
                            <div className="detail-row">
                                <span className="detail-label">Account Status</span>
                                <span className="detail-value">
                                    <span className={`status-badge ${profile.enabled ? 'active' : 'inactive'}`}>
                                        {profile.enabled ? 'Active' : 'Inactive'}
                                    </span>
                                </span>
                            </div>
                        </div>

                        <div className="account-actions-section">
                            <h4>Account Actions</h4>
                            <div className="action-buttons">
                                <button
                                    className="action-button edit-profile"
                                    onClick={() => {
                                        setShowEditProfile(!showEditProfile);
                                        setShowChangePassword(false);
                                        setShowDeleteAccount(false);
                                        setProfileError(null);
                                        setProfileSuccess(null);
                                    }}
                                >
                                    Edit Profile
                                </button>

                                {showEditProfile && (
                                    <div className="collapsible-section">
                                        <h4>Edit Profile</h4>

                                        {profileError && (
                                            <div className="error-message" style={{
                                                color: '#ff4444',
                                                backgroundColor: '#ffe6e6',
                                                padding: '10px',
                                                borderRadius: '5px',
                                                marginBottom: '15px',
                                                border: '1px solid #ff4444'
                                            }}>
                                                {profileError}
                                            </div>
                                        )}

                                        {profileSuccess && (
                                            <div className="success-message" style={{
                                                color: '#00aa00',
                                                backgroundColor: '#e6ffe6',
                                                padding: '10px',
                                                borderRadius: '5px',
                                                marginBottom: '15px',
                                                border: '1px solid #00aa00'
                                            }}>
                                                {profileSuccess}
                                            </div>
                                        )}

                                        <label>Username</label>
                                        <input
                                            type="text"
                                            value={profile.username}
                                            onChange={(e) =>
                                                setProfile({ ...profile, username: e.target.value })
                                            }
                                        />

                                        <label>Email</label>
                                        <input
                                            type="email"
                                            value={profile.email}
                                            onChange={(e) =>
                                                setProfile({ ...profile, email: e.target.value })
                                            }
                                        />

                                        <button className="save-button" onClick={handleSaveProfile}>
                                            Save Changes
                                        </button>
                                    </div>
                                )}

                                <button
                                    className="action-button change-password"
                                    onClick={() => {
                                        setShowChangePassword(!showChangePassword);
                                        setShowEditProfile(false);
                                    }}
                                >
                                    Change Password
                                </button>
                                {showChangePassword && (
                                    <div className="collapsible-section">
                                        <h4>Change Password</h4>
                                        {passwordError && (
                                            <div className="error-message" style={{
                                                color: '#ff4444',
                                                backgroundColor: '#ffe6e6',
                                                padding: '10px',
                                                borderRadius: '5px',
                                                marginBottom: '15px',
                                                border: '1px solid #ff4444'
                                            }}>
                                                {passwordError}
                                            </div>
                                        )}
                                        <label>Old Password</label>
                                        <div className="password-input">
                                            <input
                                                type={showOldPassword ? "text" : "password"}
                                                value={oldPassword}
                                                onChange={(e) => setOldPassword(e.target.value)}
                                            />
                                            <button
                                                type="button"
                                                className="show-hide-btn"
                                                onClick={() => setShowOldPassword(!showOldPassword)}
                                            >
                                                {showOldPassword ? "Hide" : "Show"}
                                            </button>
                                        </div>

                                        <label>New Password</label>
                                        <div className="password-input">
                                            <input
                                                type={showNewPassword ? "text" : "password"}
                                                value={newPassword}
                                                onChange={(e) => setNewPassword(e.target.value)}
                                            />
                                            <button
                                                type="button"
                                                className="show-hide-btn"
                                                onClick={() => setShowNewPassword(!showNewPassword)}
                                            >
                                                {showNewPassword ? "Hide" : "Show"}
                                            </button>
                                        </div>

                                        <label>Confirm New Password</label>
                                        <div className="password-input">
                                            <input
                                                type={showConfirmPassword ? "text" : "password"}
                                                value={confirmNewPassword}
                                                onChange={(e) => setConfirmNewPassword(e.target.value)}
                                            />
                                            <button
                                                type="button"
                                                className="show-hide-btn"
                                                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                                            >
                                                {showConfirmPassword ? "Hide" : "Show"}
                                            </button>
                                        </div>

                                        <button className="save-button" onClick={handleChangePassword}>
                                            Update Password
                                        </button>
                                    </div>
                                )}


                                <button
                                    className="action-button sign-out"
                                    onClick={handleSignOut}
                                >
                                    Sign Out
                                </button>

                                <button
                                    className="action-button delete-account"
                                    style={{
                                        backgroundColor: '#ff4444',
                                        color: 'white'
                                    }}
                                    onClick={() => {
                                        setShowDeleteAccount(!showDeleteAccount);
                                        setShowEditProfile(false);
                                        setShowChangePassword(false);
                                        setDeleteError(null);
                                        setDeleteConfirmPassword("");
                                    }}
                                >
                                    Delete Account
                                </button>

                                {showDeleteAccount && (
                                    <div className="collapsible-section" style={{
                                        borderLeft: '3px solid #ff4444'
                                    }}>
                                        <h4 style={{ color: '#ff4444' }}>⚠️ Delete Account</h4>

                                        <p style={{
                                            backgroundColor: '#fff3cd',
                                            padding: '10px',
                                            borderRadius: '5px',
                                            border: '1px solid #ffc107',
                                            marginBottom: '15px'
                                        }}>
                                            <strong>Warning:</strong> This action is permanent and cannot be undone.
                                            All your data, favorites, and reviews will be permanently deleted.
                                        </p>

                                        {deleteError && (
                                            <div className="error-message" style={{
                                                color: '#ff4444',
                                                backgroundColor: '#ffe6e6',
                                                padding: '10px',
                                                borderRadius: '5px',
                                                marginBottom: '15px',
                                                border: '1px solid #ff4444'
                                            }}>
                                                {deleteError}
                                            </div>
                                        )}

                                        <label>Enter your password to confirm</label>
                                        <div className="password-input">
                                            <input
                                                type={showDeletePassword ? "text" : "password"}
                                                value={deleteConfirmPassword}
                                                onChange={(e) => setDeleteConfirmPassword(e.target.value)}
                                                placeholder="Enter your current password"
                                            />
                                            <button
                                                type="button"
                                                className="show-hide-btn"
                                                onClick={() => setShowDeletePassword(!showDeletePassword)}
                                            >
                                                {showDeletePassword ? "Hide" : "Show"}
                                            </button>
                                        </div>

                                        <button
                                            className="save-button"
                                            style={{
                                                backgroundColor: '#ff4444',
                                                border: 'none'
                                            }}
                                            onClick={handleDeleteAccount}
                                        >
                                            Permanently Delete My Account
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>


                    <aside className="account-sidebar">
                        <div className="sidebar-card">
                            <h4>Quick Stats</h4>
                            <div className="stat-item">
                                <span className="stat-label">Reviews Written</span>
                                <span className="stat-value">0</span>
                            </div>
                            <div className="stat-item">
                                <span className="stat-label">Games Favorited</span>
                                <span className="stat-value">0</span>
                            </div>
                            <div className="stat-item">
                                <span className="stat-label">Member Since</span>
                                <span className="stat-value">2025</span>
                            </div>
                        </div>

                        <div className="sidebar-card">
                            <h4>Quick Links</h4>
                            <a href="/" className="quick-link">
                                <svg viewBox="0 0 24 24" fill="currentColor" className="link-icon">
                                    <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/>
                                </svg>
                                Browse Games
                            </a>
                            <a href="/favorites" className="quick-link">
                                <svg viewBox="0 0 24 24" fill="currentColor" className="link-icon">
                                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                                </svg>
                                My Favorites
                            </a>
                        </div>
                    </aside>
                </div>

                <footer className="page-footer">
                    <p>Making Games Accessible for Every Player</p>
                    <p>
                        Platform designed with{" "}
                        <a
                            href="https://accessibility-manual.dwp.gov.uk/accessibility-law/web-content-accessibility-guidelines"
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            WCAG 2.1 Level AA
                        </a>{" "}
                        compliance in mind
                    </p>
                </footer>
            </main>
        </div>
    );
}