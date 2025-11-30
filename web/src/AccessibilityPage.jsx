import React, { useEffect, useState } from 'react';
import Navbar from './NavBar';
import './style.css';

function AccessibilityPage() {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        concernType: '',
        message: ''
    });
    const [submitStatus, setSubmitStatus] = useState(''); // 'success', 'error', or ''
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        // Scroll animation observer
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('fade-in-visible');
                }
            });
        }, observerOptions);

        const cards = document.querySelectorAll('.fade-in-scroll');
        cards.forEach(card => observer.observe(card));

        return () => {
            cards.forEach(card => observer.unobserve(card));
        };
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setSubmitStatus('');

        try {
            const response = await fetch('http://localhost:8081/api/accessibility/feedback', {                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                setSubmitStatus('success');
                setFormData({ name: '', email: '', concernType: '', message: '' });
            } else {
                setSubmitStatus('error');
            }
        } catch (error) {
            console.error('Error submitting feedback:', error);
            setSubmitStatus('error');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="app">
            {/*<Navbar />*/}

            <main className="accessibility-page-simple">
                <div className="accessibility-hero">
                    <h1>Platform Accessibility Features</h1>
                    <p>This platform is designed with accessibility at its core. Here's how we support different needs:</p>
                </div>

                <div className="accessibility-cards-list">
                    {/* Hearing Impairments */}
                    <div className="accessibility-card-horizontal fade-in-scroll">
                        <div className="icon-box">
                            <span className="card-emoji">👂</span>
                        </div>
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Hearing</span> Impairments</h3>
                            <ul className="feature-checklist">
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>All audio content has visual alternatives</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>No audio-only interactions required</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Text-based communication in community features</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    {/* Vision Impairments */}
                    <div className="accessibility-card-horizontal fade-in-scroll">
                        <div className="icon-box">
                            <span className="card-emoji">👁️</span>
                        </div>
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Vision</span> Impairments</h3>
                            <ul className="feature-checklist">
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Full screen reader support</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Greyscale interface; colour only in images and icons where necessary</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>High contrast mode</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    {/* Dexterity Impairments */}
                    <div className="accessibility-card-horizontal fade-in-scroll">
                        <div className="icon-box">
                            <span className="card-emoji">✋</span>
                        </div>
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Dexterity</span> Impairments</h3>
                            <ul className="feature-checklist">
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Full voice command support</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>No time-sensitive interactions</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Large clickable targets</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    {/* Cognitive Impairments */}
                    <div className="accessibility-card-horizontal fade-in-scroll">
                        <div className="icon-box">
                            <span className="card-emoji">🧠</span>
                        </div>
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Cognitive</span> Impairments</h3>
                            <ul className="feature-checklist">
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Clear, simple language throughout</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Consistent navigation and layout</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>No flashing or distracting content</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    {/* Speech Impairments */}
                    <div className="accessibility-card-horizontal fade-in-scroll">
                        <div className="icon-box">
                            <span className="card-emoji">🔊</span>
                        </div>
                        <div className="card-content-horizontal">
                            <h3>For Users with <span className="category-name">Speech</span> Impairments</h3>
                            <ul className="feature-checklist">
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>No speech input required</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Text-based alternatives for all interactions</span>
                                </li>
                                <li>
                                    <span className="checkmark">✓</span>
                                    <span>Voice commands are optional</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                {/* Accessibility Feedback Form */}
                <section className="accessibility-contact">
                    <h2>Have Accessibility Concerns?</h2>
                    <p>We're committed to making this platform accessible to everyone. Let us know how we can improve.</p>

                    {submitStatus === 'success' && (
                        <div className="form-message success-message">
                            ✓ Thank you! Your feedback has been sent to our team.
                        </div>
                    )}

                    {submitStatus === 'error' && (
                        <div className="form-message error-message">
                            ✗ Sorry, something went wrong. Please try again later.
                        </div>
                    )}

                    <form className="accessibility-form" onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                placeholder="Your Name"
                                value={formData.name}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                placeholder="your.email@example.com"
                                value={formData.email}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="concernType">Concern Type</label>
                            <select
                                id="concernType"
                                name="concernType"
                                value={formData.concernType}
                                onChange={handleInputChange}
                                required
                            >
                                <option value="">Select Concern Type</option>
                                <option value="Vision">Vision</option>
                                <option value="Hearing">Hearing</option>
                                <option value="Motor">Motor/Dexterity</option>
                                <option value="Cognitive">Cognitive</option>
                                <option value="Speech">Speech</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>

                        <div className="form-group">
                            <label htmlFor="message">Message</label>
                            <textarea
                                id="message"
                                name="message"
                                placeholder="Describe your concern or suggestion..."
                                rows="5"
                                value={formData.message}
                                onChange={handleInputChange}
                                required
                            ></textarea>
                        </div>

                        <button type="submit" className="submit-btn" disabled={isSubmitting}>
                            {isSubmitting ? 'Sending...' : 'Submit Feedback'}
                        </button>
                    </form>
                </section>
            </main>

            <footer className="page-footer">
                <p>Making Games Accessible for Every Player</p>
                <p>
                    Platform designed with{' '}
                    <a
                        href="https://accessibility-manual.dwp.gov.uk/accessibility-law/web-content-accessibility-guidelines"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        WCAG 2.1 Level AA
                    </a>{' '}
                    compliance in mind
                </p>
            </footer>
        </div>
    );
}

export default AccessibilityPage;