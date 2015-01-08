package com.github.jreddit.action;

import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import com.github.jreddit.utils.restclient.RestResponse;
import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;


/**
 * JUnit testing for the FlairActions class.
 *
 * @author Ryan Delaney (Vitineth)
 * @since 04/01/2015
 */
public class FlairActionsTest {

    public static final String COOKIE = "cookie";
    public static final String SUBREDDIT_NAME = "TestSubreddit";
    public static final String USERNAME = "TestUser";
    public static final String FLAIR_CSS_CLASS = "flair-css";
    public static final String FLAIR_TEMPLATE_ID = "flairTemplateID";
    public static final String FLAIR_TEXT = "TestFlair";
    public static final String FLAIR_SUBMISSION_ID = "t3_SubmID";
    public static final String FLAIR_POSITION = "left";
    private User user;
    private FlairActions subject;
    private RestClient restClient;
    private Response desiredResponse;

    @Before
    public void setUp() {
        user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        restClient = mock(RestClient.class);
        subject = new FlairActions(restClient, user);
        desiredResponse = new RestResponse("{\"json\": {\"errors\": []}}", (JSONObject) JSONValue.parse("{\"json\": {\"errors\": []}}"), mock(HttpResponse.class));
    }

    /**
     * Test for setting the users flair
     */
    @Test
    public void testSetUserFlair() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flair(FLAIR_CSS_CLASS, null, USERNAME, FLAIR_TEXT, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting a links flair
     */
    @Test
    public void testSetLinkFlair() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flair(FLAIR_CSS_CLASS, FLAIR_SUBMISSION_ID, null, FLAIR_TEXT, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing a users flair
     */
    @Test
    public void testRemoveUserFlair() {

        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.deleteFlair(USERNAME, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing all user flair templates.
     */
    @Test
    public void testRemoveUserFlairTemplates() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.clearFlairTemplates("USER_FLAIR", SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing all link flair templates.
     */
    @Test
    public void testRemoveLinkFlairTemplates() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.clearFlairTemplates("LINK_FLAIR", SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting a subreddits flair configs
     */
    @Test
    public void testSetFlairConfigs() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flairConfig(true, FLAIR_POSITION, false, FLAIR_POSITION, true, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for adding a flair template
     */
    @Test
    public void testAddFlairTemplate() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flairTemplate(FLAIR_CSS_CLASS, FLAIR_TEMPLATE_ID, "USER_FLAIR", FLAIR_TEXT, false, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for selecting a users flair
     */
    @Test
    public void testSelectFlair() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.selectFlair(FLAIR_TEMPLATE_ID, null, USERNAME, FLAIR_TEXT, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting fairs as enabled.
     */
    @Test
    public void testSetFlairEnabled() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.setFlairEnabled(true, SUBREDDIT_NAME);
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }


}
