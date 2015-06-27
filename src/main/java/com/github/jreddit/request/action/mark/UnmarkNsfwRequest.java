package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class UnmarkNsfwRequest extends MarkActionRequest {
    
    /** Endpoint format. */
    private static final String ENDPOINT_FORMAT = "/api/unmarknsfw.json?";

    public UnmarkNsfwRequest(String fullname) {
        super(fullname);
    }
    
    @Override
    public String generateRedditURI() {
        return ENDPOINT_FORMAT;
    }
    
}
