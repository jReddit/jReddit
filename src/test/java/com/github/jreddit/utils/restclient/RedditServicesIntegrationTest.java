package com.github.jreddit.utils.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.model.json.response.*;
import com.github.jreddit.submissions.Popularity;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.jreddit.utils.restclient.submitbuilders.CommentBuilder.comment;
import static com.github.jreddit.utils.restclient.submitbuilders.VoteBuilder.VOTE_DIRECTION.*;
import static com.github.jreddit.utils.restclient.submitbuilders.VoteBuilder.vote;
import static java.lang.Thread.sleep;
import static org.apache.http.impl.client.HttpClients.createDefault;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedditServicesIntegrationTest {

    public static final String THING_ID = "t3_21rn3a";
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
    public void getUserSubmissions() throws Exception {
        RedditListing<SubmissionListingItem> submissions = redditServices.getUserSubmissions(USERNAME, "submitted", Sort.NEW);

        assertTrue(submissions.getData().getChildren().length > 0);
    }

    @Test
    public void getComments() throws Exception {
        RedditListing<CommentListingItem> comments = redditServices.getUserComments(USERNAME, CommentSort.NEW);

        assertTrue(comments.getData().getChildren().length > 0);
    }

    @Test
    public void getRedditSubmissions() throws Exception {
        RedditListing<SubmissionListingItem> submissions = redditServices.getRedditSubmissions("test", Popularity.NEW, null);

        assertTrue(submissions.getData().getChildren().length > 0);
    }

    @Test
    public void getSubmissionInfo() throws Exception {
        String submissionPermaLink = "/r/test/comments/21rn3a/foo/";
        RedditListing<SubmissionListingItem> submission = redditServices.getSubmissionInfo(submissionPermaLink);

        assertTrue(submission.getData().getChildren()[0].getData().getPermalink().equals(submissionPermaLink));
    }

    @Test
    public void voteMashing() throws Exception {
        String modhash = authenticate();

        redditServices.vote(vote().withModhash(modhash).withThingId(THING_ID).withVote(NEUTRAL));
        sleep(1000);
        redditServices.vote(vote().withModhash(modhash).withThingId(THING_ID).withVote(UP));
        sleep(1000);
        redditServices.vote(vote().withModhash(modhash).withThingId(THING_ID).withVote(DOWN));
        sleep(1000);
        redditServices.vote(vote().withModhash(modhash).withThingId(THING_ID).withVote(NEUTRAL));
    }

    @Test(expected = InvalidCookieException.class)
    public void voteWhenNotLoggedIn() throws Exception {
        redditServices.vote(vote().withModhash("modhash").withThingId(THING_ID).withVote(NEUTRAL));
    }

    @Test(expected = InvalidCookieException.class)
    public void commentOnSubmissionFailsWhenNotLoggedIn() throws Exception {
        redditServices.comment(comment().withCommentText("testComment").withThingId(THING_ID).withModhash("parp"));
    }

    @Test
    public void commentOnSubmission() throws Exception {
        String modhash = authenticate();

        redditServices.comment(comment().withCommentText("testComment").withThingId(THING_ID).withModhash(modhash));
        //TODO: when we've worked out how to handle the response add some sort of assertions here..
    }

    @Test
    public void callNeedsCaptchaWhenNotLoggedIn() throws Exception {
        redditServices.userNeedsCaptcha();
    }

    @Test
    public void callNeedsCaptchaWhenLoggedIn() throws Exception {
        authenticate();
        redditServices.userNeedsCaptcha();
    }


    public String authenticate() throws IOException, URISyntaxException {
        UserLogin userLogin = redditServices.userLogin(USERNAME, PASSWORD);
        assertFalse(userLogin.getJson().getData().getCookie().isEmpty());
        return userLogin.getJson().getData().getModhash();
    }
}

