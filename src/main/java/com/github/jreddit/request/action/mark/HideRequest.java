package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class HideRequest extends MarkActionRequest {
    
    /** Endpoint format. */
    private static final String ENDPOINT_FORMAT = "/api/hide.json?";

    public HideRequest(String fullname) {
        super(fullname);
    }
    
    @Override
    public String generateRedditURI() {
        return ENDPOINT_FORMAT;
    }
    
}
