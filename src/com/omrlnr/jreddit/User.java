package com.omrlnr.jreddit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.utils.Utils;

/**
 * This class represents a reddit user.
 * 
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author <a hred="https://github.com/jasonsimpson">Jason Simpson</a>
 *
 */
public class User {

	private String username, password;
	private String modhash, cookie;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Call this function to connect the user. <br />
	 * By "connect" I mean effectively sending a POST request to reddit and
	 * getting the modhash and cookie, which are required for most reddit API
	 * functions.
	 * 
	 * @throws Exception
	 *             If connection fails.
	 */
	public void connect() throws Exception {
		ArrayList<String> hashCookiePair = hashCookiePair(username, password);
		this.modhash = hashCookiePair.get(0);
		this.cookie = hashCookiePair.get(1);
	}

	/**
	 * This function submits a link to the specified subreddit.
	 * 
	 * @param title
	 *            The title of the submission
	 * @param link
	 *            The link to the submission
	 * @param subreddit
	 *            The subreddit to submit to
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON Parsing fails
	 */
	public void submitLink(String title, String link, String subreddit)
			throws IOException, ParseException {
		JSONObject object = submit(title, link, false, subreddit);
		if (object.toJSONString().contains(".error.USER_REQUIRED")) {
			System.err.println("Please login first.");
		} else if (object.toJSONString().contains(
				".error.RATELIMIT.field-ratelimit")) {
			System.err.println("You are doing that too much.");
		} else if (object.toJSONString().contains(
				".error.ALREADY_SUB.field-url")) {
			System.err.println("That link has already been submitted.");
		} else {
			System.out.println("Link submitted to "
					+ ((JSONArray) ((JSONArray) ((JSONArray) object
							.get("jquery")).get(16)).get(3)).get(0));
		}
	}

	/**
	 * This function submits a self post to the specified subreddit.
	 * 
	 * @param title
	 *            The title of the submission
	 * @param text
	 *            The text of the submission
	 * @param subreddit
	 *            The subreddit to submit to
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON Parsing fails
	 */
	public void submitSelfPost(String title, String text, String subreddit)
			throws IOException, ParseException {
		JSONObject object = submit(title, text, true, subreddit);
		if (object.toJSONString().contains(".error.USER_REQUIRED")) {
			System.err.println("Please login first.");
		} else if (object.toJSONString().contains(
				".error.RATELIMIT.field-ratelimit")) {
			System.err.println("You are doing that too much.");
		} else if (object.toJSONString().contains(
				".error.ALREADY_SUB.field-url")) {
			System.err.println("That link has already been submitted.");
		} else {
			System.out.println("Self post submitted to "
					+ ((JSONArray) ((JSONArray) ((JSONArray) object
							.get("jquery")).get(10)).get(3)).get(0));
		}
	}

	/**
	 * This functions returns true if this user has unread mail.
	 * 
	 * @return This user has mail or not
	 * @throws ParseException
	 *             If JSON parsing fails
	 * @throws IOException
	 *             If connection fails
	 */
	public boolean hasMail() throws IOException, ParseException {
		return Boolean.parseBoolean(info().get("has_mail").toString());
	}

	/**
	 * This function returns the Unix time that the user's account was created.
	 * 
	 * @return Unix time that the user's account was created
	 * @throws NumberFormatException
	 *             If the "created" property isn't a double
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public double created() throws IOException, ParseException {
		return Double.parseDouble(info().get("created").toString());
	}

	/**
	 * This function returns the Unix time (in UTC/Coordinated Universal Time)
	 * that the user's account was created.
	 * 
	 * @return Unix time that the user's account was created in UTC
	 * @throws NumberFormatException
	 *             If the "created_utc" property isn't a double
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public double createdUTC() throws IOException, ParseException {
		return Double.parseDouble(info().get("created_utc").toString());
	}

	/**
	 * This function returns the amount of link karma this user has. <br />
	 * Returns int because I doubt anyone has more than 2,147,483,647 link
	 * karma.
	 * 
	 * @return Link Karma
	 * @throws NumberFormatException
	 *             If the "link_karma" property isn't an inteher
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public int linkKarma() throws IOException, ParseException {
		return Integer.parseInt(info().get("link_karma").toString());
	}

	/**
	 * This function returns the amount of comment karma this user has. <br />
	 * Returns int because I doubt anyone has more than 2,147,483,647 comment
	 * karma.
	 * 
	 * @return Comment Karma
	 * @throws NumberFormatException
	 *             If the "link_karma" property isn't an inteher
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public int commentKarma() throws IOException, ParseException {
		return Integer.parseInt(info().get("comment_karma").toString());
	}

	/**
	 * This functions returns true if this user has a gold account.
	 * 
	 * @return This user has a gold account or not
	 * @throws ParseException
	 *             If JSON parsing fails
	 * @throws IOException
	 *             If connection fails
	 */
	public boolean isGold() throws IOException, ParseException {
		return Boolean.parseBoolean(info().get("is_gold").toString());
	}

