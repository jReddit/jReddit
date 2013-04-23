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
	 * Returns the unread messages in user's inbox.
	 * @author Karan Goel
	 */
	public List<Message> unread(User user) {
		List<Message> unread = null;

		try {
			JSONObject object = (JSONObject) Utils.get("", new URL(
					"http://www.reddit.com/message/unread.json"), 
					user.getCookie());
			JSONObject data = (JSONObject) object.get("data");
			unread = buildList((JSONArray) data.get("children"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return unread;
	}

	/**
	 * Builds a list of Messages from the passed array of children.
	 * @author Karan Goel
	 */
	private static List<Message> buildList(JSONArray children) {
		List<Message> messages = new ArrayList<Message>(10000);
		JSONObject obj;
		Message m;

		for (int i = 0; i < children.size(); i++) {
			obj = (JSONObject) children.get(i);
			obj = (JSONObject) obj.get("data");
			m = new Message();
			m.setBody(obj.get("body").toString());
			m.setWas_comment(Boolean.valueOf(obj.get("was_comment").toString()));
			m.setAuthor(obj.get("author").toString());
			m.setCreated(obj.get("created").toString());
			m.setCreatedUTC(obj.get("created_utc").toString());
			m.setSubject(obj.get("subject").toString());
			//m.setSubreddit(obj.get("subreddit").toString());
			m.setContext(obj.get("context").toString());
			m.setId(obj.get("id").toString());
			m.setSubject(obj.get("subject").toString());
			messages.add(m);
		}
		return messages;
	}

}
