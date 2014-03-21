package com.github.jreddit.test;

import com.github.jreddit.subreddit.Subreddit;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.TestUtils;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Class for testing User-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class UserTest {

    private static User user;

    @BeforeClass
    public static void initUser() {
        try {
            user = TestUtils.createAndConnectUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectUser() {
        assertNotNull(user);
        assertNotNull("The user's modhash should never be null", user.getModhash());
    }

    @Test
    public void testGetSubscriptions() {
        List<Subreddit> subreddits = user.getSubscribed();

        assertNotNull(subreddits);

        for (Subreddit subreddit : subreddits) {
            System.out.println(subreddit.getName());
        }
    }

    @Test
    public void testGetUserInformation() {
        // Test with connected user
        JSONObject info = user.getUserInformation();
        assertNotNull(info);

        // Test with non-existent user so that we check it fails
        User newUser = new User("username", "password");
        info = newUser.getUserInformation();
        assertNull(info);
    }
}