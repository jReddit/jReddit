package com.github.jreddit.retrieval;

import static com.github.jreddit.testsupport.JsonHelpers.subredditListingForFunny;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBREDDITS_GET;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.retrieval.ExtendedSubreddits;
import com.github.jreddit.retrieval.Subreddits;
import com.github.jreddit.retrieval.params.SubredditsView;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Class for testing Subreddit-related methods
 *
 * @author Raul Rene Lepsa
 */
public class SubredditsRetrievalTest {

    private RestClient restClient;
    private ExtendedSubreddits underTest;
    private Response response;

    @Before
    public void setUp() {
        restClient = mock(RestClient.class);
        underTest = new ExtendedSubreddits(new Subreddits(restClient));
    }

    @Test
    public void getSubredditByNameSuccessfully() throws IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get("/subreddits/search.json?&q=funny&limit=1", null)).thenReturn(response);

        Subreddit subreddit = underTest.getByName("funny");
        assertNotNull(subreddit);
    }

    @Test
    public void getSubredditByNameForUnknownReddit() throws IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get("/subreddits/search.json?&q=parp&limit=1", null)).thenReturn(response);

        Subreddit subreddit = underTest.getByName("parp");
        assertNull(subreddit);
    }


    @Test
    public void testGetNewSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, SubredditsView.NEW.value(), "&limit=" + RedditConstants.MAX_LIMIT_LISTING), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(SubredditsView.NEW);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetMineSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, SubredditsView.MINE_SUBSCRIBER.value(), "&limit=" + RedditConstants.MAX_LIMIT_LISTING), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(SubredditsView.MINE_SUBSCRIBER);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void testGetPopularSubreddits() throws InterruptedException, IOException, ParseException {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(String.format(SUBREDDITS_GET, SubredditsView.POPULAR.value(), "&limit=" + RedditConstants.MAX_LIMIT_LISTING), null)).thenReturn(response);

        List<Subreddit> subreddits = underTest.get(SubredditsView.POPULAR);
        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }
}
