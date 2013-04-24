package message;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.omrlnr.jreddit.user.User;
import com.omrlnr.jreddit.utils.Utils;

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
	public List<Message> createList(User user, int maxMessages, String method) {
		List<Message> messages = null;

		try {
			JSONObject object = (JSONObject) Utils.get("", new URL(
					"http://www.reddit.com/message/" + method + ".json"), 
					user.getCookie());
			JSONObject data = (JSONObject) object.get("data");
			messages = buildList((JSONArray) data.get("children"), maxMessages);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}

	/**
	 * Builds a list of Messages from the passed array of children.
	 * @author Karan Goel
	 */
	private static List<Message> buildList(JSONArray children, int maxMessages) {
		List<Message> messages = new ArrayList<Message>(10000);
		JSONObject obj;
		Message m;

		if (maxMessages < 0 || maxMessages > children.size()) {
			maxMessages = children.size();
		}
		for (int i = 0; i < maxMessages; i++) {
			obj = (JSONObject) children.get(i);
			obj = (JSONObject) obj.get("data");
			m = new Message();
			m.setBody(obj.get("body").toString());
			m.setWas_comment(Boolean.valueOf(obj.get("was_comment").toString()));
			m.setAuthor(obj.get("author").toString());
			m.setCreated(obj.get("created").toString());
			m.setCreatedUTC(obj.get("created_utc").toString());
			m.setSubject(obj.get("subject").toString());
			//TODO: m.setSubreddit(obj.get("subreddit").toString());
			m.setContext(obj.get("context").toString());
			m.setId(obj.get("id").toString());
			m.setSubject(obj.get("subject").toString());
			messages.add(m);
		}
		return messages;
	}

}
