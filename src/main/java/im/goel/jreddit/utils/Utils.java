package im.goel.jreddit.utils;

import im.goel.jreddit.CommentSort;
import im.goel.jreddit.Sort;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * This class contains (or will contain) various utilities for jReddit.
 *
 * @author Omer Elnour
 * @author Raul Rene Lepsa
 */
public class Utils {

    private static String userAgent = "Omer's Reddit API Java Wrapper";

    /**
     * Function that submits a POST request and returns a JSON object that corresponds to it.
     *
     * @param apiParams parameters to put on the request
     * @param url       URL to POST to
     * @param cookie    authentication for the User
     * @return JSONObject response for the POST
     */
    public static JSONObject post(String apiParams, URL url, String cookie) {

        Object response = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(apiParams.length()));
            connection.setRequestProperty("cookie", "reddit_session=" + cookie);
            connection.setRequestProperty("User-Agent", userAgent);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(apiParams);
            outputStream.flush();
            outputStream.close();

            JSONParser parser = new JSONParser();
            response = parser.parse(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine());
        } catch (IOException e) {
            System.out.println("Error making POST request to URL: " + url);
        } catch (ParseException e) {
            System.out.println("Error parsing response from POST request for URL: " + url);
        }

        return (JSONObject) response;
    }

    /**
     * Function that makes a GET request and returns a
     *
     * @param url    URL for the GET request
     * @param cookie authentication for the User
     * @return JSON object that corresponds to the request
     */
    public static Object get(URL url, String cookie) {

        Object object = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            // Don't pass cookie if it is null
            if (cookie != null) {
                connection.setRequestProperty("cookie", "reddit_session=" + cookie);
            }
            connection.setRequestProperty("User-Agent", userAgent);


            // Debugging stuff
            InputStream is;
            Scanner scanner;
            String response;
            if (connection.getResponseCode() != 200) {
                scanner = new Scanner(connection.getErrorStream());
            } else {
                is = connection.getInputStream();
                scanner = new Scanner(is);
            }
            scanner.useDelimiter("\\Z");
            response = "";
            while (scanner.hasNext()) {
                response += scanner.next();
            }
            scanner.close();
            // Debugging stuff


            JSONParser parser = new JSONParser();
            object = parser.parse(response);

        } catch (IOException e) {
            System.out.println("Error making GET request to URL: " + url);
        } catch (ParseException e) {
            System.out.println("Error parsing response from GET request for URL: " + url);
        }

        return object;
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
     * Safely converts an object into string (used because sometimes JSONObject's get() method returns null).
     *
     * @param obj The object to convert.
     * @return The string.
     */
    public static String toString(Object obj) {
        return (obj == null ? null : obj.toString());
    }

}
