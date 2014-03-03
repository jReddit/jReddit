package com.reddit.utils;

import im.goel.jreddit.user.User;

/**
 * Utility class for Tests. Holds methods used throughout different test classes
 *
 * @author Raul Rene Lepsa
 */
public class TestUtils {

    public static final String TEST_USERNAME = "jReddittest";
    public static final String TEST_PASSWORD = "jReddittest";

    /**
     * Creates a new user with the Test Credentials
     *
     * @return the new User object
     */
    public static User createUser() {
        return new User(TEST_USERNAME, TEST_PASSWORD);
    }

    /**
     * Creates and attempts to connect a user using the Test Credentials
     *
     * @return User object if created and connected successfully, null otherwise
     */
    public static User createAndConnectUser() {
        User user = null;

        try {
            user = createUser();
            user.connect();
        } catch (Exception e) {
            System.out.println("Error connecting User");
        }

        return user;
    }
}
