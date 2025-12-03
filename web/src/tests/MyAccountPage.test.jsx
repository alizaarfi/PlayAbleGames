import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter } from 'react-router-dom';
import MyAccountPage from './MyAccountPage';
import { AuthProvider } from '../hooks/AuthProvider';

// Mock the useAuth hook
const mockLogout = jest.fn();
const mockToken = 'mock-jwt-token';

jest.mock('../hooks/AuthProvider', () => ({
    ...jest.requireActual('../hooks/AuthProvider'),
    useAuth: () => ({
        token: mockToken,
        logout: mockLogout,
    }),
}));

// Mock fetch globally
global.fetch = jest.fn();

// Helper function to render component with providers
const renderWithProviders = (component) => {
    return render(
        <BrowserRouter>
            <AuthProvider>
                {component}
            </AuthProvider>
        </BrowserRouter>
    );
};

// Mock profile data
const mockProfileData = {
    username: 'testuser',
    email: 'test@example.com',
    userId: 1,
    enabled: true,
    profilePictureURL: null,
};

describe('MyAccountPage', () => {
    beforeEach(() => {
        // Reset mocks before each test
        jest.clearAllMocks();

        // Default successful profile fetch
        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => mockProfileData,
        });
    });

    afterEach(() => {
        jest.restoreAllMocks();
    });

    // ==========================================
    // LOADING AND ERROR STATES
    // ==========================================

    test('displays loading state initially', () => {
        renderWithProviders(<MyAccountPage />);
        expect(screen.getByText('Loading your account...')).toBeInTheDocument();
    });

    test('displays error message when profile fetch fails', async () => {
        global.fetch.mockRejectedValueOnce(new Error('Network error'));

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText(/Error: Network error/i)).toBeInTheDocument();
        });
    });

    test('fetches profile data on mount', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(global.fetch).toHaveBeenCalledWith(
                'http://localhost:8081/api/user/profile',
                expect.objectContaining({
                    headers: {
                        'Authorization': `Bearer ${mockToken}`,
                        'Content-Type': 'application/json',
                    },
                })
            );
        });
    });

    // ==========================================
    // PROFILE DISPLAY
    // ==========================================

    test('displays user profile information', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
            expect(screen.getByText('test@example.com')).toBeInTheDocument();
            expect(screen.getByText('#1')).toBeInTheDocument();
            expect(screen.getByText('Active')).toBeInTheDocument();
        });
    });

    test('displays avatar placeholder with first letter of username', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            const avatar = screen.getByText('T'); // First letter of 'testuser'
            expect(avatar).toBeInTheDocument();
        });
    });

    test('displays profile picture when URL is provided', async () => {
        const profileWithPicture = {
            ...mockProfileData,
            profilePictureURL: 'https://example.com/profile.jpg',
        };

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => profileWithPicture,
        });

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            const img = screen.getByAltText('Profile');
            expect(img).toHaveAttribute('src', 'https://example.com/profile.jpg');
        });
    });

    // ==========================================
    // EDIT PROFILE
    // ==========================================

    test('shows edit profile form when Edit Profile button is clicked', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        const editButton = screen.getByText('Edit Profile');
        fireEvent.click(editButton);

        expect(screen.getByLabelText('Username')).toBeInTheDocument();
        expect(screen.getByLabelText('Email')).toBeInTheDocument();
    });

    test('validates username length when saving profile', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Edit Profile'));

        const usernameInput = screen.getByLabelText('Username');
        fireEvent.change(usernameInput, { target: { value: 'ab' } }); // Too short

        fireEvent.click(screen.getByText('Save Changes'));

        await waitFor(() => {
            expect(screen.getByText('Username must be at least 3 characters long.')).toBeInTheDocument();
        });
    });

    test('validates email format when saving profile', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Edit Profile'));

        const emailInput = screen.getByLabelText('Email');
        fireEvent.change(emailInput, { target: { value: 'invalid-email' } });

        fireEvent.click(screen.getByText('Save Changes'));

        await waitFor(() => {
            expect(screen.getByText('Please enter a valid email address.')).toBeInTheDocument();
        });
    });

    test('successfully updates profile', async () => {
        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ message: 'Profile updated', usernameChanged: false }),
        });

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Edit Profile'));

        const usernameInput = screen.getByLabelText('Username');
        fireEvent.change(usernameInput, { target: { value: 'newusername' } });

        fireEvent.click(screen.getByText('Save Changes'));

        await waitFor(() => {
            expect(screen.getByText('Profile updated successfully!')).toBeInTheDocument();
        });
    });

    test('forces re-login when username is changed', async () => {
        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ message: 'Profile updated', usernameChanged: true }),
        });

        window.alert = jest.fn();
        delete window.location;
        window.location = { href: '' };

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Edit Profile'));

        const usernameInput = screen.getByLabelText('Username');
        fireEvent.change(usernameInput, { target: { value: 'differentuser' } });

        fireEvent.click(screen.getByText('Save Changes'));

        await waitFor(() => {
            expect(window.alert).toHaveBeenCalledWith(
                'Username updated successfully! Please log in again with your new username.'
            );
            expect(mockLogout).toHaveBeenCalled();
        });
    });

    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    test('shows change password form when Change Password button is clicked', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        expect(screen.getByLabelText('Old Password')).toBeInTheDocument();
        expect(screen.getByLabelText('New Password')).toBeInTheDocument();
        expect(screen.getByLabelText('Confirm New Password')).toBeInTheDocument();
    });

    test('validates all password fields are filled', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));
        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(screen.getByText('Please fill all the fields.')).toBeInTheDocument();
        });
    });

    test('validates new password length', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'OldPass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'Short1' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'Short1' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(screen.getByText('New password must be at least 8 characters.')).toBeInTheDocument();
        });
    });

    test('validates new password contains uppercase letter', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'OldPass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'newpass123' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'newpass123' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(
                screen.getByText('New password must contain at least one uppercase letter.')
            ).toBeInTheDocument();
        });
    });

    test('validates new password contains number', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'OldPass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'NewPassword' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'NewPassword' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(
                screen.getByText('New password must contain at least one number.')
            ).toBeInTheDocument();
        });
    });

    test('validates passwords match', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'OldPass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'NewPass123' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'DifferentPass123' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(screen.getByText('New passwords do not match.')).toBeInTheDocument();
        });
    });

    test('validates new password is different from old password', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'SamePass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'SamePass123' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'SamePass123' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(
                screen.getByText('New password cannot be the same as your current password.')
            ).toBeInTheDocument();
        });
    });

    test('successfully changes password', async () => {
        window.confirm = jest.fn(() => true);
        window.alert = jest.fn();

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ message: 'Password changed successfully' }),
        });

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        fireEvent.change(screen.getByLabelText('Old Password'), {
            target: { value: 'OldPass123' },
        });
        fireEvent.change(screen.getByLabelText('New Password'), {
            target: { value: 'NewPass123' },
        });
        fireEvent.change(screen.getByLabelText('Confirm New Password'), {
            target: { value: 'NewPass123' },
        });

        fireEvent.click(screen.getByText('Update Password'));

        await waitFor(() => {
            expect(window.alert).toHaveBeenCalledWith('Password updated successfully');
        });
    });

    test('toggles password visibility', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Change Password'));

        const oldPasswordInput = screen.getByLabelText('Old Password');
        expect(oldPasswordInput).toHaveAttribute('type', 'password');

        const showButtons = screen.getAllByText('Show');
        fireEvent.click(showButtons[0]); // Click first "Show" button

        expect(oldPasswordInput).toHaveAttribute('type', 'text');

        fireEvent.click(screen.getAllByText('Hide')[0]);
        expect(oldPasswordInput).toHaveAttribute('type', 'password');
    });

    // ==========================================
    // SIGN OUT
    // ==========================================

    test('signs out user when Sign Out button is clicked', async () => {
        delete window.location;
        window.location = { href: '' };

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Sign Out'));

        await waitFor(() => {
            expect(mockLogout).toHaveBeenCalled();
            expect(window.location.href).toBe('/');
        });
    });

    // ==========================================
    // DELETE ACCOUNT
    // ==========================================

    test('shows delete account form when Delete Account button is clicked', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Delete Account'));

        expect(screen.getByText('⚠️ Delete Account')).toBeInTheDocument();
        expect(screen.getByText(/This action is permanent/i)).toBeInTheDocument();
    });

    test('validates password is entered before deletion', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Delete Account'));
        fireEvent.click(screen.getByText('Permanently Delete My Account'));

        await waitFor(() => {
            expect(
                screen.getByText('Please enter your password to confirm account deletion.')
            ).toBeInTheDocument();
        });
    });

    test('requires DELETE confirmation text', async () => {
        window.prompt = jest.fn(() => 'WRONG');

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Delete Account'));

        const passwordInput = screen.getByPlaceholderText('Enter your current password');
        fireEvent.change(passwordInput, { target: { value: 'Password123' } });

        fireEvent.click(screen.getByText('Permanently Delete My Account'));

        await waitFor(() => {
            expect(
                screen.getByText('Account deletion cancelled or incorrect confirmation.')
            ).toBeInTheDocument();
        });
    });

    test('successfully deletes account', async () => {
        window.prompt = jest.fn(() => 'DELETE');
        window.alert = jest.fn();
        delete window.location;
        window.location = { href: '' };

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ message: 'Account deleted' }),
        });

        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Delete Account'));

        const passwordInput = screen.getByPlaceholderText('Enter your current password');
        fireEvent.change(passwordInput, { target: { value: 'Password123' } });

        fireEvent.click(screen.getByText('Permanently Delete My Account'));

        await waitFor(() => {
            expect(window.alert).toHaveBeenCalledWith(
                'Account deleted successfully. You will be logged out.'
            );
            expect(mockLogout).toHaveBeenCalled();
            expect(window.location.href).toBe('/');
        });
    });

    // ==========================================
    // UI INTERACTIONS
    // ==========================================

    test('closes edit profile when change password is opened', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('testuser')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Edit Profile'));
        expect(screen.getByLabelText('Username')).toBeInTheDocument();

        fireEvent.click(screen.getByText('Change Password'));
        expect(screen.queryByLabelText('Username')).not.toBeInTheDocument();
        expect(screen.getByLabelText('Old Password')).toBeInTheDocument();
    });

    test('displays quick stats sidebar', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('Quick Stats')).toBeInTheDocument();
            expect(screen.getByText('Reviews Written')).toBeInTheDocument();
            expect(screen.getByText('Games Favorited')).toBeInTheDocument();
            expect(screen.getByText('Member Since')).toBeInTheDocument();
        });
    });

    test('displays quick links sidebar', async () => {
        renderWithProviders(<MyAccountPage />);

        await waitFor(() => {
            expect(screen.getByText('Quick Links')).toBeInTheDocument();
            expect(screen.getByText('Browse Games')).toBeInTheDocument();
            expect(screen.getByText('My Favorites')).toBeInTheDocument();
        });
    });
});