package com.example.domain;


// types of voice commands
public enum CommandType {
    NAVIGATE,    // Navigate to a page
    CLICK,       // Click an element
    SUBMIT,      // Submit a form
    CANCEL,      // Cancel/close/back
    SEARCH,      // Search for something
    SCROLL,      // Scroll up/down
    UNKNOWN      // Unrecognized command
}
