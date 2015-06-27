package com.github.jreddit.request.retrieval.mixed;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.TimeSpan;
import com.github.jreddit.request.retrieval.param.UserMixedCategory;
import com.github.jreddit.request.retrieval.param.UserOverviewSort;

public class MixedOfUserRequest extends ListingRequest {

    /** Endpoint format. */
    private static final String ENDPOINT_FORMAT = "/user/%s/%s.json?%s";
    
    private UserMixedCategory category;
    private String username;
    
    /**
     * @param username Username of a user (e.g. "JohnM")
     * @param category Category of mixed things
     */
    public MixedOfUserRequest(String username, UserMixedCategory category) {
        this.username = username;
        this.category = category;
    }

    /**
     * Set the sorting method.<br>
     * <br>
     * <i>Note: This only works for Overview</i>
     * 
     * @param sort Sorting method
     * 
     * @return This builder
     */
    public MixedOfUserRequest setSort(UserOverviewSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    /**
     * Set the time span.<br>
     * <br>
     * <i>Note: This only works for Overview, and then specifically for the top/controversial sorting method.</i>
     * 
     * @param time Time span
     * 
     * @return This builder
     */
    public MixedOfUserRequest setTime(TimeSpan time) {
        this.addParameter("t", time.value());
        return this;
    }
    
    public MixedOfUserRequest setShowGiven() {
        this.addParameter("show", "given");
        return this;
    }

    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, username, category.value(), this.generateParameters());
    }
    
}
