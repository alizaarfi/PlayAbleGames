import React, { useState, useEffect } from 'react';
import './community.css';
import ConfirmModal from "./ConfirmModal.jsx";


function CommunityPage() {
    const [discussions, setDiscussions] = useState([]);
    const [blogs, setBlogs] = useState([]);
    const [faqs, setFaqs] = useState([]);
    const [expandedFaq, setExpandedFaq] = useState(null);
    const [selectedDiscussion, setSelectedDiscussion] = useState(null);
    const [replies, setReplies] = useState([]);
    const [showLoginModal, setShowLoginModal] = useState(false);
    const [showErrorModal, setShowErrorModal] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    // relevant new discussions
    const [showNewDiscussionForm, setShowNewDiscussionForm] = useState(false);
    const [newDiscussionTitle, setNewDiscussionTitle] = useState('');
    const [newDiscussionText, setNewDiscussionText] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    // this is for new replies
    const [newReplyText, setNewReplyText] = useState('');
    const [isSubmittingReply, setIsSubmittingReply] = useState(false);

    // fetching each from backend
    useEffect(() => {
        fetchDiscussions();
        fetchBlogs();
        fetchFaqs();
    }, []);

    const fetchDiscussions = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/discussions');
            const data = await response.json();
            setDiscussions(data);
        } catch (error) {
            console.error('Error fetching discussions:', error);
        }
    };

    const fetchBlogs = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/blogs');
            const data = await response.json();
            setBlogs(data);
        } catch (error) {
            console.error('Error fetching blogs:', error);
        }
    };

    const fetchFaqs = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/faq');
            const data = await response.json();
            setFaqs(data);
        } catch (error) {
            console.error('Error fetching FAQs:', error);
        }
    };

    const fetchReplies = async (discussionId) => {
        try {
            const response = await fetch(`http://localhost:8081/api/discussions/${discussionId}/replies`);
            const data = await response.json();
            setReplies(data);
        } catch (error) {
            console.error('Error fetching replies:', error);
        }
    };

    const handleDiscussionClick = async (discussion) => {
        setSelectedDiscussion(discussion);
        await fetchReplies(discussion.discussionID);
    };

    const handleCreateDiscussion = async () => {
        if (!newDiscussionTitle.trim() || !newDiscussionText.trim()) {
            setErrorMessage('Please fill in both title and text');
            setShowErrorModal(true);
            return;
        }

        setIsSubmitting(true);
        try {
            // send
            const token = localStorage.getItem('token');
            const response = await fetch('http://localhost:8081/api/discussions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // ADD THIS LINE
                },
                body: JSON.stringify({
                    discussionTitle: newDiscussionTitle,
                    discussionText: newDiscussionText
                })
            });

            if (response.ok) {
                // Reset form and close
                setNewDiscussionTitle('');
                setNewDiscussionText('');
                setShowNewDiscussionForm(false);
                // Refresh discussions
                await fetchDiscussions();
            } else if (response.status === 401) {
                setShowLoginModal(true);
            } else {
                setErrorMessage('Failed to create discussion. Please try again.');
                setShowErrorModal(true);
                console.log("Failed, try again");
            }
        } catch (error) {
            console.error('Error creating discussion:', error);
            setErrorMessage('An error occurred. Please try again.');
            setShowErrorModal(true);
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleAddReply = async () => {
        if (!newReplyText.trim()) {
            setErrorMessage('Please enter a reply');
            setShowErrorModal(true);
            return;
        }

        setIsSubmittingReply(true);
        try {
            const token = localStorage.getItem('token'); // ADD THIS

            const response = await fetch(`http://localhost:8081/api/discussions/${selectedDiscussion.discussionID}/replies`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // ADD THIS
                },
                body: JSON.stringify({
                    replyText: newReplyText
                })
            });

            if (response.ok) {
                setNewReplyText('');
                await fetchReplies(selectedDiscussion.discussionID);
            } else if (response.status === 401) {
                setShowLoginModal(true);
            } else {
                setErrorMessage('Failed to add reply. Please try again.');
                setShowErrorModal(true);
                console.log("Failed, try again");
            }
        } catch (error) {
            console.error('Error adding reply:', error);
            setErrorMessage('An error occurred. Please try again.');
            setShowErrorModal(true);
        } finally {
            setIsSubmittingReply(false);
        }
    };

    const toggleFaq = (faqId) => {
        setExpandedFaq(expandedFaq === faqId ? null : faqId);
    };

    const scrollToId = (id) => {
        const element = document.getElementById(id);
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    };

    return (
        <div className="app">
            <br></br>
            <main className="community-page-simple">
                <div className="community-hero">
                    <h1>Community</h1>
                    <p>Welcome to our community: browse discussions, blogs and FAQs</p>
                </div>

                <nav className="jump-nav">
                    <span className="jump-label2">Jump to:</span>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("discussions")}>
                        Discussions
                    </button>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("blogs")}>
                        Blogs
                    </button>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("faq")}>
                        FAQ
                    </button>
                </nav>

                {/* Discussions Section */}
                <section id="discussions" className="community-section">
                    <div className="section-header">
                        <h2>Discussions</h2>
                        <button
                            className="btn-primary"
                            onClick={() => setShowNewDiscussionForm(!showNewDiscussionForm)}
                        >
                            {showNewDiscussionForm ? 'Cancel' : 'Start New Discussion'}
                        </button>
                    </div>

                    {/* New Discussion Form */}
                    {showNewDiscussionForm && (
                        <div className="new-discussion-form">
                            <h3>Start a New Discussion</h3>
                            <div className="form-group">
                                <label htmlFor="discussion-title">Title</label>
                                <input
                                    id="discussion-title"
                                    type="text"
                                    className="form-input"
                                    placeholder="Enter discussion title"
                                    value={newDiscussionTitle}
                                    onChange={(e) => setNewDiscussionTitle(e.target.value)}
                                    disabled={isSubmitting}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="discussion-text">Discussion</label>
                                <textarea
                                    id="discussion-text"
                                    className="form-textarea"
                                    placeholder="What would you like to discuss?"
                                    rows="6"
                                    value={newDiscussionText}
                                    onChange={(e) => setNewDiscussionText(e.target.value)}
                                    disabled={isSubmitting}
                                />
                            </div>
                            <div className="form-actions">
                                <button
                                    className="btn-primary"
                                    onClick={handleCreateDiscussion}
                                    disabled={isSubmitting}
                                >
                                    {isSubmitting ? 'Posting...' : 'Post Discussion'}
                                </button>
                                <button
                                    className="btn-secondary"
                                    onClick={() => {
                                        setShowNewDiscussionForm(false);
                                        setNewDiscussionTitle('');
                                        setNewDiscussionText('');
                                    }}
                                    disabled={isSubmitting}
                                >
                                    Cancel
                                </button>
                            </div>
                        </div>
                    )}

                    <div className="discussions-container">
                        {/* Discussion List */}
                        <div className="discussions-list">
                            {discussions.length === 0 ? (
                                <p className="no-content">No discussions yet. Be the first to start one!</p>
                            ) : (
                                discussions.map(discussion => (
                                    <div
                                        key={discussion.discussionID}
                                        className={`discussion-card ${selectedDiscussion?.discussionID === discussion.discussionID ? 'active' : ''}`}
                                        onClick={() => handleDiscussionClick(discussion)}
                                    >
                                        <h3>{discussion.discussionTitle}</h3>
                                        <p className="discussion-meta">
                                            <span className="author">By {discussion.username || 'Anonymous'}</span>
                                            <span className="date">
                                                {new Date(discussion.dateCreated).toLocaleDateString()}
                                            </span>
                                        </p>
                                        <p className="discussion-preview">
                                            {discussion.discussionText.substring(0, 150)}
                                            {discussion.discussionText.length > 150 ? '...' : ''}
                                        </p>
                                    </div>
                                ))
                            )}
                        </div>

                        {/* Discussion Detail */}
                        {selectedDiscussion && (
                            <div className="discussion-detail">
                                <button
                                    className="btn-back"
                                    onClick={() => setSelectedDiscussion(null)}
                                >
                                    ← Back to Discussions
                                </button>

                                <div className="discussion-full">
                                    <h2>{selectedDiscussion.discussionTitle}</h2>
                                    <p className="discussion-meta">
                                        <span className="author">By {selectedDiscussion.username || 'Anonymous'}</span>
                                        <span className="date">
                                            {new Date(selectedDiscussion.dateCreated).toLocaleDateString()}
                                        </span>
                                    </p>
                                    <p className="discussion-text">{selectedDiscussion.discussionText}</p>
                                </div>

                                <div className="replies-section">
                                    <h3>Replies ({replies.length})</h3>

                                    {replies.length === 0 ? (
                                        <p className="no-content">No replies yet. Be the first to reply!</p>
                                    ) : (
                                        replies.map(reply => (
                                            <div key={reply.replyID} className="reply-card">
                                                <p className="reply-meta">
                                                    <span className="author">{reply.username || 'Anonymous'}</span>
                                                    <span className="date">
                                                        {new Date(reply.dateCreated).toLocaleDateString()}
                                                    </span>
                                                </p>
                                                <p className="reply-text">{reply.replyText}</p>
                                            </div>
                                        ))
                                    )}

                                    <div className="reply-form">
                                        <textarea
                                            className="form-textarea"
                                            placeholder="Write your reply..."
                                            rows="4"
                                            value={newReplyText}
                                            onChange={(e) => setNewReplyText(e.target.value)}
                                            disabled={isSubmittingReply}
                                        />
                                        <button
                                            className="btn-primary"
                                            onClick={handleAddReply}
                                            disabled={isSubmittingReply}
                                        >
                                            {isSubmittingReply ? 'Posting...' : 'Add Reply'}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </section>

                {/* Blogs Section */}
                <section id="blogs" className="community-section">
                    <div className="section-header">
                        <h2>Latest Blog Posts</h2>
                    </div>

                    <div className="blog-grid">
                        {blogs.length === 0 ? (
                            <p className="no-content">No blog posts available yet.</p>
                        ) : (
                            blogs.map(blog => (
                                <div key={blog.blogId} className="blog-card">
                                    <h3>{blog.blogTitle}</h3>
                                    <p className="blog-meta">
                    <span className="date">
                        {new Date(blog.dateCreated).toLocaleDateString()}
                    </span>
                                        <span className="read-time">• {blog.readTime} min read</span>
                                    </p>
                                    <p className="blog-description">{blog.blogDescription}</p>
                                    <a href={blog.linkToBlog} className="btn-secondary" target="_blank" rel="noopener noreferrer">
                                        Read More →
                                    </a>
                                </div>
                            ))
                        )}
                    </div>
                </section>

                {/* FAQ Section */}
                <section id="faq" className="community-section">
                    <div className="section-header">
                        <h2>Frequently Asked Questions</h2>
                    </div>

                    <div className="faq-list">
                        {faqs.length === 0 ? (
                            <p className="no-content">No FAQs available yet.</p>
                        ) : (
                            faqs.map(faq => (
                                <div key={faq.faqID} className="faq-item">
                                    <button
                                        className={`faq-question ${expandedFaq === faq.faqID ? 'active' : ''}`}
                                        onClick={() => toggleFaq(faq.faqID)}
                                        aria-expanded={expandedFaq === faq.faqID}
                                    >
                                        <span>{faq.question}</span>
                                        <span className="faq-icon">
                                            {expandedFaq === faq.faqID ? '−' : '+'}
                                        </span>
                                    </button>
                                    {expandedFaq === faq.faqID && (
                                        <div className="faq-answer">
                                            <p>{faq.answer}</p>
                                        </div>
                                    )}
                                </div>
                            ))
                        )}
                    </div>
                </section>
            </main>

            <ConfirmModal
                isOpen={showLoginModal}
                onClose={() => setShowLoginModal(false)}
                onConfirm={() => window.location.href = '/login'}
                title="Login Required"
                message="You must be logged in to participate in discussions. Would you like to log in now?"
                confirmText="Go to Login"
                cancelText="Cancel"
            />

            {/*<ConfirmModal*/}
            {/*    isOpen={showErrorModal}*/}
            {/*    onClose={() => setShowErrorModal(false)}*/}
            {/*    onConfirm={() => setShowErrorModal(false)}*/}
            {/*    title="Error"*/}
            {/*    message={errorMessage}*/}
            {/*    confirmText="OK"*/}
            {/*    cancelText="" // Empty string should hide cancel button*/}
            {/*/>*/}
        </div>
    )
}

export default CommunityPage;