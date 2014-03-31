package com.github.jreddit.utils.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jreddit.model.json.response.*;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.apache.http.impl.client.HttpClients.createDefault;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedditServicesIntegrationTest {

    private static String USERNAME = "jReddittest";
    private static String PASSWORD = "jReddittest";
    private RedditServices redditServices;

    @Before
    public void setUp() {
        redditServices = new RedditServices(new BetterRestClient(createDefault(), new BasicRedditResponseHandler()), new ObjectMapper());
    }

    @Test
    public void getUserInfo() throws Exception {
        authenticate();
        UserInfo userInfo = redditServices.getUserInfo();
        assertTrue(userInfo.getData().getName().equals(USERNAME));
    }

    @Test
    public void getSubscribed() throws Exception {
        authenticate();
        RedditListing<SubredditListingItem> subscribedSubs = redditServices.getSubscribed();
        assertTrue(subscribedSubs.getData().getChildren().length > 0);
    }

    @Test
    public void getUserAbout() throws Exception {
        UserAbout userAbout = redditServices.getUserAbout("intortus");
        assertTrue(userAbout.getData().getName().equals("intortus"));
    }

    @Test
    public void getSubmissions() throws Exception {
        RedditListing<SubmissionListingItem> submissions = redditServices.getSubmissions(USERNAME, "submitted", Sort.NEW);

        assertTrue(submissions.getData().getChildren().length > 0);
    }

    @Test
    public void getComments() throws Exception {
        RedditListing<CommentListingItem> comments = redditServices.getComments(USERNAME, CommentSort.NEW);

        assertTrue(comments.getData().getChildren().length > 0);
    }



    public void authenticate() throws IOException, URISyntaxException {
        UserLogin userLogin = redditServices.userLogin(USERNAME, PASSWORD);
        assertFalse(userLogin.getJson().getData().getCookie().isEmpty());
    }
}

