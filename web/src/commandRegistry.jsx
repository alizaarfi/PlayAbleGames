
/**
 * Central registry for all voice commands
 * Each command type has an execute function that takes (target, dependencies)
 */

const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const commandRegistry = {

    // ============================================
    // NAVIGATION
    // ============================================
    NAVIGATE: {
        execute: async (target, { navigate }) => {
            const routes = {
                'accessibility': '/accessibility',
                'community': '/community',
                'home': '/',
                'settings': '/settings',
                'favourites': '/favourites',
                'login': '/login',
                'registration': '/register',
                // Game pages will be dynamic: /game/[gameName]
            };

            const route = routes[target?.toLowerCase()];

            if (route) {
                await delay(800); // Show "executing" state
                navigate(route);
            } else if (target) {
                // Assume it's a game name
                await delay(800);
                navigate(`/game/${target.toLowerCase().replace(/\s+/g, '-')}`);
            } else {
                throw new Error('No navigation target provided');
            }
        }
    },

    // ============================================
    // CLICKS (Profile dropdown, logout, etc)
    // ============================================
    CLICK: {
        execute: async (target, { setDropdownOpen, handleLogout }) => {
            await delay(500);

            if (target === 'profile' || target === 'profile dropdown') {
                setDropdownOpen?.(true);
            } else if (target === 'logout' || target === 'log out' || target === 'sign out') {
                handleLogout?.();
            } else {
                console.log('Click target:', target);
                // TODO: Implement generic click by element selector
            }
        }
    },

    // ============================================
    // SEARCH
    // ============================================
    SEARCH: {
        execute: async (target, { performSearch }) => {
            if (!target) {
                throw new Error('No search query provided');
            }

            await delay(500);
            performSearch?.(target);
        }
    },

    // ============================================
    // FILTER
    // ============================================
    FILTER: {
        execute: async (target, { applyFilter }) => {
            if (!target) {
                throw new Error('No filter category provided');
            }

            await delay(500);

            // Map voice input to filter values
            const filterMap = {
                'vision': 'vision',
                'hearing': 'hearing',
                'speech': 'speech',
                'cognitive': 'cognitive',
                'action': 'action',
                'adventure': 'adventure',
                'puzzle': 'puzzle',
                'strategy': 'strategy',
                'casual': 'casual'
            };

            const filterValue = filterMap[target.toLowerCase()];

            if (filterValue) {
                applyFilter?.(filterValue);
            } else {
                throw new Error(`Unknown filter: ${target}`);
            }
        }
    },

    // ============================================
    // SORT
    // ============================================
    SORT: {
        execute: async (target, { applySort }) => {
            if (!target) {
                throw new Error('No sort option provided');
            }

            await delay(500);

            // Map voice input to sort values
            const sortMap = {
                'popular': 'popularity',
                'most popular': 'popularity',
                'rating': 'rating',
                'most reviewed': 'most-reviewed',
                'least reviewed': 'least-reviewed',
                'a-z': 'a-z',
                'a z': 'a-z',
                'z-a': 'z-a',
                'z a': 'z-a'
            };

            const sortValue = sortMap[target.toLowerCase()];

            if (sortValue) {
                applySort?.(sortValue);
            } else {
                throw new Error(`Unknown sort: ${target}`);
            }
        }
    },

    // ============================================
    // FAVOURITE
    // ============================================
    FAVOURITE: {
        //edit togglefavourite
        execute: async (target, { toggleFavourite }) => {
            if (!target) {
                throw new Error('No game name provided');
            }

            await delay(500);

            toggleFavourite?.(target);
        }
    },

    // ============================================
    // CANCEL (Unfavourite, undo, etc)
    // ============================================
    CANCEL: {
        execute: async (target, { undoAction }) => {
            await delay(500);
            undoAction?.();
        }
    },

    // ============================================
    // SCROLL
    // ============================================
    SCROLL: {
        execute: async (target) => {
            await delay(300);

            const scrollMap = {
                'up': () => window.scrollBy({ top: -500, behavior: 'smooth' }),
                'down': () => window.scrollBy({ top: 500, behavior: 'smooth' }),
                'top': () => window.scrollTo({ top: 0, behavior: 'smooth' }),
                'bottom': () => window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' })
            };

            const scrollAction = scrollMap[target?.toLowerCase()];

            if (scrollAction) {
                scrollAction();
            } else {
                throw new Error(`Unknown scroll direction: ${target}`);
            }
        }
    },

    // ============================================
    // REVIEW (Placeholder for later)
    // ============================================
    REVIEW_START: {
        execute: async (target) => {
            console.log('TODO: Start review for', target);
            alert('Review feature coming soon!');
        }
    }
};

/**
 * Execute a command with error handling
 */
export const executeCommand = async (commandData, dependencies) => {
    const command = commandRegistry[commandData.type];

    if (!command) {
        throw new Error(`Unknown command type: ${commandData.type}`);
    }

    return await command.execute(commandData.target, dependencies);
};