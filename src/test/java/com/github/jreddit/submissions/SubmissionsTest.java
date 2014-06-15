package com.github.jreddit.submissions;

import static com.github.jreddit.testsupport.JsonHelpers.createMediaEmbedObject;
import static com.github.jreddit.testsupport.JsonHelpers.createMediaObject;
import static com.github.jreddit.testsupport.JsonHelpers.createSubmission;
import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.github.jreddit.submissions.Submissions.Sort;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.user.User;
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
        underTest = new Submissions(restClient);
    }
    
    @Test
    public void testSubmissionsWithinLimit() throws IOException, ParseException {
    	Response response = new UtilResponse(null, submissionListings(), 50);
    	
        when(restClient.get("/r/" + REDDIT_NAME + "/new.json?limit=50", COOKIE)).thenReturn(response);
        when(user.getCookie()).thenReturn(COOKIE);

         List<Submission> frontPage = underTest.getSubmissionsWithinLimit(REDDIT_NAME, user, Sort.NEW, 50, null);
         assertEquals(frontPage.size(), 2);
    	
    }

    @SuppressWarnings("unchecked")
    private JSONObject submissionListings() {
        JSONObject media = createMediaObject();
        JSONObject mediaEmbed = createMediaEmbedObject();
        JSONObject submission = createSubmission("redditObjName", false, media, mediaEmbed);
        JSONObject submission1 = createSubmission("anotherRedditObjName", false, media, mediaEmbed);

        JSONObject foo = new JSONObject();
        foo.put("data", submission);
        foo.put("kind", "t3");

        JSONObject bar = new JSONObject();
        bar.put("data", submission1);
        bar.put("kind", "t3");

        return redditListing(foo, bar);
    }
}
