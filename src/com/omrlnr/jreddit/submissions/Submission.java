package com.omrlnr.jreddit.submissions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.Thing;
import com.omrlnr.jreddit.user.User;
import com.omrlnr.jreddit.utils.Utils;

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
	/**
	 * The path to this submission
	 */
	private URL url;

	public Submission(User user, String fullName) {
		if (fullName.startsWith("t3_"))
			fullName = fullName.replaceFirst("t3_", "");

		this.user = user;
		this.fullName = "t3_" + fullName;
	}

	public Submission(User user, String fullName, URL url) {
		if (fullName.startsWith("t3_"))
			fullName = fullName.replaceFirst("t3_", "");

		this.user = user;
		this.fullName = "t3_" + fullName;
		this.url = url;
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
		JSONObject object = Utils.post("thing_id=" + fullName + "&text=" + text
				+ "&uh=" + user.getModhash(), new URL(
				"http://www.reddit.com/api/comment"), user.getCookie());

		if (object.toJSONString().contains(".error.USER_REQUIRED"))
			throw new InvalidCookieException("Cookie not present");
		else
			System.out.println("Commented on thread id " + fullName
					+ " saying: \"" + text + "\"");
	}

	/**
	 * This function returns the name of the author of this submission.
	 * 
	 * @return The author's name
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public String getAuthor() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return info(url).get("author").toString();
	}

	/**
	 * This function returns the title of this submission.
	 * 
	 * @return The title
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public String getTitle() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return info(url).get("title").toString();
	}

	/**
	 * This function returns the name of the subreddit that this submission was
	 * submitted to.
	 * 
	 * @return The name of the subreddit that this submission was submitted to
	 * @throws IOException
	 *             If the connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public String getSubreddit() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return info(url).get("subreddit").toString();
	}

	/**
	 * This function returns the score of this sumbission.
	 * 
	 * @return The score of this submission
	 * @throws IOException
	 *             If the connection fails
	 * @throws ParseException
	 *             If the JSON parsing fails
	 */
	public int getScore() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Integer.parseInt(info(url).get("score").toString());
	}

	/**
	 * This function returns the number of upvotes of this sumbission.
	 * 
	 * @return The number of upvotes of this submission
	 * @throws IOException
	 *             If the connection fails
	 * @throws ParseException
	 *             If the JSON parsing fails
	 */
	public int upVotes() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Integer.parseInt(info(url).get("ups").toString());
	}

	/**
	 * This function returns the number of downvotes of this sumbission.
	 * 
	 * @return The number of downvotes of this submission
	 * @throws IOException
	 *             If the connection fails
	 * @throws ParseException
	 *             If the JSON parsing fails
	 */
	public int downVotes() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Integer.parseInt(info(url).get("downs").toString());
	}

	/**
	 * This function returns true if this submission is marked as NSFW (18+)
	 * 
	 * @return This submission is NSFW
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public boolean isNSFW() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Boolean.parseBoolean(info(url).get("over_18").toString());
	}

	/**
	 * This function returns true if this submission is a self-post
	 * 
	 * @return This submission is a self post
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public boolean isSelfPost() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Boolean.parseBoolean(info(url).get("is_self").toString());
	}

	/**
	 * This function returns the number of comments in this sumbission.
	 * 
	 * @return The number of comments in this submission
	 * @throws IOException
	 *             If the connection fails
	 * @throws ParseException
	 *             If the JSON parsing fails
	 */
	public int commentCount() throws IOException, ParseException {
		if (url == null)
			throw new IOException("URL needs to be present");

		return Integer.parseInt(info(url).get("num_comments").toString());
	}

	/**
	 * This function upvotes this submission.
	 * 
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public void upVote() throws IOException, ParseException {
		JSONObject object = voteResponse(1);
		if (!(object.toJSONString().length() > 2))
			// Will return "{}"
			System.out.println("Successful upboat!");
		else
			System.out.println(object.toJSONString());
	}

	/**
	 * This function rescinds, or normalizes this submission. <br />
	 * (i.e Removes a downvote or upvote)
	 * 
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public void rescind() throws IOException, ParseException {
		JSONObject object = voteResponse(0);
		if (!(object.toJSONString().length() > 2))
			// Will return "{}"
			System.out.println("Successful rescind!");
		else
			System.out.println(object.toJSONString());
	}

	/**
	 * This function downvotes this submission.
	 * 
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public void downVote() throws IOException, ParseException {
		JSONObject object = voteResponse(-1);
		if (!(object.toJSONString().length() > 2))
			// Will return "{}"
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
		return Utils.post(
				"id=" + fullName + "&dir=" + dir + "&uh=" + user.getModhash(),
				new URL("http://www.reddit.com/api/vote"), user.getCookie());
	}

	private JSONObject info(URL url) throws IOException, ParseException {
		url = new URL(url.toString() + "/info.json");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("cookie",
				"reddit_session=" + user.getCookie());
		connection.setRequestProperty("User-Agent", Utils.userAgent);

		JSONParser parser = new JSONParser();
		Object object = parser.parse(new BufferedReader(new InputStreamReader(
				connection.getInputStream())).readLine());

		// THERE HAS TO BE A BETTER WAY TO DO THIS!!!
		JSONArray array = (JSONArray) object;
		JSONObject obj = (JSONObject) array.get(0);
		obj = (JSONObject) obj.get("data");
		array = (JSONArray) obj.get("children");
		obj = (JSONObject) array.get(0);
		obj = (JSONObject) obj.get("data");
		return (JSONObject) obj;
	}

	@Override
	public String toString() {
		try {
			return "(" + getScore() + ") " + getTitle();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}
}