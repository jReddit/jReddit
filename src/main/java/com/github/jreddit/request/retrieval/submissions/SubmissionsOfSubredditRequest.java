package com.github.jreddit.request.retrieval.submissions;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.SubmissionSort;

public class SubmissionsOfSubredditRequest extends ListingRequest {

    private static final String ENDPOINT_FORMAT = "/r/%s/%s.json?%s";
    
    private SubmissionSort sort;
    private String subreddit;
    
    /**
     * @param subreddit Subreddit (e.g. "funny")
     * @param sort Sorting method
     */
    public SubmissionsOfSubredditRequest(String subreddit, SubmissionSort sort) {
        this.subreddit = subreddit;
        this.sort = sort;
    }
    
    public SubmissionsOfSubredditRequest setShowAll() {
        this.addParameter("show", "all");
        return this;
    }

    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, subreddit, sort.value(), this.generateParameters());
    }

}
