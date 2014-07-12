package com.github.jreddit.action;

import static com.github.jreddit.testsupport.JsonHelpers.createUserAbout;
import static com.github.jreddit.testsupport.JsonHelpers.createUserInfo;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.github.jreddit.entity.User;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Test case for profile related actions.
 * 
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public class ProfileActionsTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COOKIE = "cookie";
    public static final String MOD_HASH = "modHash";
    public static final String UNKNOWN_USERNAME = "unknownUsername";
    private User user;
    private ProfileActions subject;
    private RestClient restClient;
    private Response response;
    
    /**
     * Mock depended classes and stub if necessary.
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        restClient = mock(RestClient.class);
        user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        when(user.getModhash()).thenReturn(MOD_HASH);
        subject = new ProfileActions(restClient, user);
    }
    
    /**
     * Succesfully get user information.
     */
    @Test
    public void getUserInformationSuccessfully() {
        response = new UtilResponse(null, createUserInfo(USERNAME), 200);
        when(restClient.get(ApiEndpointUtils.USER_INFO, COOKIE)).thenReturn(response);

        UserInfo info = subject.getUserInformation();
        assertNotNull(info);
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void getAboutUserSuccessfully() {
        response = new UtilResponse(null, createUserAbout(USERNAME), 200);
        when(restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, USERNAME), null)).thenReturn(response);

        UserInfo userInfo = subject.about(USERNAME);
        assertNotNull(userInfo);
        assertEquals(userInfo.getName(), USERNAME);
    }

    /**
     * Get the information about a user for a unknown user (should return null).
     */
    @Test
    public void getAboutUserForUnknownUser() {
        response = new UtilResponse(null, new JSONObject(singletonMap("error", 404)), 404);
        when(restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, UNKNOWN_USERNAME), null)).thenReturn(response);

        UserInfo userInfo = subject.about(UNKNOWN_USERNAME);
        assertNull(userInfo);
    }
    
}
