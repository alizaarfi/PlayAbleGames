import React from 'react';
import ReactDOM from 'react-dom/client';
import HomePage from './components/HomePage';
import './style.css';

// Render the homepage component
ReactDOM.createRoot(document.getElementById('app')).render(
    <React.StrictMode>
        <HomePage />
    </React.StrictMode>
);