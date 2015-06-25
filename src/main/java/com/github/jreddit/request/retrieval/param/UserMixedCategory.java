package com.github.jreddit.request.retrieval.param;

public enum UserMixedCategory {
	
    OVERVIEW("overview"), 
    GILDED_RECEIVED("gilded"), 
    GILDED_GIVEN("gilded/given"), 
    SAVED("saved");

    private final String value;

    UserMixedCategory(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
