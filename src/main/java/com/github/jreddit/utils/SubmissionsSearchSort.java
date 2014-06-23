package com.github.jreddit.utils;

/**
 * Enumeration to represent the different sort methods for submission search.
 * @author Simon Kassing
 */
public enum SubmissionsSearchSort {

    HOT			("hot"), 
    RELEVANCE	("relevance"), 
    NEW			("new"), 
    TOP			("top"), 
    COMMENTS	("comments");

    private final String value;

    SubmissionsSearchSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
