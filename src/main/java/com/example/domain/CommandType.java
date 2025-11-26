package com.example.domain;


// Enum for types of voice commands
// provides compile-time safety
public enum CommandType {
    NAVIGATE,    // Navigate to a page
    CLICK,       // Click an element
    SUBMIT,      // Submit a form
    CANCEL,      // Cancel/close/back
    SEARCH,      // Search for something
    SCROLL,      // Scroll up/down
    UNKNOWN, //Unrecognised
    NO_SPEECH,      // No speech

}
