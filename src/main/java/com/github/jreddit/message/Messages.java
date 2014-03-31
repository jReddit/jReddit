package com.github.jreddit.message;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * Messaging functionality
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 */
public class Messages {

    public static final int ALL_MESSAGES = -1;
    private final RestClient restClient;

    public Messages(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Mark a message as read
     *
     * @param fullName Full name of the <code>Message</code> to mark as read. The full name is a combination of the
     *                 <code>TypePrefix</code> and ID of the message
     * @param user     Reddit user that reads the message
     */
    public void readMessage(String fullName, User user) {
        try {
            restClient.post("id=" + fullName + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
        } catch (Exception e) {
            System.err.println("Error reading message: " + fullName);
        }
    }

    /**
     * Mark a message as unread
     *
     * @param fullName Full name of the <code>Message</code> to mark as unread. The full name is a combination of the
     *                 <code>TypePrefix</code> and ID of the message
     * @param user     Reddit user that marks the message as unread
     */
    public void unreadMessage(String fullName, User user) {
        try {
            restClient.post("id=" + fullName + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
        } catch (Exception e) {
            System.err.println("Error marking message: " + fullName + " as unread");
        }
    }
}
