package im.goel.jreddit.message;

import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Messaging functionality
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class Messages {

    public static final int ALL_MESSAGES = -1;

    /**
     * Get the list of messages of a certain type for a user
     *
     * @param user        Reddit user for which to check the inbox
     * @param maxMessages number of messages to fetch. If it is set to <code>ALL_MESSAGES</code>, it will bring all messages
     * @param messageType <code>MessageType</code> instance, that determines the type of the message
     *
     * @return list of messages based on passed method
     */
    @SuppressWarnings("unchecked")
    public List<Message> getMessages(User user, int maxMessages, MessageType messageType) {

        List<Message> messages = null;

        try {
            JSONObject object = (JSONObject) Utils.get(new URL(
                    "http://www.reddit.com/message/" + messageType.getValue() + ".json"),
                    user.getCookie());
            JSONObject data = (JSONObject) object.get("data");
            messages = (List) buildList((JSONArray) data.get("children"), maxMessages);

        } catch (Exception e) {
            System.out.println("Error retrieving messages of type " + messageType);
        }
        return messages;
    }


    /**
     * Composes a messages based on passed text and send it to
     * the passed user name.
     * Make sure you call Captcha.newCaptcha to generate a im.goel.jreddit.captcha
     * and pass it's iden and solution.
     *
     * @param text
     */
    public void compose(User user, String to, String subject, String text, String iden, String captcha) {
        JSONObject object = null;

        try {
            object = Utils.post("im.goel.jreddit.captcha=" + captcha + "&iden=" + iden +
                    "&subject=" + subject + "&text=" + text + "&to=" + to +
                    "&uh=" + user.getModhash(),
                    new URL("http://www.reddit.com/api/compose"), user.getCookie());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("Please login first.");
        } else if (object.toJSONString().contains(
                ".error.RATELIMIT.field-ratelimit")) {
            System.err.println("You are doing that too much.");
        } else if (object.toJSONString().contains(
                ".error.BAD_CAPTCHA.field-im.goel.jreddit.captcha")) {
            System.err.println("Invalid im.goel.jreddit.captcha submitted.");
        } else {
            System.out.println(((JSONArray) ((JSONArray) ((JSONArray) object.get("jquery")).get(14)).get(3)).get(0));
        }
    }

    /**
     * Builds a list of Messages from the passed array of children.
     */
    private static List<Object> buildList(JSONArray children, int maxMessages) {
        List<Object> messages = new ArrayList<Object>(10000);
        JSONObject obj;

        if (maxMessages < 0 || maxMessages > children.size()) {
            maxMessages = children.size();
        }
        for (int i = 0; i < maxMessages; i++) {
            obj = (JSONObject) children.get(i);
            if (obj.get("kind").toString().startsWith("t4")) { // It's a message
                obj = (JSONObject) obj.get("data");
                Message m = new Message();
                m.setBody(obj.get("body").toString());
                m.setWas_comment(Boolean.valueOf(obj.get("was_comment").toString()));
                m.setName(obj.get("name").toString());
                m.setAuthor(obj.get("author").toString());
                m.setCreated(obj.get("created").toString());
                m.setDest(obj.get("dest").toString());
                m.setAuthor(obj.get("author").toString());
                m.setCreatedUTC(obj.get("created_utc").toString());
                m.setBody_html(obj.get("body_html").toString());
                m.setSubject(obj.get("subject").toString());
                m.setContext(obj.get("context").toString());
                m.setId(obj.get("id").toString());
                m.setSubject(obj.get("subject").toString());
                messages.add(m);
            } else { // It's a comment/reply to a post
                obj = (JSONObject) obj.get("data");
                Comment m = new Comment();
                m.setBody(obj.get("body").toString());
                m.setLink_title(obj.get("link_title").toString());
                m.setWas_comment(Boolean.valueOf(obj.get("was_comment").toString()));
                m.setName(obj.get("name").toString());
                m.setAuthor(obj.get("author").toString());
                m.setCreated(obj.get("created").toString());
                m.setDest(obj.get("dest").toString());
                m.setAuthor(obj.get("author").toString());
                m.setCreatedUTC(obj.get("created_utc").toString());
                m.setBody_html(obj.get("body_html").toString());
                m.setSubject(obj.get("subject").toString());
                m.setSubreddit(obj.get("subreddit").toString());
                m.setContext(obj.get("context").toString());
                m.setId(obj.get("id").toString());
                m.setSubject(obj.get("subject").toString());
                messages.add(m);
            }
        }

        return messages;
    }

}
