package com.github.jreddit.request.retrieval.subreddits;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.SubredditsView;

public class SubredditsOfUserRequest extends ListingRequest {
    
    private static final String ENDPOINT_FORMAT = "/subreddits/%s.json?%s"; // ApiEndpointUtils.SUBREDDITS_GET
    
    private SubredditsView view;
    
    /**
     * @param view View of the subreddits
     */
    public SubredditsOfUserRequest(SubredditsView view) {
        this.view = view;
    }
    
    public SubredditsOfUserRequest setShowAll() {
        this.addParameter("show", "all");
        return this;
    }
    
    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, view, this.generateParameters());
    }
    
}
