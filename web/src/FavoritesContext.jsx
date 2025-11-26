import React, {
    createContext,
    useContext,
    useEffect,
    useState,
} from "react";

const FavoritesContext = createContext();

const API_BASE = "http://localhost:8081";
//Hard coded user will replace when login system is made
const USER_ID = 1;

// ----- Helper to ensure IDs are always treated as numbers
const toId = (value) => Number(value);

export function FavoritesProvider({ children }) {
    const [favorites, setFavorites] = useState([]);
    const [loading, setLoading] = useState(true);

    // ---------------------------------------------------------------------
    // Load favorites from backend once when the app starts.
    // Calls: GET /api/users/{userId}/favorites
    // ---------------------------------------------------------------------
    useEffect(() => {
        console.log("Favorites state is now:", favorites);
    }, [favorites]);

    useEffect(() => {
        async function loadFavorites() {
            try {
                const res = await fetch(`${API_BASE}/api/users/${USER_ID}/favorites`);
                console.log("GET favorites status:", res.status);

                if (!res.ok) {
                    console.error("Failed to load favorites:", await res.text());
                    setFavorites([]);
                    return;
                }

                const data = await res.json();
                const ids = Array.isArray(data) ? data.map(toId) : [];
                console.log("Loaded favorites from backend:", ids);
                setFavorites(ids);
            } catch (err) {
                console.error("Error fetching favorites:", err);
                setFavorites([]);
            } finally {
                setLoading(false);
            }
        }

        loadFavorites();
    }, []);


    // ---------------------------------------------------------------------
    // Toggle a game's favorite status.
    // Uses optimistic UI: update immediately, then notify backend.
    // Calls:
    //   POST   /api/users/{userId}/favorites/{gameId}
    //   DELETE /api/users/{userId}/favorites/{gameId}
    // ---------------------------------------------------------------------
    const toggleFavorite = async (gameId, event) => {
        if (event) event.stopPropagation();

        const id = toId(gameId);
        const isFav = favorites.includes(id);

        const next = isFav
            ? favorites.filter((v) => v !== id)
            : [...favorites, id];

        setFavorites(next);

        try {
            const method = isFav ? "DELETE" : "POST";
            const res = await fetch(
                `${API_BASE}/api/users/${USER_ID}/favorites/${id}`,
                { method }
            );

            console.log(`${method} /favorites/${id} ->`, res.status);

            if (!res.ok) {
                // If backend fails, UI stays updated but logs error
                console.error("Backend favorite update failed:", await res.text());
            }
        } catch (err) {
            console.error("Network error updating favorite:", err);
            // Hearts remain filled because we did not revert optimistic UI
        }
    };

    // ----- Provide favorites state and toggle function to the app
    return (
        <FavoritesContext.Provider
            value={{ favorites, toggleFavorite, loading }}
        >
            {children}
        </FavoritesContext.Provider>
    );
}

// ----- Hook for components to access favorites easily
export function useFavorites() {
    return useContext(FavoritesContext);
}
