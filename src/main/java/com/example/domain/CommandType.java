package com.example.domain;

/**
 * CommandType is a type-safe enum of the supported commands
 * Editing: Add new command types here and update their uses in react files easily , consider adding a comment
 * Architecture & Design Patterns:
 *  - Core domain for defining actions in the app separate from service and controller later
 *  - Prevents invalid strings at compile-time (compile-time safety)
 *  - Type-safe enum pattern
 */

public enum CommandType {
    NAVIGATE,    // Navigate to a page
    CLICK,       // Click an element
    SUBMIT,      // Submit a form
    CANCEL,      // Cancel/close/back
    FAVOURITE, // Favouriting games, currently saying it again unfavourites same as filter
    FILTER,
    SEARCH,      // Search for something
    SCROLL,      // Scroll up/down
    SORT,
    REVIEW_START, // Prompt-based for reviews so diff command types for them
    REVIEW_RATING,
    REVIEW_COMMENT,
    REVIEW_SUBMIT,
    REVIEW_CANCEL,
    UNKNOWN,        //Unrecognised prompts retry
    NO_SPEECH,      // No speech
    MIC_MUTE,
    MIC_UNMUTE,
}
