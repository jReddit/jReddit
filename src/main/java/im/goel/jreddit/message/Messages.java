package im.goel.jreddit.message;

import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * @author Karan Goel
 *
 */
public class Messages {

	/**
	 * Returns all unread messages in user's inbox.
	 * @author Karan Goel
	 */
	public List<Message> unread(User user) {
		return createList(user, -1, "unread");
	}

	/**
	 * Returns the specified number of messages in user's inbox.
	 * Return all messages if maxMessages = -1.
	 * @author Karan Goel
	 */
	public List<Message> inbox(User user, int maxMessages) {
		return createList(user, maxMessages, "inbox");
	}

	/**
	 * Returns the sent messages for a user.
	 * @param user
	 * @param maxMessages
	 * @return List of sent messages
	 */
	public List<Message> sent(User user, int maxMessages) {
		return createList(user, maxMessages, "sent");
	}

	/**
	 * Builds a list of Messages based on passed parameters.
	 * @param user
	 * @param maxMessages
	 * @param method
	 * @return list of messages based on passed method
	 * @author Karan Goel
	 */
	@SuppressWarnings("unchecked")
	public List<Message> createList(User user, int maxMessages, String method) {
		List<Message> messages = null;

		try {
			JSONObject object = (JSONObject) Utils.get(new URL(
					"http://www.reddit.com/message/" + method + ".json"), 
					user.getCookie());
			JSONObject data = (JSONObject) object.get("data");
			messages = (List) buildList((JSONArray) data.get("children"), maxMessages);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}

	/**
	 * Builds a list of Messages from the passed array of children.
	 * @author Karan Goel
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

	/**
	 * Composes a messages based on passed text and send it to 
	 * the passed user name.
	 * Make sure you call Captcha.new_captcha to generate a im.goel.jreddit.captcha
	 * and pass it's iden and solution.
	 * @param text
	 */
	public void compose(User user, String to, String subject, String text, String iden, 
			String captcha) {
		JSONObject object = null;

        try {
			object = Utils.post("im.goel.jreddit.captcha=" + captcha + "&iden=" +iden + 
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


}
