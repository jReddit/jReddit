package com.github.jreddit.request.retrieval.comments;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.TimeSpan;
import com.github.jreddit.request.retrieval.param.UserOverviewSort;

public class CommentsOfUserRequest extends ListingRequest {

    private static final String ENDPOINT_FORMAT = "/user/%s/comments.json?%s";

    String username;
    
    /**
     * @param username Username of a user (e.g. "JohnM")
     */
    public CommentsOfUserRequest(String username) {
        this.username = username;
    }

    public CommentsOfUserRequest setSort(UserOverviewSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    public CommentsOfUserRequest setShowGiven() {
        this.addParameter("show", "given");
        return this;
    }

    public CommentsOfUserRequest setTime(TimeSpan time) {
        this.addParameter("t", time.value());
        return this;
    }
    
    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, username, this.generateParameters());
    }
    
}
