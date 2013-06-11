package com.omrlnr.jreddit.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

import java.lang.Thread;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 * This class contains (or will contain) various utilities for jReddit.
 * 
 */
public class Utils {

    private static int SLEEP_TIME = 2000;

    private static String userAgent = "Fixing Reddit API Java Wrapper";

    /**
     * This function is here because I do this same request a hundred times
     * throughout jReddit and I wanted to inline the function somehow.
     * 
     * It basically submits a POST request and returns a JSON object that
     * corresponds to it.
     */
    public static JSONObject post(String apiParams, URL url, String cookie)
            throws IOException, ParseException {

        //
        // Adhere to API rules....
        // (Make this configurable)
        //        
        try {
            Thread.sleep(SLEEP_TIME);
        } catch(InterruptedException ie) {
            ie.printStackTrace();
            return null;
        }

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
    public static JSONObject get(String apiParams, URL url, String cookie)
            throws IOException, ParseException {

        //
        // Adhere to API rules....
        // (Make this configurable)
        //        
        try {
            Thread.sleep(SLEEP_TIME);
        } catch(InterruptedException ie) {
            ie.printStackTrace();
            return null;
        }


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

    public static String getUserAgent() {
        return userAgent;
    }

    public static void setUserAgent(String agent) {
        userAgent = agent;
    }

    /**
     *
     * Get a somewhat more human readable version of the JSON string.
     *
     */
    public static String getJSONDebugString(Object obj, String indent) {

        String ret = "";

        //
        // Handle hashtable
        //
        if(obj instanceof HashMap) {
            ret += indent + "{\n";
            HashMap hash = (HashMap)obj;
            Iterator it = hash.keySet().iterator();
            while(it.hasNext()) {
                String key = (String)it.next();
                ret += indent + key + ": ";
                Object val = hash.get(key);
                ret += indent + getJSONDebugString(val, indent + "    ");
            }
            ret += indent + "}\n";
            return ret;
        }

        //
        // Handle array
        //
        if(obj instanceof ArrayList) {
            ret += indent + "[\n";
            ArrayList list = (ArrayList)obj;
            for(int i = 0; i < list.size(); i++) {
                Object val = list.get(i); 
                ret += indent + getJSONDebugString(val, indent + "    ");
            }
            ret += indent + "]\n";
            return ret;
        }

        //
        // No hashtable or array so this should be a primitive...
        //
        return ((obj == null) ? "null" : obj.toString()) + "\n";

    }


}
