package com.github.jreddit.retrieval.params;

/**
 * Enumeration to represent the different sort methods for submission search.
 * @author Simon Kassing
 */
public enum SearchSort {

    HOT("hot"), 
    RELEVANCE("relevance"), 
    NEW("new"), 
    TOP("top"), 
    COMMENTS("comments");

    private final String value;

    SearchSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
