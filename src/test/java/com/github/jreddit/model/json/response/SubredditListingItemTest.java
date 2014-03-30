package com.github.jreddit.model.json.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.currentThread;
import static org.junit.Assert.assertTrue;

public class SubredditListingItemTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void createSubredditListing() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/subredditlisting-item.json");
        SubredditListingItem subreddit = mapper.readValue(resource, SubredditListingItem.class);

        assertTrue(subreddit.getData().getSubscribers() == 5619102);
    }

    @Test
    public void createSubredditListings() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/subreddits.json");
        RedditListing<SubredditListingItem> subredditsListing = mapper.readValue(resource, new TypeReference<RedditListing<SubredditListingItem>>() { });

        SubredditListingItem[] subreddits = subredditsListing.getData().getChildren();
        SubredditListingItem subreddit = subreddits[0];

        assertTrue(subreddit.getData().getSubscribers() == 5555797L);
        assertTrue(subreddit.getKind().equals("t5"));
    }
}
