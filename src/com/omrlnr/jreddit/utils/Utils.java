package com.omrlnr.jreddit.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class contains (or will contain) various utilities for jReddit.
 * 
 * @author <a href="https://www.github.com/OmerE">Omer Elnour</a>
 */
public class Utils {
	// Edit this!
	public static final String userAgent = "Omer's Reddit API Java Wrapper";

	/**
	 * This function is here because I do this same request a hundred times
	 * throughout jReddit and I wanted to inline the function somehow.
	 * 
	 * It basically submits a POST request and returns a JSON object that
	 * corresponds to it.
	 */
	public static JSONObject post(String apiParams, URL url, String cookie)
			throws IOException, ParseException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length",
				String.valueOf(apiParams.length()));
		connection.setRequestProperty("cookie", "reddit_session=" + cookie);
		connection.setRequestProperty("User-Agent", userAgent);
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

	public static JSONObject get(String apiParams, URL url, String cookie)
			throws IOException, ParseException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("cookie", "reddit_session=" + cookie);
		connection.setRequestProperty("User-Agent", userAgent);

		JSONParser parser = new JSONParser();
		Object object = parser.parse(new BufferedReader(new InputStreamReader(
				connection.getInputStream())).readLine());
		JSONObject jsonObject = (JSONObject) object;

		return jsonObject;
	}
}