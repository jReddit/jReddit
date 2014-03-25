package com.github.jreddit.utils;

import com.github.jreddit.utils.RestClient.Response;
import com.github.jreddit.utils.RestClient.RestClient;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * This class contains (or will contain) various utilities for jReddit.
 *
 * @author Omer Elnour
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 */
public class Utils implements RestClient {

    private static String DEFAULT_USER_AGENT = "Omer's Reddit API Java Wrapper";

    /**
     * Function that makes a GET request and returns a JSON object
     *
     * @param urlPath Path on Reddit to POST to
     * @param cookie  authentication for the User
     * @return Object that corresponds to the request
     */
    @Override
    public Response get(String urlPath, String cookie) {

        Response responseObject = null;

        try {
            URL url = new URL(ApiEndpointUtils.REDDIT_BASE_URL + urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            // Don't pass cookie if it is null
            if (cookie != null) {
                connection.setRequestProperty("cookie", "reddit_session=" + cookie);
            }
            connection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);


            // Debugging stuff
            InputStream is;
            Scanner scanner;
            String responseBody;
            if (connection.getResponseCode() != 200) {
                scanner = new Scanner(connection.getErrorStream());
            } else {
                is = connection.getInputStream();
                scanner = new Scanner(is);
            }
            scanner.useDelimiter("\\Z");
            responseBody = "";
            while (scanner.hasNext()) {
                responseBody += scanner.next();
            }
            scanner.close();
            // Debugging stuff

            JSONParser parser = new JSONParser();
            responseObject = new UtilResponse(responseBody, parser.parse(responseBody), connection.getResponseCode());

        } catch (IOException e) {
            System.err.println("Error making GET request to URL path: " + urlPath);
        } catch (ParseException e) {
            System.err.println("Error parsing response from GET request for URL path: " + urlPath);
        }

        return responseObject;
    }

    /**
     * Submits a POST request and returns a JSON object that corresponds to it.
     *
     * @param urlPath   Path on Reddit to POST to
     * @param apiParams parameters to put on the request
     * @param cookie    authentication for the User
     * @return JSONObject response for the POST
     */
    @Override
    public Response post(String apiParams, String urlPath, String cookie) {

        Response responseObject = null;

        try {
            URL url = new URL(ApiEndpointUtils.REDDIT_BASE_URL + urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(apiParams.length()));
            connection.setRequestProperty("cookie", "reddit_session=" + cookie);
            connection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(apiParams);
            outputStream.flush();
            outputStream.close();

            JSONParser parser = new JSONParser();
            String responseBody = IOUtils.toString(connection.getInputStream());
            Object parsedResponse = parser.parse(responseBody);
            responseObject = new UtilResponse(responseBody, parsedResponse, connection.getResponseCode());
        } catch (IOException e) {
            System.err.println("Error making POST request to URL path: " + urlPath);
        } catch (ParseException e) {
            System.err.println("Error parsing response from POST request for URL path: " + urlPath);
        }

        return responseObject;
    }


    /**
     * Sets the user agent string.
     * Note: The user-agent identifies the client software originating the HTTP request.
     *
     * @param agent User agent string.
     */
    @Override
    public void setUserAgent(String agent) {
        DEFAULT_USER_AGENT = agent;
    }
}
