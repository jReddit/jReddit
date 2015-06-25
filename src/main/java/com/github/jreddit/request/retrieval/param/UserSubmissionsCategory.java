package com.github.jreddit.request.retrieval.param;

public enum UserSubmissionsCategory {
	
    SUBMITTED("submitted"), 
    UPVOTED("upvoted"), 
    DOWNVOTED("downvoted"), 
    HIDDEN("hidden");

    private final String value;

    UserSubmissionsCategory(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
