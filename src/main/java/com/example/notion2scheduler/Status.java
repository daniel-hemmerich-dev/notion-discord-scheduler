package com.example.notion2scheduler;

/**
 * The different status a post can have.
 */
public enum Status {
    SCHEDULED,  // Created, not posted yet
    POSTED,     // The post was published
    FAILED      // An error occurred during posting
}
