package im.goel.jreddit.utils;

import im.goel.jreddit.CommentSort;
import im.goel.jreddit.Sort;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
     * @param url URL
     * @param cookie cookie
     */
    public static Object get(URL url, String cookie) throws IOException, ParseException{
    	return get("", url, cookie);
    }
    
    /**
     * This function submits a GET request and returns a JSON object that
     * corresponds to it.
     * 
     * @param apiParams HTTP arguments
     * @param url URL
     * @param cookie cookie
     */
    public static Object get(String apiParams, URL url, String cookie)
            throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");
        // Don't pass cookie if it is null
        if (cookie != null) {
            connection.setRequestProperty("cookie", "reddit_session=" + cookie);
        }
        connection.setRequestProperty("User-Agent", userAgent);
        
        
        // Debugging stuff
        // @author Karan Goel
        InputStream is = null;
		Scanner s = null;
		String response = null;
		try {
			if (connection.getResponseCode() != 200) {
				s = new Scanner(connection.getErrorStream());
			} else {
				is = connection.getInputStream();
				s = new Scanner(is);
			}
			s.useDelimiter("\\Z");
			response = "";
			while (s.hasNext()) {
				response += s.next();
			}
			System.out.println("\nResponse: " + response + "\n\n");
			s.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		// Debugging stuff
		

        JSONParser parser = new JSONParser();
        return parser.parse(response);
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


	/**
	 * Safely converts an object into string (used because sometimes
	 * JSONObject's get() method returns null).
	 *
	 * @param obj The object to convert.
	 * @return The string.
	 * @author Benjamin Jakobus
	 */
	public static String toString(Object obj) {
		return (obj == null ? null : obj.toString());
	}
	/**
	 * Function that converts the Sort enum to a String
	 * @param sort The Sort enum to be a String
	 * @return A String representation of the Sort enum
	 */
	public static String sortToString(Sort sort){
		String s = "hot";
		switch(sort){
			case t_confidence: s = "confidence"; break;
			case t_hot: s = "hot"; break;
			case t_controversial: s = "controversial"; break;
			case t_new: s = "new"; break;
			case t_old: s = "old"; break;
			case t_random: s = "random"; break;
			case t_top: s = "top"; break;
			default: s = "hot"; break;
		}
		return s;
	}
	/**
	 * Function that converts the CommentSort enum to a String
	 * @param commentSort The CommentSort enum to be a String
	 * @return A String representation of the CommentSort enum
	 */
	public static String commentSortToString(CommentSort commentSort){
		String s = "new";
		switch(commentSort){
		case t_hot: s = "hot"; break;
		case t_new: s = "new"; break;
		case t_top: s = "top"; break;
		case t_controversial: s = "controversial"; break;
		default: break;
		}
		return s;
	}
    
}
