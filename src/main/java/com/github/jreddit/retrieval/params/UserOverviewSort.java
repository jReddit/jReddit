package com.github.jreddit.retrieval.params;

/**
 * Enumeration to represent the different sort methods for the user overview.
 * @author Simon Kassing
 */
public enum UserOverviewSort {
	
    NEW("new"), 
    HOT("hot"), 
    TOP("top"), 
    COMMENTS("controversial");

    private final String value;

    UserOverviewSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
