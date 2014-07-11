package com.github.jreddit.message;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.entity.Kind;
import com.github.jreddit.entity.User;
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
     * Get the list of messages of a certain type for a user
     *
     * @param user        Reddit user for which to check the inbox
     * @param maxMessages number of messages to fetch. If it is set to <code>ALL_MESSAGES</code>, it will bring all messages
     * @param messageType <code>MessageType</code> instance, that determines the type of the message
     * @return list of messages based on passed method
     */
    public List<Message> getMessages(User user, int maxMessages, MessageType messageType) {

        List<Message> messages = null;

        try {
            JSONObject object = (JSONObject)  restClient.get(String.format(ApiEndpointUtils.MESSAGE_GET, messageType.getValue()), user.getCookie()).getResponseObject();
            JSONObject data = (JSONObject) object.get("data");
            messages = buildList((JSONArray) data.get("children"), maxMessages);

        } catch (Exception e) {
            System.err.println("Error retrieving messages of type " + messageType);
        }
        return messages;
    }

    /**
     * Compose and send a message. Requires an authenticated user and a Captcha challenge.
     * A new captcha can be generated using the <code>newCaptcha()</code> method of the <code>Captcha</code> class.
     *
     * @param user       Reddit user for which to check the inbox
     * @param to         recipient of the message (has to be an existing user)
     * @param subject    Subject of the message (no longer than 100 characters)
     * @param text       body of the message
     * @param iden       identifier of the Captcha challenge
     * @param captchaTry The user's response to the Captcha challenge
     * @return true if the message was sent successfully, false otherwise
     */
    public boolean compose(User user, String to, String subject, String text, String iden, String captchaTry) {

        if (subject.length() > 100) {
            System.err.println("Subject cannot have more than 100 characters");
            return false;
        }

        try {
            JSONObject object = (JSONObject) restClient.post("captcha=" + captchaTry + "&iden=" + iden +
                    "&subject=" + subject + "&text=" + text + "&to=" + to +
                    "&uh=" + user.getModhash(),
                    ApiEndpointUtils.MESSAGE_COMPOSE, user.getCookie()).getResponseObject();

            if (object.toJSONString().contains(".error.USER_REQUIRED")) {
                System.err.println("Please login first.");
            } else if (object.toJSONString().contains(".error.RATELIMIT.field-ratelimit")) {
                System.err.println("You are doing that too much.");
            } else if (object.toJSONString().contains(".error.BAD_CAPTCHA.field-captcha")) {
                System.err.println("Invalid captcha submitted.");
            } else {
                System.out.println(((JSONArray) ((JSONArray) ((JSONArray) object.get("jquery")).get(14)).get(3)).get(0));   // prints a message confirming delivery
                return true;
            }

        } catch (Exception e) {
            System.err.println("Error sending message to " + to);
        }

        return false;
    }

    /**
     * Mark a message as read
     *
     * @param fullName Full name of the <code>Message</code> to mark as read. The full name is a combination of the
     *                 <code>Kind</code> and ID of the message
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
     *                 <code>Kind</code> and ID of the message
     * @param user     Reddit user that marks the message as unread
     */
    public void unreadMessage(String fullName, User user) {
        try {
            restClient.post("id=" + fullName + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
        } catch (Exception e) {
            System.err.println("Error marking message: " + fullName + " as unread");
        }
    }

    /**
     * Builds a list of Messages from the passed array of children.
     */
    private List<Message> buildList(JSONArray children, int maxMessages) {
        List<Message> messages = new ArrayList<Message>();
        JSONObject obj;

        if (maxMessages < 0 || maxMessages > children.size()) {
            maxMessages = children.size();
        }
        for (int i = 0; i < maxMessages; i++) {
            obj = (JSONObject) children.get(i);

            // If the kind of the object is a MESSAGE
            if (obj.get("kind").toString().startsWith(Kind.MESSAGE.value())) {
                obj = (JSONObject) obj.get("data");
                messages.add(MessageMapper.mapMessage(obj));

                // Else it is a comment
            } else {
                obj = (JSONObject) obj.get("data");
                MessageComment messageComment = new MessageComment();
                messageComment.setBody(obj.get("body").toString());
                messageComment.setLink_title(obj.get("link_title").toString());
                messageComment.setComment(Boolean.valueOf(obj.get("was_comment").toString()));
                messageComment.setFullName(obj.get("name").toString());
                messageComment.setAuthor(obj.get("author").toString());
                messageComment.setCreated(obj.get("created").toString());
                messageComment.setRecipient(obj.get("dest").toString());
                messageComment.setAuthor(obj.get("author").toString());
                messageComment.setCreatedUTC(obj.get("created_utc").toString());
                messageComment.setBodyHtml(obj.get("body_html").toString());
                messageComment.setSubject(obj.get("subject").toString());
                messageComment.setSubreddit(obj.get("subreddit").toString());
                messageComment.setContext(obj.get("context").toString());
                messageComment.setId(obj.get("id").toString());
                messageComment.setSubject(obj.get("subject").toString());
                messages.add(messageComment);
            }
        }

        return messages;
    }

}
