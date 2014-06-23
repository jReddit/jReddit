package com.github.jreddit.subreddit;

import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.github.jreddit.testsupport.JsonHelpers.subredditListingForFunny;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBREDDITS;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBREDDITS_GET;
import static com.github.jreddit.utils.SubredditType.*;
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
    public void getSubredditByNameSuccessfully() throws IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get("/subreddits/search.json?q=funny&limit=1", null)).thenReturn(response);

        Subreddit subreddit = underTest.getByName("funny");
        assertNotNull(subreddit);
    }

    @Test
    public void getSubredditByNameForUnknownReddit() throws IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get("/subreddits/search.json?q=parp&limit=1", null)).thenReturn(response);

        Subreddit subreddit = underTest.getByName("parp");
        assertNull(subreddit);
    }


    @Test
    public void testGetNewSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, NEW.getValue()) + "?limit=" + Subreddits.MAX_LIMIT, null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(NEW);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetMineSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, MINE_SUBSCRIBER.getValue()) + "?limit=" + Subreddits.MAX_LIMIT, null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(MINE_SUBSCRIBER);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetPopularSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, POPULAR.getValue()) + "?limit=" + Subreddits.MAX_LIMIT, null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(POPULAR);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }
}
