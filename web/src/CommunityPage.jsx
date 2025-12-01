import React, { useState, useEffect } from 'react';

// Start discussions, browse discussions
// FAQs
// Browse blogs, links to other pages
function CommunityPage() {

    return (
        <div className="app">
            <br></br>
            <main className="community-page-simple">
                <div className="community-hero">
                    <h1>Community</h1>
                    <p>Welcome to our community: browse discussions, blogs and FAQs</p>
                </div>

                {/* jump navigation from game page code */}
                <nav className="jump-nav">
                    <span className="jump-label2">Jump to:</span>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("accessibility")}>
                        Accessibility Features
                    </button>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("reviews")}>
                            Reviews
                    </button>
                    <button className="jump-link2" type="button" onClick={() => scrollToId("gallery")}>
                        Gallery
                    </button>
                </nav>
            </main>
        </div>
    )
}
export default CommunityPage;