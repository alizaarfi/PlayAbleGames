import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import CommunityPage from '../CommunityPage';

// Mock ConfirmModal component
jest.mock('../ConfirmModal', () => {
    return function ConfirmModal({ isOpen, title, message, onConfirm, onClose, confirmText, cancelText }) {
        if (!isOpen) return null;
        return (
            <div data-testid="confirm-modal">
                <h2>{title}</h2>
                <p>{message}</p>
                <button onClick={onConfirm}>{confirmText || 'Confirm'}</button>
                {cancelText && <button onClick={onClose}>{cancelText}</button>}
            </div>
        );
    };
});

// Mock CSS import after
jest.mock('../community.css', () => ({}));

beforeEach(() => {
    jest.clearAllMocks();
    localStorage.clear();
    global.fetch = jest.fn();
});

describe('CommunityPage', () => {
    test('Renders community page with main heading first', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('Community')).toBeInTheDocument();
        });
    });

    test('Renders welcome message for it', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText(/Welcome to our community/i)).toBeInTheDocument();
        });
    });

    test('Renders section headers', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByRole('heading', { name: 'Discussions' })).toBeInTheDocument();
            expect(screen.getByRole('heading', { name: 'Latest Blog Posts' })).toBeInTheDocument();
            expect(screen.getByRole('heading', { name: 'Frequently Asked Questions' })).toBeInTheDocument();
        });
    });

    test('Fetches and displays discussions', async () => {
        fetch.mockImplementation((url) => {
            if (url.includes('/api/discussions')) {
                return Promise.resolve({
                    ok: true,
                    json: async () => [
                        {
                            discussionID: 1,
                            discussionTitle: 'Test Discussion',
                            discussionText: 'This is a test discussion text',
                            username: 'TestUser',
                            dateCreated: '2024-01-01T00:00:00.000Z'
                        }
                    ]
                });
            }
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('Test Discussion')).toBeInTheDocument();
        });
    });

    test('Fetches and displays blogs', async () => {
        fetch.mockImplementation((url) => {
            if (url.includes('/api/blogs')) {
                return Promise.resolve({
                    ok: true,
                    json: async () => [
                        {
                            blogId: 1,
                            blogTitle: 'Test Blog',
                            blogDescription: 'Test blog description',
                            dateCreated: '2024-01-01T00:00:00.000Z',
                            readTime: 5,
                            linkToBlog: 'https://example.com/blog'
                        }
                    ]
                });
            }
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('Test Blog')).toBeInTheDocument();
        });
    });

    test('Fetches and displays FAQs', async () => {
        fetch.mockImplementation((url) => {
            if (url.includes('/api/faq')) {
                return Promise.resolve({
                    ok: true,
                    json: async () => [
                        {
                            faqID: 1,
                            question: 'Test Question?',
                            answer: 'Test Answer'
                        }
                    ]
                });
            }
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('Test Question?')).toBeInTheDocument();
        });
    });

    test('Shows new discussion form when button clicked', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByRole('button', { name: /start new discussion/i })).toBeInTheDocument();
        });

        const startButton = screen.getByRole('button', { name: /start new discussion/i });
        fireEvent.click(startButton);

        expect(screen.getByText('Start a New Discussion')).toBeInTheDocument();
    });

    test('Can type in discussion title field', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByRole('button', { name: /start new discussion/i })).toBeInTheDocument();
        });

        const startButton = screen.getByRole('button', { name: /start new discussion/i });
        fireEvent.click(startButton);

        const titleInput = screen.getByPlaceholderText('Enter discussion title');
        fireEvent.change(titleInput, { target: { value: 'My New Discussion' } });

        expect(titleInput.value).toBe('My New Discussion');
    });

    test('Toggles FAQ answer when question is clicked', async () => {
        fetch.mockImplementation((url) => {
            if (url.includes('/api/faq')) {
                return Promise.resolve({
                    ok: true,
                    json: async () => [
                        {
                            faqID: 1,
                            question: 'Test Question?',
                            answer: 'Test Answer'
                        }
                    ]
                });
            }
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('Test Question?')).toBeInTheDocument();
        });

        const faqButton = screen.getByText('Test Question?').closest('button');

        expect(screen.queryByText('Test Answer')).not.toBeInTheDocument();

        fireEvent.click(faqButton);

        await waitFor(() => {
            expect(screen.getByText('Test Answer')).toBeInTheDocument();
        });
    });

    test('Shows empty state when no discussions available', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('No discussions yet. Be the first to start one!')).toBeInTheDocument();
        });
    });

    test('Shows empty state when no blogs available', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('No blog posts available yet.')).toBeInTheDocument();
        });
    });

    test('Shows empty state when no FAQs available', async () => {
        fetch.mockImplementation((url) => {
            return Promise.resolve({
                ok: true,
                json: async () => []
            });
        });

        render(<CommunityPage />);

        await waitFor(() => {
            expect(screen.getByText('No FAQs available yet.')).toBeInTheDocument();
        });
    });
});