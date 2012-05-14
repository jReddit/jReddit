package org.omer.api.links;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omer.api.InvalidCookieException;
import org.omer.api.Thing;
import org.omer.api.user.User;

/**
 * This class represents a vote on a link submission on reddit.
 * 
 * @author <a href="https://www.github.com/OmerE">Omer Elnour</a>
 */
public class Link extends Thing {
	/**
	 * This is the user that will vote on a submission.
	 */
	private User user;

	public Link(User user, String fullName) {
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
		JSONObject object = commentResponse(text);

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
		String apiParams = "id=" + fullName + "&dir=" + dir + "&uh="
				+ user.getModhash();
		URL voteURL = new URL("http://www.reddit.com/api/vote");
		HttpURLConnection connection = (HttpURLConnection) voteURL
				.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length",
				String.valueOf(apiParams.length()));
		connection.setRequestProperty("cookie",
				"reddit_session=" + user.getCookie());
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(apiParams);
		wr.flush();
		wr.close();

		JSONParser parser = new JSONParser();
		Object object = parser.parse(new BufferedReader(new InputStreamReader(
				connection.getInputStream())).readLine());
		JSONObject jsonObject = (JSONObject) object;

		return jsonObject;
	}

	private JSONObject commentResponse(String text) throws IOException,
			ParseException {
		String apiParams = "thing_id=" + fullName + "&text=" + text + "&uh="
				+ user.getModhash();
		URL voteURL = new URL("http://www.reddit.com/api/comment");
		HttpURLConnection connection = (HttpURLConnection) voteURL
				.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length",
				String.valueOf(apiParams.length()));
		connection.setRequestProperty("cookie",
				"reddit_session=" + user.getCookie());
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(apiParams);
		wr.flush();
		wr.close();

		JSONParser parser = new JSONParser();
		Object object = parser.parse(new BufferedReader(new InputStreamReader(
				connection.getInputStream())).readLine());
		JSONObject jsonObject = (JSONObject) object;

		return jsonObject;
	}
}