package com.github.jreddit.utils.restclient;

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

    /**
     * Safely converts an object into an integer
     *
     * @param obj The object to convert.
     * @return an Integer representing the integer value of the Object (null if the object cannot be converted to an Integer)
     */
    public static Integer safeJsonToInteger(Object obj) {
        Integer intValue;

        try {
            intValue = Integer.parseInt(safeJsonToString(obj));
        } catch (NumberFormatException e) {
            intValue = null;
        }

        return intValue;
    }
}
