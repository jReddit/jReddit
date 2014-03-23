package com.github.jreddit.test;

import com.github.jreddit.subreddit.Subreddit;
import com.github.jreddit.subreddit.SubredditType;
import com.github.jreddit.subreddit.Subreddits;
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
    public void testGetNewSubreddits() throws InterruptedException {
        List<Subreddit> newSubreddits = subreddits.getSubreddits(SubredditType.NEW);
        assertNotNull(newSubreddits);
        System.out.println("Retrieved " + newSubreddits.size() + " NEW subreddits.");
    }

    @Test
    public void testGetBannedSubreddits() throws InterruptedException {
        List<Subreddit> bannedSubreddits = subreddits.getSubreddits(SubredditType.BANNED);
        assertNotNull(bannedSubreddits);
        System.out.println("Retrieved " + bannedSubreddits.size() + " Banned subreddits.");
    }

    @Test
    public void testGetPopularSubreddits() throws InterruptedException {
        List<Subreddit> popularSubreddits = subreddits.getSubreddits(SubredditType.POPULAR);
        assertNotNull(popularSubreddits);
        System.out.println("Retrieved " + popularSubreddits.size() + " Popular subreddits.");
    }
}
