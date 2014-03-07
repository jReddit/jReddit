package com.reddit.test;

import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.subreddit.SubredditType;
import im.goel.jreddit.subreddit.Subreddits;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Class for testing Subreddit-related methods
 *
 * @author Raul Rene Lepsa
 */
public class SubredditTest {

    private static Subreddits subreddits = new Subreddits();

    /**
     * Get Subreddit by Name. This also tests Subreddits.listDefault() method, because it is used by the getSubredditByName one
     */
    @Test
    public void testGetSubredditByName() {
        Subreddit subreddit = subreddits.getSubredditByName("funny");
        assertNotNull(subreddit);

        subreddit = subreddits.getSubredditByName("dbuaoudbasygdavyidayda2551678e1");
        assertNull(subreddit);
    }

    @Test
    public void testGetSubreddits() {
        List<Subreddit> newSubreddits = subreddits.getSubreddits(SubredditType.NEW);
        List<Subreddit> bannedSubreddits = subreddits.getSubreddits(SubredditType.BANNED);
        List<Subreddit> popularSubreddits = subreddits.getSubreddits(SubredditType.POPULAR);

        assertNotNull(newSubreddits);
        assertNotNull(bannedSubreddits);
        assertNotNull(popularSubreddits);

        System.out.println("Retrieved " + newSubreddits.size() + " NEW subreddits.");
        System.out.println("Retrieved " + bannedSubreddits.size() + " BANNED subreddits.");
        System.out.println("Retrieved " + popularSubreddits.size() + " POPULAR subreddits.");
    }
}
