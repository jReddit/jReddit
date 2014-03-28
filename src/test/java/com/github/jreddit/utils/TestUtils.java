package com.github.jreddit.utils;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Utility class for Tests. Holds methods used throughout different test classes
 *
 * @author Raul Rene Lepsa
 */
public class TestUtils {

    private static final String TEST_USERNAME = "hvfcjhgrtc";
    private static final String TEST_PASSWORD = "hvfcjhgrtc";
    private static final RestClient restClient = new HttpRestClient();

    /**
     * Creates a new user with the Test Credentials
     *
     * @return the new User object
     */
    public static User createUser() {
        return new User(restClient, TEST_USERNAME, TEST_PASSWORD);
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
