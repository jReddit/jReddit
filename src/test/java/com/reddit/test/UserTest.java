package com.reddit.test;

import static org.junit.Assert.*;

import com.reddit.utils.TestUtils;
import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.user.User;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Class for testing User-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class UserTest {

    private static User user;

    @Before
    public void setUp() {
        user = TestUtils.createAndConnectUser();
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

        for (Subreddit subreddit: subreddits) {
            System.out.println(subreddit.getName());
        }
    }
}