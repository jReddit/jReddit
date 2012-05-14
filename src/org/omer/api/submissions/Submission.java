package org.omer.api.submissions;

import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.omer.api.Thing;
import org.omer.api.user.User;
import org.omer.api.utils.Utils;

/**
 * This class represents a vote on a link submission on reddit.
 * 
 * @author <a href="https://www.github.com/OmerE">Omer Elnour</a>
 */
public class Submission extends Thing {
	/**
	 * This is the user that will vote on a submission.
	 */
	private User user;

	public Submission(User user, String fullName) {
		if (fullName.startsWith("t3_"))
			fullName = fullName.replaceFirst("t3_", "");

		this.user = user;
		this.fullName = "t3_" + fullName;
	}

	/**
	 * This function comments on this submission saying the comment specified in
	 * <code>text</code> (CAN INCLUDE MARKDOWN)
	 * 
	 * @param text
	 *            The text to comment
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public void comment(String text) throws IOException, ParseException {
		JSONObject object = Utils.postAndGetJSON("thing_id=" + fullName + "&text="
				+ text + "&uh=" + user.getModhash(), new URL(
				"http://www.reddit.com/api/comment"), user.getCookie());

		if (object.toJSONString().contains(".error.USER_REQUIRED"))
			throw new InvalidCookieException("Cookie not present");
		else
			System.out.println("Commented on thread id " + fullName
					+ " saying: \"" + text + "\"");
	}

	/**
	 * This function upvotes this submission.
	 * 
	 * @throws IOException
	 *             If connection failed
	 * @throws ParseException
	 *             If JSON parsing failed
	 */
	public void upVote() throws IOException, ParseException {
		JSONObject object = voteResponse(1);
		if (!(object.toJSONString().length() > 2))
			System.out.println("Successful upboat!");
		else
			System.out.println(object.toJSONString());
	}

	/**
	 * This function rescinds, or normalizes this submission. <br />
	 * (i.e Removes a downvote or upvote)
	 * 
	 * @throws IOException
	 *             If connection failed
	 * @throws ParseException
	 *             If JSON parsing failed
	 */
	public void rescind() throws IOException, ParseException {
		JSONObject object = voteResponse(0);
		if (!(object.toJSONString().length() > 2))
			System.out.println("Successful rescind!");
		else
			System.out.println(object.toJSONString());
	}

	/**
	 * This function downvotes this submission.
	 * 
	 * @throws IOException
	 *             If connection failed
	 * @throws ParseException
	 *             If JSON parsing failed
	 */
	public void downVote() throws IOException, ParseException {
		JSONObject object = voteResponse(-1);
		if (!(object.toJSONString().length() > 2))
			System.out.println("Successful downboat!");
		else
			System.out.println(object.toJSONString());
	}

	public User getUser() {
		return user;
	}

	public String getFullName() {
		return fullName;
	}

	private JSONObject voteResponse(int dir) throws IOException, ParseException {
		return Utils.postAndGetJSON(
				"id=" + fullName + "&dir=" + dir + "&uh=" + user.getModhash(),
				new URL("http://www.reddit.com/api/vote"), user.getCookie());
	}
}