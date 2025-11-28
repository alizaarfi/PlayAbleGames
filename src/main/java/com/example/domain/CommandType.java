package com.example.domain;


// Enum for types of voice commands
// provides compile-time safety
public enum CommandType {
    NAVIGATE,    // Navigate to a page
    CLICK,       // Click an element
    SUBMIT,      // Submit a form
    CANCEL,      // Cancel/close/back
    FAVOURITE,
    FILTER,
    SEARCH,      // Search for something
    SCROLL,      // Scroll up/down
    SORT,
    REVIEW_START,
    REVIEW_RATING,
    REVIEW_COMMENT,
    REVIEW_SUBMIT,
    UNKNOWN, //Unrecognised
    NO_SPEECH,      // No speech

}
