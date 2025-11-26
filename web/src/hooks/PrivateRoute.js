import { authProvider } from "./AuthProvider.jsx";
import { Navigate } from "react-router-dom";

function PrivateRoute({ children }) {
    const { isAuthenticated } = authProvider();
    return isAuthenticated ? children : <Navigate to="/login" />;
}

export default PrivateRoute;
