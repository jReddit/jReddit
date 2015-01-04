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

import static org.mockito.Mockito.*;

/**
 * Created by Ryan on 04/01/2015.
 *
 * @author Ryan
 * @since 04/01/2015
 */
public class FlairActionsTest {

    private User user;
    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    public static final String USERNAME = "TestUser";
    private FlairActions subject;
    private RestClient restClient;
    private Response response;
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
        subject.flair("red", null, "Tridentac", "Blueprinter", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting a links flair
     */
    @Test
    public void testSetLinkFlair() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flair("red", "t3_2r86db", null, "Blueprinter", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing a users flair
     */
    @Test
    public void testRemoveUserFlair() {

        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.deleteFlair("Tridentac", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing all user flair templates.
     */
    @Test
    public void testRemoveUserFlairTemplates() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.clearFlairTemplates("USER_FLAIR", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for removing all link flair templates.
     */
    @Test
    public void testRemoveLinkFlairTemplates() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.clearFlairTemplates("LINK_FLAIR", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting a subreddits flair configs
     */
    @Test
    public void testSetFlairConfigs() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flairConfig(true, "left", false, "right", true, "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for adding a flair template
     */
    @Test
    public void testAddFlairTemplate() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.flairTemplate("blueteam", "BLUE-TAG", "USER_FLAIR", "Blue Team", false, "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for selecting a users flair
     */
    @Test
    public void testSelectFlair() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.selectFlair("BLUE-TAG", null, "Vitineth", "Blue team", "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }

    /**
     * Test for setting fairs as enabled.
     */
    @Test
    public void testSetFlairEnabled() {
        when(restClient.post(anyString(), anyString(), eq(COOKIE))).thenReturn(desiredResponse);
        subject.setFlairEnabled(true, "myblueprints");
        verify(restClient, times(1)).post(anyString(), anyString(), eq(COOKIE));
    }


}
