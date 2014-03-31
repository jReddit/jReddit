package com.github.jreddit.utils;

import com.github.jreddit.RedditServices;
import com.github.jreddit.model.domain.Page;
import com.github.jreddit.model.domain.Popularity;
import com.github.jreddit.model.json.response.RedditListing;
import com.github.jreddit.model.json.response.SubmissionListingItem;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * This class offers some submission utilities.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class Submissions {

    private final RedditServices redditServices;

    public Submissions(RedditServices redditServices) {
        this.redditServices = redditServices;
    }

    /**
     * Return the submissions of a reddit by type, page
     *
     * @param redditName The subreddit's name
     * @param popularity       HOT or NEW and some others to come
     * @param page       //TODO Implement Pages
     * @return RedditListing containing submissions
     *
     * @throws IOException        If connection fails
     * @throws URISyntaxException If bad url
     */
    public RedditListing<SubmissionListingItem>
            getSubmissions(String redditName, Popularity popularity, Page page) throws IOException, URISyntaxException {
        return redditServices.getRedditSubmissions(redditName, popularity, page);
    }
}