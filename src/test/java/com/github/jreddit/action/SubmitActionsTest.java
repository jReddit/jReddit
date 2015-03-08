package com.github.jreddit.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

import com.github.jreddit.entity.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import com.github.jreddit.utils.restclient.RestResponse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.http.HttpResponse;

public class SubmitActionsTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    public static final String USERNAME = "TestUser";
    public static final String MODHASH = "modhash";
    private SubmitActions submitAction;
    private RestClient restClient;
    private User user;
    private Response responseNotAuthor;
    private Response responseUserRequired;
    private Response responseEmpty;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Mock classes it depends on.
     */
    @Before
    public void setup() {
        user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        when(user.getModhash()).thenReturn(MODHASH);
        restClient = mock(RestClient.class);
        submitAction = new SubmitActions(restClient, user);

        JSONObject errorObject = new JSONObject();
        errorObject.put("call", ".error.NOT_AUTHOR.field-thing_id");
        responseNotAuthor = new RestResponse("test", errorObject, mock(HttpResponse.class));

        errorObject = new JSONObject();
        errorObject.put("call", ".error.USER_REQUIRED");
        responseUserRequired = new RestResponse("test", errorObject, mock(HttpResponse.class));

        errorObject = new JSONObject();
        responseEmpty = new RestResponse("", errorObject, mock(HttpResponse.class));
    }

    /**
     * Test deleting a thing.
     */
    @Test
    public void testDelete() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post(
                "id=" + "fullname" + "&uh=" + user.getModhash(),
                ApiEndpointUtils.DELETE, user.getCookie()))
                .thenReturn(responseEmpty);

        // Delete thing succesfully
        assertEquals(submitAction.delete("fullname"), true);
    }

    /**
     * Test commenting.
     */
    @Test
    public void testComment() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("thing_id=" + "fullname" + "&text=" + "text"
                + "&uh=" + user.getModhash(),
                ApiEndpointUtils.COMMENT, user.getCookie()))
                .thenReturn(responseEmpty);

        // Comment succesfully
        assertEquals(submitAction.comment("fullname", "text"), true);
    }

    /**
     * Test commenting without a logged in user.
     */
    @Test
    public void testCommentUserRequired() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("thing_id=" + "fullname" + "&text=" + "text"
                + "&uh=" + user.getModhash(),
                ApiEndpointUtils.COMMENT, user.getCookie()))
                .thenReturn(responseUserRequired);
        // Save System.err.println output to variable
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        // Attempt to comment
        boolean res = submitAction.comment("fullname", "text");
        assertEquals(errorOutput.toString().contains("User submission failed: please login first."), true);
        assertEquals(res, false);
    }

    /**
     * Test creating a Live Thread without a logged in user.
     */
    @Test
    public void testCreateLiveUserRequired() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("api_type=json&title=" + "title" + "&description="
                + "description" + "&uh=" + user.getModhash(),
                ApiEndpointUtils.LIVE_THREAD_CREATE,
                user.getCookie()))
                .thenReturn(responseUserRequired);
        // Save System.err.println output to variable
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        // Fail the creation of a live thread
        boolean res = submitAction.createLive("title", "description");
        assertEquals(errorOutput.toString().contains("User submission failed: please login first."), true);
        assertEquals(res, false);
    }

    /**
     * Test succesfully creating a Live Thread.
     */
    @Test
    public void testCreateLive() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("api_type=json&title=" + "title" + "&description="
                + "description" + "&uh=" + user.getModhash(),
                ApiEndpointUtils.LIVE_THREAD_CREATE,
                user.getCookie()))
                .thenReturn(responseEmpty);

        // Test succesfull creation of a live thread
        boolean res = submitAction.createLive("title", "description");
        assertEquals(res, true);
    }

    /**
     * Test posting an update to a Live Thread without a logged in user.
     */
    @Test
    public void testUpdateLiveUserRequired() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("api_type=json&body=" + "message" + "&uh=" + user.getModhash(),
                String.format(ApiEndpointUtils.LIVE_THREAD_UPDATE, "liveThread"),
                user.getCookie()))
                .thenReturn(responseUserRequired);
        // Save System.err.println output to variable
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        // Attempt the update
        boolean res = submitAction.updateLive("liveThread", "message");
        assertEquals(errorOutput.toString().contains("User submission failed: please login first."), true);
        assertEquals(res, false);
    }

    /**
     * Test succesfully posting an update to a Live Thread.
     */
    @Test
    public void testUpdateLive() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post("api_type=json&body=" + "message" + "&uh=" + user.getModhash(),
                String.format(ApiEndpointUtils.LIVE_THREAD_UPDATE, "liveThread"),
                user.getCookie()))
                .thenReturn(responseEmpty);

        // Test updating a live thread
        boolean res = submitAction.updateLive("liveThread", "message");
        assertEquals(res, true);
    }

    /**
     * Test the succesfull submission of a link.
     */
    @Test
    public void testSubmitLink() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        String params = "title=" + "title" + "&url=" + "http://link" + "&sr="
                + "subreddit" + "&kind=" + "link" + "&uh=" + user.getModhash()
                + "&iden=" + "captchaid" + "&captcha=" + "solution";
        when(restClient.post(params, ApiEndpointUtils.USER_SUBMIT,
                user.getCookie()))
                .thenReturn(responseEmpty);

        // Test submitting a link
        boolean res = submitAction.submitLink("title", "http://link", "subreddit",
                "captchaid", "solution");
        assertEquals(res, true);
    }

    /**
     * Test the succesfull submission of a self post.
     */
    @Test
    public void testSubmitSelfPost() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        String params = "title=" + "title" + "&text=" + "text" + "&sr="
                + "subreddit" + "&kind=" + "self" + "&uh=" + user.getModhash()
                + "&iden=" + "captchaid" + "&captcha=" + "solution";
        when(restClient.post(params, ApiEndpointUtils.USER_SUBMIT,
                user.getCookie()))
                .thenReturn(responseEmpty);

        // Test submitting a SelfPost
        boolean res = submitAction.submitSelfPost("title", "text", "subreddit",
                "captchaid", "solution");
        assertEquals(res, true);
    }

    /**
     * Test submitting without a logged in user.
     */
    @Test
    public void testSubmitUserRequired() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        when(user.getCookie()).thenReturn(COOKIE);
        String params = "title=" + "title" + "&text=" + "text" + "&sr="
                + "subreddit" + "&kind=" + "self" + "&uh=" + user.getModhash()
                + "&iden=" + "captchaid" + "&captcha=" + "solution";
        when(restClient.post(params, ApiEndpointUtils.USER_SUBMIT,
                user.getCookie()))
                .thenReturn(responseUserRequired);

        // Save System.err.println output to variable
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        // Fail submitting a SelfPost
        boolean res = submitAction.submitSelfPost("title", "text", "subreddit",
                "captchaid", "solution");
        assertEquals(errorOutput.toString().contains("User submission failed: please login first."), true);
        assertEquals(res, false);
    }

    /**
     * Test the succesfull edition of a thing.
     */
    @Test
    public void testEditUserText() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        String apiParams = "thing_id=" + "fullname" + "&text=" + "text"
                + "&uh=" + user.getModhash();
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post(apiParams,
                ApiEndpointUtils.EDITUSERTEXT, user.getCookie()))
                .thenReturn(responseEmpty);

        // Test succesfull edit
        boolean res = submitAction.editUserText("fullname", "text");
        assertEquals(res, true);

    }

    /**
     * Test editing a thing that is not the user's.
     */
    @Test
    public void testEditUserTextNotAuthor() {
        // Stub REST client methods
        when(user.getModhash()).thenReturn(MODHASH);
        String apiParams = "thing_id=" + "fullname" + "&text=" + "text"
                + "&uh=" + user.getModhash();
        when(user.getCookie()).thenReturn(COOKIE);
        when(restClient.post(apiParams,
                ApiEndpointUtils.EDITUSERTEXT, user.getCookie()))
                .thenReturn(responseNotAuthor);

        // Save System.err.println output to variable
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        // Attempt the edit
        boolean res = submitAction.editUserText("fullname", "text");
        assertEquals(errorOutput.toString().contains("User is not the author of this thing."), true);
        assertEquals(res, false);

    }

}
