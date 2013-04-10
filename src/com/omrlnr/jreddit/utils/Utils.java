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
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class Utils {
    // Edit this!

    private static String userAgent = "Omer's Reddit API Java Wrapper";

    /**
     * This function is here because I do this same request a hundred times
     * throughout jReddit and I wanted to inline the function somehow.
     * <p/>
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

    /**
     * This function submits a GET request and returns a JSON object that
     * corresponds to it.
     */
    public static Object get(String apiParams, URL url, String cookie)
            throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");
        // Don't pass cookie if it is null
        if (cookie != null) {
            connection.setRequestProperty("cookie", "reddit_session=" + cookie);
        }
        connection.setRequestProperty("User-Agent", userAgent);

        JSONParser parser = new JSONParser();
        return parser.parse(new BufferedReader(new InputStreamReader(
                connection.getInputStream())).readLine());
//		JSONObject jsonObject = null;
//		try {
//			jsonObject = (JSONObject) object;
//		} catch (ClassCastException e) {
//			JSONArray jsonArray = (JSONArray) object;
//
//			jsonObject = (JSONObject) jsonArray.get(0);
//		}
//		return jsonObject;
    }

    /**
     * Returns the user agent string used when sending requests to Reddit.com.
     * Note: The user-agent identifies the client software originating the HTTP request.
     *
     * @return The user agent string used when sending requests to Reddit.com.
     */
    public static String getUserAgent() {
        return userAgent;
    }

    /**
     * Sets the user agent string.
     * Note: The user-agent identifies the client software originating the HTTP request.
     *
     * @param agent User agent string.
     */
    public static void setUserAgent(String agent) {
        userAgent = agent;
    }
}