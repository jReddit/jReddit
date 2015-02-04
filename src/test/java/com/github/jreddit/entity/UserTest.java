package com.github.jreddit.entity;

import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static com.github.jreddit.testsupport.JsonHelpers.userLoginResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.jreddit.testsupport.JsonHelpers;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

import java.util.List;


/**
 * Class for testing User-related methods
 * 
 * @author Simon Kassing
 * @author Marc Leef
 */
public class UserTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COOKIE = "cookie";
    public static final String MOD_HASH = "modHash";
    public static final String UNKNOWN_USERNAME = "unknownUsername";
    private User subject;
    private RestClient restClient;
    private UtilResponse subscribedResponse;
    private UtilResponse moderatedResponse;
    private UtilResponse contributedResponse;


    /**
     * Setup mock objects and if necessary stub them.
     */
    @Before
    public void setup() {
    	restClient = mock(RestClient.class);
        subject = new User(restClient, USERNAME, PASSWORD);
        subscribedResponse = new UtilResponse(null, subscribedSubreddits(), 200);
        contributedResponse = new UtilResponse(null, contributedSubreddits(), 200);
        moderatedResponse = new UtilResponse(null, moderatedSubreddits(), 200);
    }
    
    /**
     * Test whether the correct username is stored.
     */
    @Test
    public void testCorrectUsername() {
    	assertEquals(subject.getUsername(), USERNAME);
    }
    
    /**
     * Test connecting the user.
     * @throws Exception If connect failed
     */
    @Test
    public void testConnect() throws Exception {
    	
    	// Stub response
    	Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);
        
        // Connect user
        subject.connect();
        
        // Test that the correct cookie and mod hash have been set
        assertEquals(subject.getCookie(), COOKIE);
        assertEquals(subject.getModhash(), MOD_HASH);
        
    }

    /**
     * Test getting user subscribed subreddits.
     * @throws Exception If connect failed
     */
    @Test
    public void testGetSubscribedSubreddits() throws Exception {
        // Stub response
        Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);

        // Connect user
        subject.connect();

        when(restClient.get(ApiEndpointUtils.USER_GET_SUBSCRIBED + "?&limit=100", COOKIE)).thenReturn(subscribedResponse);
        List<Subreddit> results = subject.getSubscribed(0);
        assertTrue(results.size() == 3);
    }

    /**
     * Test getting user subscribed subreddits with a limit.
     * @throws Exception If connect failed
     */
    @Test
    public void testGetSubscribedSubredditsWithLimit() throws Exception {
        // Stub response
        Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);

        // Connect user
        subject.connect();

        when(restClient.get(ApiEndpointUtils.USER_GET_SUBSCRIBED + "?&limit=1", COOKIE)).thenReturn(moderatedResponse);
        List<Subreddit> results = subject.getSubscribed(1);
        assertTrue(results.size() == 1);
    }

    /**
     /**
     * Test getting user approved contributor subreddits.
     * @throws Exception If connect failed
     */
    @Test
    public void testGetContributedToSubreddits() throws Exception {
        // Stub response
        Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);

        // Connect user
        subject.connect();

        when(restClient.get(ApiEndpointUtils.USER_GET_CONTRIBUTED_TO + "?&limit=100", COOKIE)).thenReturn(contributedResponse);
        List<Subreddit> results = subject.getContributedTo(0);
        assertTrue(results.size() == 2);
    }

    /**
     * Test getting user moderated subreddits.
     * @throws Exception If connect failed
     */
    @Test
    public void testGetModeratedSubreddits() throws Exception {

        // Stub response
        Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);

        // Connect user
        subject.connect();

        // Test getModeratorOf()
        when(restClient.get(ApiEndpointUtils.USER_GET_MODERATOR_OF + "?&limit=100", COOKIE)).thenReturn(moderatedResponse);
        List<Subreddit> results = subject.getModeratorOf(0);
        assertTrue(results.size() == 1);
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
    private JSONObject subscribedSubreddits() {
        JSONObject subreddit1 = JsonHelpers.createSubreddit("subA", "t5_subAID", "subAID");
        JSONObject subreddit2 = JsonHelpers.createSubreddit("subB", "t5_subBID", "subBID");
        JSONObject subreddit3 = JsonHelpers.createSubreddit("subC", "t5_subCID", "subCID");
        return redditListing(subreddit1, subreddit2, subreddit3);
    }

    /**
     * Generate a subreddit listing.
     *
     * @return Subreddit listing
     */
    private JSONObject contributedSubreddits() {
        JSONObject subreddit1 = JsonHelpers.createSubreddit("subA", "t5_subAID", "subAID");
        JSONObject subreddit2 = JsonHelpers.createSubreddit("subB", "t5_subBID", "subBID");
        return redditListing(subreddit1, subreddit2);
    }

    /**
     * Generate a subreddit listing.
     *
     * @return Subreddit listing
     */
    private JSONObject moderatedSubreddits() {
        JSONObject subreddit1 = JsonHelpers.createSubreddit("subA", "t5_subAID", "subAID");
        return redditListing(subreddit1);
    }

}