package com.reddit.test;

import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.subreddit.Subreddits;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Class for testing Subreddit-related methods
 *
 * @author Raul Rene Lepsa
 */
public class SubredditTest {

    /**
     * Get Subreddit by Name. This also tests Subreddits.listDefault() method, because it is used by the getSubredditByName one
     */
    @Test
    public void testGetSubredditByName() {
        Subreddits subreddits = new Subreddits();

        Subreddit subreddit = subreddits.getSubredditByName("funny");
        assertNotNull(subreddit);

        subreddit = subreddits.getSubredditByName("dbuaoudbasygdavyidayda2551678e1");
        assertNull(subreddit);
    }
}
