package com.github.jreddit.retrieval;

import static com.github.jreddit.testsupport.JsonHelpers.createMediaEmbedObject;
import static com.github.jreddit.testsupport.JsonHelpers.createMediaObject;
import static com.github.jreddit.testsupport.JsonHelpers.createRedditError;
import static com.github.jreddit.testsupport.JsonHelpers.createSubmission;
import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.retrieval.params.UserSubmissionsCategory;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Test if the submissions retrieval works correctly.
 * Testing whether submissions are parsed from JSON correctly
 * is done in SubmissionTest.
 * 
 * @author Simon Kassing
 *
 */
public class SubmissionsRetrievalTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    public static final String USERNAME = "TestUser";
    private Submissions subject;
    private RestClient restClient;
    private User user;
    private UtilResponse normalResponse;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        restClient = mock(RestClient.class);
        subject = new Submissions(restClient, user);
        normalResponse = new UtilResponse(null, submissionListings(), 200);
    }
    
    /**
     * Test parsing with normal response.
     */
    @Test
    public void testParseNormal() {
    	
    	// Stub REST client methods
    	String url = "/r/fake";
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        List<Submission> result = subject.parse(url);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
    	
    }
    
    /**
     * Test parse with erroneous response.
     */
    @Test
    public void testParseFailedRetrieval() {
    	
    	// Stub REST client methods
    	String url = "/r/fake";
    	doThrow(new RetrievalFailedException("reason")).when(restClient).get(url, COOKIE);
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        exception.expect(RetrievalFailedException.class);
        subject.parse(url);
    	
    }
    
    /**
     * Test parse with erroneous response.
     */
    @Test
    public void testParseRedditError() {
    	
    	// Stub REST client methods
    	String url = "/r/fake";
        when(restClient.get(url, COOKIE)).thenReturn(new UtilResponse(null, createRedditError(403), 200));
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        exception.expect(RedditError.class);
        subject.parse(url);
    	
    }
    
    /**
     * Test retrieving submissions from a subreddit
     */
    @Test
    public void testSubredditSubmissions() {
    	
    	// Stub REST client methods
    	String url = "/r/" + REDDIT_NAME + ".json?&sort=new&limit=50";
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        when(user.getCookie()).thenReturn(COOKIE);

        // Retrieve the submissions
        List<Submission> result = subject.ofSubreddit(REDDIT_NAME, SubmissionSort.NEW, -1, 50, null, null, false);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
    	
    }
    
    /**
     * Test subreddit when illegal subreddit argument.
     */
    @Test
    public void testSubredditIllegalArgumentSubreddit() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofSubreddit(null, SubmissionSort.NEW, -1, 50, null, null, false);
    }
    
    /**
     * Test retrieving submissions of a user
     */
    @Test
    public void testUserSubmissions() {
    	
        // Stub REST client methods
    	String url = "/user/" + USERNAME + "/submitted.json?&sort=hot&limit=" + RedditConstants.MAX_LIMIT_LISTING;
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);
        
        // Retrieve submissions
        List<Submission> result = subject.ofUser(USERNAME, UserSubmissionsCategory.SUBMITTED, UserOverviewSort.HOT, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);

    }
    
    /**
     * Test user when illegal query argument.
     */
    @Test
    public void testUserIllegalArgumentUsername() {
    	exception.expect(IllegalArgumentException.class);
        subject.ofUser(null, UserSubmissionsCategory.SUBMITTED, UserOverviewSort.HOT, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
    }
    

    /**
     * Test user when illegal query argument.
     */
    @Test
    public void testUserIllegalArgumentCategory() {
    	exception.expect(IllegalArgumentException.class);
        subject.ofUser(USERNAME, null, UserOverviewSort.HOT, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
    }
    
    /**
     * Test user when illegal limit argument.
     */
    @Test
    public void testUserIllegalArgumentLimit() {
    	exception.expect(IllegalArgumentException.class);
        subject.ofUser(USERNAME, UserSubmissionsCategory.SUBMITTED, UserOverviewSort.HOT, -1, -298, null, null, false);
    }
    
    
    /**
     * Test search for submissions.
     */
    @Test
    public void testSearchSubmissions() {
    	
        // Stub REST client methods
    	String url = "/search.json?&q=query&syntax=lucene&sort=new&t=all&limit=" + RedditConstants.MAX_LIMIT_LISTING;
        when(restClient.get(url, COOKIE)).thenReturn(normalResponse);

        // Retrieve submissions
        List<Submission> result = subject.search("query", QuerySyntax.LUCENE, SearchSort.NEW, TimeSpan.ALL, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
        verify(restClient, times(1)).get(url, COOKIE);
        verifyNormalResult(result);
        
    }
    
    /**
     * Test search when illegal query argument.
     */
    @Test
    public void testSearchIllegalArgumentQuery() {
    	exception.expect(IllegalArgumentException.class);
        subject.search(null, QuerySyntax.LUCENE, SearchSort.NEW, TimeSpan.ALL, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
    }
    
    /**
     * Test search when illegal limit argument.
     */
    @Test
    public void testSearchIllegalArgumentLimit() {
    	exception.expect(IllegalArgumentException.class);
        subject.search("query", QuerySyntax.LUCENE, SearchSort.NEW, TimeSpan.ALL, -1, -577, null, null, false);
    }
    
    /**
     * Verify that the normal result is correct.
     * @param result The list of submissions returned.
     */
    public void verifyNormalResult(List<Submission> result) {
        assertEquals(2, result.size());
        assertEquals(result.get(0).getFullName(), "t3_redditObjName");
        assertEquals(result.get(1).getFullName(), "t3_anotherRedditObjName");
    }

    /**
     * Generate a submission listing.
     * 
     * @return Submission listing
     */
    private JSONObject submissionListings() {
        JSONObject media = createMediaObject();
        JSONObject mediaEmbed = createMediaEmbedObject();
        JSONObject submission1 = createSubmission("t3_redditObjName", false, media, mediaEmbed);
        JSONObject submission2 = createSubmission("t3_anotherRedditObjName", false, media, mediaEmbed);
        return redditListing(submission1, submission2);
    }
    
}
