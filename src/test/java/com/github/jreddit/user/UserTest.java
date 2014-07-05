package com.github.jreddit.user;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.entity.User;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.jreddit.testsupport.JsonHelpers.*;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Class for testing User-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class UserTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COOKIE = "cookie";
    public static final String MOD_HASH = "modHash";
    public static final String UNKNOWN_USERNAME = "unknownUsername";
    private User underTest;
    private RestClient restClient;
    private Response response;

    @Before
    public void initUser() throws Exception {
        Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);

        restClient = mock(RestClient.class);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);
        underTest = new User(restClient, USERNAME, PASSWORD);
        underTest.connect();
    }

    /*@Test
    public void getSubscriptions() {
        response = new UtilResponse(null, subredditListingForFunny(), 200);
        when(restClient.get(ApiEndpointUtils.USER_GET_SUBSCRIBED, COOKIE)).thenReturn(response);

        List<Subreddit> subreddits = underTest.getSubscribed();

        assertNotNull(subreddits);
        assertTrue(subreddits.size() == 1);
    }

    @Test
    public void getUserInformationSuccessfully() {
        response = new UtilResponse(null, generateUserInfo(USERNAME), 200);
        when(restClient.get(ApiEndpointUtils.USER_INFO, COOKIE)).thenReturn(response);

        UserInfo info = underTest.getUserInformation();
        assertNotNull(info);
    }

    @Test
    public void getUserInformationForNotLoggedInUser() {
        User newUser = new User(restClient, "username", "password");
        assertNull(newUser.getUserInformation());
    }

    @Test
    public void getAboutUserSuccessfully() {
        response = new UtilResponse(null, generateUserAbout(USERNAME), 200);
        when(restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, USERNAME), null)).thenReturn(response);

        UserInfo userInfo = underTest.about(USERNAME);
        assertNotNull(userInfo);
        assertEquals(userInfo.getName(), USERNAME);
    }

    @Test
    public void getAboutUserForUnknownUser() {
        response = new UtilResponse(null, new JSONObject(singletonMap("error", 404)), 404);
        when(restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, UNKNOWN_USERNAME), null)).thenReturn(response);

        UserInfo userInfo = underTest.about(UNKNOWN_USERNAME);
        assertNull(userInfo);
    }*/
}