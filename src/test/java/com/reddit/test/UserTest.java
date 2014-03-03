package com.reddit.test;

import static org.junit.Assert.*;

import com.reddit.utils.TestUtils;
import im.goel.jreddit.user.User;

import org.junit.Test;


/**
 * Class for testing User-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class UserTest {

	@Test
	public void test() {
		User user = TestUtils.createAndConnectUser();

        assertNotNull(user);
		assertNotNull("The user's modhash should never be null", user.getModhash());
	}
}