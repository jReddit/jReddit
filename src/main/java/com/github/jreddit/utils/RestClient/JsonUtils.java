package com.github.jreddit.utils.RestClient;

public class JsonUtils {

    /**
     * Safely converts an object into string (used because sometimes JSONObject's get() method returns null).
     *
     * @param obj The object to convert.
     * @return The string.
     */
    public static String safeJsonToString(Object obj) {
        return (obj == null ? null : obj.toString());
    }
}
