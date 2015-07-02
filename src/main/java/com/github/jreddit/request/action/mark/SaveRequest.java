package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class SaveRequest extends MarkActionRequest {
    
    /** Endpoint format. */
    private static final String ENDPOINT_FORMAT = "/api/save.json?";

    public SaveRequest(String fullname) {
        super(fullname);
    }
    
    @Override
    public String generateRedditURI() {
        return ENDPOINT_FORMAT;
    }
    
}
