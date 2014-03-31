package com.github.jreddit.message;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Class for testing Message-related methods
 *
 * @author Raul Rene Lepsa
 */
public class MessageTest {

    private static final String COOKIE = "cookie";
    private static final String MOD_HASH = "modHash";

    private User user;
    private RestClient restClient;
    private Messages underTest;

    @Before
    public void setUp() {
        user = mock(User.class);
        restClient = mock(RestClient.class);
        underTest = new Messages(restClient);

        when(user.getCookie()).thenReturn(COOKIE);
        when(user.getModhash()).thenReturn(MOD_HASH);
    }

    @Test
    public void markMessageAsRead() {
        underTest.readMessage("messageName", user);

        verify(restClient).post("id=" + "messageName" + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
    }

    @Test
    public void markMessageAsUnread() {
        underTest.readMessage("messageName", user);

        verify(restClient).post("id=" + "messageName" + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
    }
}
