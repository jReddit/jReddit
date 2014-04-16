package com.github.jreddit.subreddit;

import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.jreddit.subreddit.SubredditType.*;
import static com.github.jreddit.testsupport.JsonHelpers.subredditListingForFunny;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBREDDITS;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBREDDITS_GET;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class for testing Subreddit-related methods
 *
 * @author Raul Rene Lepsa
 */
public class SubredditTest {

    private RestClient restClient;
    private Subreddits underTest;
    private Response response;

    @Before
    public void setUp() {
        restClient = mock(RestClient.class);
        underTest = new Subreddits(restClient);
    }

    @Test
    public void getSubredditByNameSuccessfully() {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(SUBREDDITS, null)).thenReturn(response);

        Subreddit subreddit = underTest.getSubredditByName("funny");
        assertNotNull(subreddit);
    }

    @Test
    public void getSubredditByNameForUnknownReddit() {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(SUBREDDITS, null)).thenReturn(response);

        Subreddit subreddit = underTest.getSubredditByName("parp");
        assertNull(subreddit);
    }

    @Test
    public void listDefaultReddits() {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(SUBREDDITS, null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.listDefault();
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }


    @Test
    public void testGetNewSubreddits() throws InterruptedException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, NEW.getValue()), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.getSubreddits(NEW);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetBannedSubreddits() throws InterruptedException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, BANNED.getValue()), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.getSubreddits(BANNED);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetPopularSubreddits() throws InterruptedException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, POPULAR.getValue()), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.getSubreddits(POPULAR);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }
}
