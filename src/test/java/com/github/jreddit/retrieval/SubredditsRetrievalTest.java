package com.github.jreddit.retrieval;

import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.params.SubredditsView;
import com.github.jreddit.testsupport.JsonHelpers;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Class for testing Subreddit-related methods
 *
 * @author Raul Rene Lepsa
 */
public class SubredditsRetrievalTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    public static final String USERNAME = "TestUser";
    private Subreddits subject;
    private RestClient restClient;
    private User user;
    private UtilResponse normalResponse;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    /**
     * Mock classes it depends on.
     */
    @Before
    public void setup() {
    	user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        restClient = mock(RestClient.class);
        subject = new Subreddits(restClient, user);
        normalResponse = new UtilResponse(null, subredditListings(), 200);
    }


    /**
     * Test parsing with normal response.
     */
    @Test
    public void testParseNormal() {
    	
    	// Stub REST client methods
    	String url = "/some/fake/url";
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        List<Subreddit> result = subject.parse(url);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
    	
    }
    
    /**
     * Test parse with erroneous response.
     */
    @Test
    public void testParseFailedRetrieval() {
    	
    	// Stub REST client methods
    	String url = "/some/fake/url";
    	doThrow(new RetrievalFailedException("reason")).when(restClient).get(url, COOKIE);
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        exception.expect(RetrievalFailedException.class);
        subject.parse(url);
    	
    }
    
    /**
     * Test retrieving subreddits from the overview.
     */
    @Test
    public void testSearchSubreddits() {
    	
    	// Stub REST client methods
    	String url = "/subreddits/search.json?&q=query";
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        when(user.getCookie()).thenReturn(COOKIE);
        
        // Retrieve the submissions
        List<Subreddit> result = subject.search("query", -1, -1, null, null);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
    	
    }
    
    /**
     * Test that the search of subreddits fail with a null query.
     */
    @Test
    public void testSearchSubredditsFailedQuery() {
    	exception.expect(IllegalArgumentException.class);
        subject.search(null, -1, -1, null, null);
    }
    
    /**
     * Test retrieving subreddits using search.
     */
    @Test
    public void testOverviewSubreddits() {
    	
    	// Stub REST client methods
    	String url = "/subreddits/" + SubredditsView.MINE_CONTRIBUTOR.value() + ".json?&limit=100";
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        when(user.getCookie()).thenReturn(COOKIE);
        
        // Retrieve the submissions
        List<Subreddit> result = subject.get(SubredditsView.MINE_CONTRIBUTOR, -1, 100, null, null);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
    	
    }
    
    /**
     * Verify that the normal result is correct.
     * @param result The list of subreddits returned.
     */
    public void verifyNormalResult(List<Subreddit> result) {
        assertEquals(3, result.size());
        assertEquals(result.get(0).getFullName(), "t5_subAID");
        assertEquals(result.get(1).getFullName(), "t5_subBID");
        assertEquals(result.get(2).getFullName(), "t5_subCID");
    }
    
    /**
     * Generate a subreddit listing.
     * 
     * @return Subreddit listing
     */
    private JSONObject subredditListings() {
        JSONObject subreddit1 = JsonHelpers.createSubreddit("subA", "t5_subAID", "subAID");
        JSONObject subreddit2 = JsonHelpers.createSubreddit("subB", "t5_subBID", "subBID");
        JSONObject subreddit3 = JsonHelpers.createSubreddit("subC", "t5_subCID", "subCID");
        return redditListing(subreddit1, subreddit2, subreddit3);
    }
    
}
