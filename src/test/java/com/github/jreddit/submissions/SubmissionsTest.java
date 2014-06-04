package com.github.jreddit.submissions;

import com.github.jreddit.user.User;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.github.jreddit.testsupport.JsonHelpers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void testNew() throws IOException, ParseException {
        Response response = new UtilResponse(null, submissionListings(), 200);
        String urlString = "/r/" + REDDIT_NAME + ".json";

        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.get(urlString, COOKIE)).thenReturn(response);

        List<Submission> frontPage = underTest.getSubmissions(REDDIT_NAME, Submissions.Popularity.HOT, Submissions.Page.FRONTPAGE, user);

        assertTrue(frontPage.size() == 2);
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
