package com.github.jreddit.request.retrieval;

import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.request.RedditGetRequest;

public abstract class ListingRequest extends RedditGetRequest {

    /**
     * @param count Starting count for first element of listing
     * @return This request
     */
    public ListingRequest setCount(int count) {
        this.addParameter("count", String.valueOf(count));
        return this;
    }
    
    /**
     * @param limit Maximum number of listing elements. This does not mean exactly this parameter will be returned. An upper bound (~100) is imposed by reddit.
     * @return This request
     */
    public ListingRequest setLimit(int limit) {
        this.addParameter("limit", String.valueOf(limit));
        return this;
    }
    
    /**
     * @param after The thing in a listing after which the newly requested listing should start.
     * @return This request
     */
    public ListingRequest setAfter(Thing after) {
        return setAfter(after.getFullName());
    }
    
    /**
     * @param after The fullname of a thing in a listing after which the newly requested listing should start.
     * @return This request
     * @see ListingRequest#setAfter(Thing) The usage of setAfter(Thing) is preferred over this method
     */
    public ListingRequest setAfter(String after) {
        this.addParameter("after", after);
        return this;
    }
    
    /**
     * @param before The thing in a listing before which the newly requested listing should end.
     * @return This request
     */
    public ListingRequest setBefore(Thing before) {
        return setBefore(before.getFullName());
    }
    
    /**
     * @param before The fullname of a thing in a listing before which the newly requested listing should end.
     * @return This request
     * @see ListingRequest#setBefore(Thing) The usage of setBefore(Thing) is preferred over this method
     */
    public ListingRequest setBefore(String before) {
        this.addParameter("before", before);
        return this;
    }
    
}
