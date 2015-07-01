package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class ReportRequest extends MarkActionRequest {
    
    /** Endpoint format. */
    private static final String ENDPOINT_FORMAT = "/api/report.json?";

    public ReportRequest(String fullname) {
        super(fullname);
    }
    
    @Override
    public String generateRedditURI() {
        return ENDPOINT_FORMAT;
    }
    
}
