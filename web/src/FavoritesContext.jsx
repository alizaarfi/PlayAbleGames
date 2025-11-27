import React, {
    createContext,
    useContext,
    useEffect,
    useState,
} from "react";

const FavoritesContext = createContext();

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

    // trigger CSS transition
    requestAnimationFrame(() => {
        el.classList.add("visible");
    });

    setTimeout(() => {
        el.classList.remove("visible");
        setTimeout(() => el.remove(), 300);
    }, 2500);
};

export function FavoritesProvider({ children }) {
    // track whether a token exists
    const [hasToken, setHasToken] = useState(!!getToken());

    // initial favourites: only load if logged in
    const [favorites, setFavorites] = useState(() => {
        if (!getToken()) return [];
        const saved = localStorage.getItem("favorites");
        return saved ? JSON.parse(saved) : [];
    });

    // persist favourites only when logged in
    useEffect(() => {
        if (hasToken) {
            localStorage.setItem("favorites", JSON.stringify(favorites));
        } else {
            localStorage.removeItem("favorites");
        }
    }, [favorites, hasToken]);

    // watch for logout: clear favourites when token disappears
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
                    localStorage.removeItem("favorites");
                }
            }
        };

        window.addEventListener("storage", handleStorage);

        // simple poll for changes in the same tab that performed logout
        const intervalId = setInterval(() => {
            setHasToken((prev) => {
                const nowHasToken = !!getToken();
                if (prev && !nowHasToken) {
                    setFavorites([]);
                    localStorage.removeItem("favorites");
                }
                return nowHasToken;
            });
        }, 500);

        return () => {
            window.removeEventListener("storage", handleStorage);
            clearInterval(intervalId);
        };
    }, []);

    // Toggle favourite for a game
    // Requires login
    const toggleFavorite = (gameId, event) => {
        if (event && event.stopPropagation) {
            event.stopPropagation();
        }

        if (!hasToken) {
            showToast("Please log in to save favourites.");
            return;
        }

        setFavorites((prev) => {
            const isFav = prev.includes(gameId);
            const next = isFav
                ? prev.filter((id) => id !== gameId)
                : [...prev, gameId];

            showToast(
                isFav ? "Removed from favourites." : "Added to favourites."
            );

            return next;
        });
    };

    return (
        <FavoritesContext.Provider value={{ favorites, toggleFavorite }}>
            {children}
        </FavoritesContext.Provider>
    );
}

export const useFavorites = () => useContext(FavoritesContext);
