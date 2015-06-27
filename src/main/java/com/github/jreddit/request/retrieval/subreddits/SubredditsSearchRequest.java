package com.github.jreddit.request.retrieval.subreddits;

import com.github.jreddit.request.retrieval.ListingRequest;

public class SubredditsSearchRequest extends ListingRequest {
    
    private static final String ENDPOINT_FORMAT = "/subreddits/search.json?%s";
    
    /**
     * @param query Search query (e.g. "programming")
     */
    public SubredditsSearchRequest(String query) {
        this.addParameter("q", query);
    }
    
    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, this.generateParameters());
    }
    
}