	/**
	 * This functions returns true if this user is a reddit moderator
	 * (apparently this means a moderator of any subreddit).
	 * 
	 * @return This user is a moderator or not
	 * @throws ParseException
	 *             If JSON parsing fails
	 * @throws IOException
	 *             If connection fails
	 */
	public boolean isMod() throws IOException, ParseException {
		return Boolean.parseBoolean(info().get("is_mod").toString());
	}

	/**
	 * This function returns the user's ID. <br />
	 * The user's ID. This is only used internally, <b>right</b>?
	 * 
	 * @return This user's ID
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	public String id() throws IOException, ParseException {
		return info().get("id").toString();
	}

	/**
	 * This functions returns true if this user has unread moderator mail.
	 * 
	 * @return This user has unread moderator mail or not
	 * @throws ParseException
	 *             If JSON parsing fails
	 * @throws IOException
	 *             If connection fails
	 */
	public boolean hasModMail() throws IOException, ParseException {
		return Boolean.parseBoolean(info().get("has_mod_mail").toString());
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getModhash() {
		return modhash;
	}

	public String getCookie() {
		return cookie;
	}

	/**
	 * This function logs in to reddit and returns an ArrayList containing a
	 * modhash and cookie.
	 * 
	 * @param username
	 *            The username
	 * @param password
	 *            The password
	 * @return An array containing a modhash and cookie
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If parsing JSON fails
	 */
	private ArrayList<String> hashCookiePair(String username, String password)
			throws IOException, ParseException {
		ArrayList<String> values = new ArrayList<String>();
		JSONObject jsonObject = Utils.post("api_type=json&user=" + username
				+ "&passwd=" + password, new URL(
				"http://www.reddit.com/api/login/" + username), getCookie());
		JSONObject valuePair = (JSONObject) ((JSONObject) jsonObject
				.get("json")).get("data");

		values.add(valuePair.get("modhash").toString());
		values.add(valuePair.get("cookie").toString());

		return values;
	}

	/**
	 * This function returns a "response" (me.json) JSON data containing info
	 * about the user. <br />
	 * 
	 * @return JSON data containing info about the user
	 */
	private JSONObject info() throws IOException, ParseException {

		if (cookie == null || modhash == null) {
			throw new IOException("User not connected. " +
                "Please invoke the \"connect\" method before attempting " +
                "to call any other User API functions.");
		}

		JSONObject jsonObject = 
            (JSONObject)Utils.get("", 
                new URL("http://www.reddit.com/api/me.json"), 
                getCookie() 
                );

		return (JSONObject)jsonObject.get("data");
	}

	/**
	 * This function submits a link or self post.
	 * 
	 * @param title
	 *            The title of the submission
	 * @param linkOrText
	 *            The link of the submission or text
	 * @param selfPost
	 *            If this submission is a self post
	 * @param subreddit
	 *            Which subreddit to submit this to
	 * @return A JSONObject
	 * @throws IOException
	 *             If connection fails
	 * @throws ParseException
	 *             If JSON parsing fails
	 */
	private JSONObject submit(String title, String linkOrText,
			boolean selfPost, String subreddit) throws IOException,
			ParseException {
		return Utils.post("title=" + title + "&" + (selfPost ? "text" : "url")
				+ "=" + linkOrText + "&sr=" + subreddit + "&kind="
				+ (selfPost ? "link" : "self") + "&uh=" + getModhash(),
				new URL("http://www.reddit.com/api/submit"), getCookie());
	}
}
