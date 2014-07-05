package com.github.jreddit.submissions;

import static com.github.jreddit.testsupport.JsonHelpers.createMediaEmbedObject;
import static com.github.jreddit.testsupport.JsonHelpers.createMediaObject;
import static com.github.jreddit.testsupport.JsonHelpers.createSubmission;
import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmissionsTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    private Submissions underTest;
    private RestClient restClient;
    private User user;

    @Before
    public void setUp() {
        user = mock(User.class);
        restClient = mock(RestClient.class);
        underTest = new Submissions(restClient, user);
    }
    
    @Test
    public void testSubmissionsWithinLimit() throws IOException, ParseException {
    	Response response = new UtilResponse(null, submissionListings(), 50);
    	
        when(restClient.get("/r/" + REDDIT_NAME + ".json?&sort=new&limit=50", COOKIE)).thenReturn(response);
        when(user.getCookie()).thenReturn(COOKIE);

        List<Submission> frontPage = underTest.ofSubreddit(REDDIT_NAME, SubmissionSort.NEW, -1, 50, null, null, false);
        verify(restClient, times(1)).get("/r/" + REDDIT_NAME + ".json?&sort=new&limit=50", COOKIE);
        assertEquals(2, frontPage.size());
        
    	
    }
    
    @Test
    public void testGetSubmissions() throws InterruptedException, IOException, ParseException {
        UtilResponse response = new UtilResponse(null, submissionListings(), 200);
        when(restClient.get("/r/funny.json" + "?&sort=new&limit=" + RedditConstants.MAX_LIMIT_LISTING, null)).thenReturn(response);
        
        List<Submission> subs = underTest.ofSubreddit("funny", SubmissionSort.NEW, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
        assertNotNull(subs);
        assertEquals(subs.size(), 2);
    }
    
    @Test
    public void testSearchSubmissions() throws InterruptedException, IOException, ParseException {
        UtilResponse response = new UtilResponse(null, submissionListings(), 200);
        when(restClient.get("/search.json?&q=query&syntax=lucene&sort=new&t=all&limit=" + RedditConstants.MAX_LIMIT_LISTING, null)).thenReturn(response);

        List<Submission> subs = underTest.search("query", QuerySyntax.LUCENE, SearchSort.NEW, TimeSpan.ALL, -1, RedditConstants.MAX_LIMIT_LISTING, null, null, false);
        assertNotNull(subs);
        assertEquals(subs.size(), 2);
    }

    @SuppressWarnings("unchecked")
    private JSONObject submissionListings() {
        JSONObject media = createMediaObject();
        JSONObject mediaEmbed = createMediaEmbedObject();
        JSONObject submission = createSubmission("t3_redditObjName", false, media, mediaEmbed);
        JSONObject submission1 = createSubmission("t3_anotherRedditObjName", false, media, mediaEmbed);

        JSONObject foo = new JSONObject();
        foo.put("data", submission);
        foo.put("kind", "t3");

        JSONObject bar = new JSONObject();
        bar.put("data", submission1);
        bar.put("kind", "t3");
        
        return redditListing(foo, bar);
    }
}
