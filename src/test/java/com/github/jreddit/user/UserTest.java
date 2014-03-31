package com.github.jreddit.user;

import com.github.jreddit.model.json.response.RedditListing;
import com.github.jreddit.model.json.response.SubmissionListingItem;
import com.github.jreddit.model.json.response.UserInfo;
import com.github.jreddit.model.json.response.UserLogin;
import com.github.jreddit.utils.restclient.RedditServices;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.github.jreddit.testsupport.json.builders.UserLoginBuilder.userLogin;
import static com.github.jreddit.utils.Sort.NEW;
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
    private User underTest;
    private RedditServices redditServices;

    @Before
    public void setUp() {
        redditServices = mock(RedditServices.class);
        underTest = new User(redditServices, USERNAME, PASSWORD);
    }

    @Test
    public void initUser() throws Exception {
        UserLogin userLogin = userLogin().withCookie(COOKIE).withModhash(MOD_HASH).build();
        when(redditServices.userLogin(USERNAME, PASSWORD)).thenReturn(userLogin);

        underTest.connect();

        assertTrue(underTest.getCookie().equals(COOKIE));
        assertTrue(underTest.getModhash().equals(MOD_HASH));
    }

    @Test
    public void getUserInformationSuccessfully() throws IOException, URISyntaxException {
        UserLogin userLogin = userLogin().withCookie(COOKIE).withModhash(MOD_HASH).build();
        when(redditServices.userLogin(USERNAME, PASSWORD)).thenReturn(userLogin);

        underTest.connect();
        when(redditServices.getUserInfo()).thenReturn(new UserInfo());

        assertNotNull(underTest.getUserInformation());
    }

    @Test
    public void getUserInformationForNotLoggedInUser() throws IOException, URISyntaxException {
        assertNull(underTest.getUserInformation());
    }

    @Test
    public void getUserInformationWhenCookieNotSet() throws Exception {
        UserLogin userLogin = userLogin().withCookie(null).withModhash(MOD_HASH).build();
        when(redditServices.userLogin(USERNAME, PASSWORD)).thenReturn(userLogin);

        underTest.connect();

        assertNull(underTest.getUserInformation());
    }

    @Test
    public void getSubmissionsWithNullWhereArg() throws IOException, URISyntaxException {
        assertNull(underTest.getSubmissions(null, null, null));
    }

    @Test
    public void getSubmissionsWithBadWhereArg() throws IOException, URISyntaxException {
        assertNull(underTest.getSubmissions(null, "foo", null));
    }

    @Test
    public void getSubmissions() throws IOException, URISyntaxException {
        RedditListing<SubmissionListingItem> listing = new RedditListing<SubmissionListingItem>();
        when(redditServices.getUserSubmissions(USERNAME, "submitted", NEW)).thenReturn(listing);

        assertNotNull(underTest.getSubmissions(USERNAME, "submitted", NEW));
    }
}