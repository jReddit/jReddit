package com.github.jreddit.retrieval.params;

public enum UserSubmissionsCategory {
	
    SUBMITTED("submitted"), 
    LIKED("liked"), 
    DISLIKED("disliked"), 
    HIDDEN("hidden"),
    SAVED("saved");

    private final String value;

    UserSubmissionsCategory(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
