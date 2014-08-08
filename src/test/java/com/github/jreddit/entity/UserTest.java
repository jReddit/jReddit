package com.github.jreddit.entity;

import static com.github.jreddit.testsupport.JsonHelpers.userLoginResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * Class for testing User-related methods
 * 
 * @author Simon Kassing
 */
public class UserTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COOKIE = "cookie";
    public static final String MOD_HASH = "modHash";
    public static final String UNKNOWN_USERNAME = "unknownUsername";
    private User subject;
    private RestClient restClient;
	
    /**
     * Setup mock objects and if necessary stub them.
     */
    @Before
    public void setup() {
    	restClient = mock(RestClient.class);
        subject = new User(restClient, USERNAME, PASSWORD);
    }
    
    /**
     * Test whether the correct username is stored.
     */
    @Test
    public void testCorrectUsername() {
    	assertEquals(subject.getUsername(), USERNAME);
    }
    
    /**
     * Test connecting the user.
     * @throws Exception If connect failed
     */
    @Test
    public void testConnect() throws Exception {
    	
    	// Stub response
    	Response loginResponse = new UtilResponse(null, userLoginResponse(COOKIE, MOD_HASH), 200);
        when(restClient.post("api_type=json&user=" + USERNAME + "&passwd=" + PASSWORD, String.format(ApiEndpointUtils.USER_LOGIN, USERNAME), null)).thenReturn(loginResponse);
        
        // Connect user
        subject.connect();
        
        // Test that the correct cookie and mod hash have been set
        assertEquals(subject.getCookie(), COOKIE);
        assertEquals(subject.getModhash(), MOD_HASH);
        
    }
    
}