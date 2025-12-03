import React, {
    createContext,
    useContext,
    useEffect,
    useState,
} from "react";

const FavoritesContext = createContext();

// base URL for backend API
const API_BASE = "http://localhost:8081";

// read auth token from localStorage
const getToken = () =>
    localStorage.getItem("authToken") ||
    localStorage.getItem("token") ||
    localStorage.getItem("jwt");

// toast notification
const showToast = (msg) => {
    const el = document.createElement("div");
    el.className = "toast-notice";
    el.textContent = msg;

    document.body.appendChild(el);

    requestAnimationFrame(() => {
        el.classList.add("visible");
    });

    setTimeout(() => {
        el.classList.remove("visible");
        setTimeout(() => el.remove(), 300);
    }, 2500);
};

export function FavoritesProvider({ children }) {
    // whether a token exists (used as our "logged in" flag)
    const [hasToken, setHasToken] = useState(!!getToken());

    // list of favourite game IDs for the current user
    const [favorites, setFavorites] = useState([]);

    // loading flag for initial fetch
    const [loading, setLoading] = useState(false);

    // ---------------------------------------------------------
    // Watch for token changes (login/logout)
    // ---------------------------------------------------------
    useEffect(() => {
        const handleStorage = (event) => {
            if (!event.key) return;
            if (
                event.key === "authToken" ||
                event.key === "token" ||
                event.key === "jwt"
            ) {
                const nowHasToken = !!getToken();
                setHasToken(nowHasToken);
                if (!nowHasToken) {
                    setFavorites([]);
                }
            }
        };

        window.addEventListener("storage", handleStorage);

        // polling for same-tab logout
        const intervalId = setInterval(() => {
            setHasToken((prev) => {
                const nowHasToken = !!getToken();
                if (prev && !nowHasToken) {
                    setFavorites([]);
                }
                return nowHasToken;
            });
        }, 500);

        return () => {
            window.removeEventListener("storage", handleStorage);
            clearInterval(intervalId);
        };
    }, []);

    // ---------------------------------------------------------
    // When the user is logged in, load favourites from the backend
    // ---------------------------------------------------------
    useEffect(() => {
        const token = getToken();

        if (!token) {
            setFavorites([]);
            setLoading(false);
            return;
        }

        setLoading(true);

        fetch(`${API_BASE}/api/favorites`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(async (res) => {
                if (!res.ok) {
                    const text = await res.text().catch(() => "");
                    console.error("Failed to load favourites:", res.status, text);
                    showToast("Could not load favourites from server.");
                    setFavorites([]);
                    return;
                }
                return res.json();
            })
            .then((list) => {
                if (Array.isArray(list)) {
                    setFavorites(list);
                }
            })
            .catch((err) => {
                console.error("Network error fetching favourites:", err);
                showToast("Network error while loading favourites.");
                setFavorites([]);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [hasToken]);

    // ---------------------------------------------------------
    // Toggle favourite for a game (DB + local state)
    // ---------------------------------------------------------
    const toggleFavorite = async (gameId, event) => {
        if (event && event.stopPropagation) {
            event.stopPropagation();
        }

        const token = getToken();

        if (!token) {
            showToast("Please log in to save favourites.");
            setHasToken(false);
            return;
        }

        setHasToken(true);

        const isFav = favorites.includes(gameId);
        const method = isFav ? "DELETE" : "POST";

        // optimistic update
        setFavorites((prev) =>
            isFav ? prev.filter((id) => id !== gameId) : [...prev, gameId]
        );

        try {
            const res = await fetch(`${API_BASE}/api/favorites/${gameId}`, {
                method,
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!res.ok) {
                const text = await res.text().catch(() => "");
                console.error("Failed to update favourite:", res.status, text);
                showToast("Could not update favourites (server error).");
                return;
            }

            showToast(
                isFav ? "Removed from favourites." : "Added to favourites."
            );
        } catch (err) {
            console.error("Network error updating favourite:", err);
            showToast("Network error while updating favourites.");
        }
    };

    return (
        <FavoritesContext.Provider
            value={{ favorites, toggleFavorite, loading }}
        >
            {children}
        </FavoritesContext.Provider>
    );
}

export const useFavorites = () => useContext(FavoritesContext);
