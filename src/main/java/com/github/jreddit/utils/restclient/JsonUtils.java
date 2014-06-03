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
    
    /**
     * Safely converts an object into an double
     *
     * @param obj The object to convert.
     * @return a Double representing the double value of the Object (null if the object cannot be converted to Double)
     */
    public static Double safeJsonToDouble(Object obj) {
        Double doubleValue;

        try {
            doubleValue = Double.parseDouble(safeJsonToString(obj));
        } catch (NumberFormatException e) {
            doubleValue = null;
        }

        return doubleValue;
    }
    
    
    /**
     * Safely converts an object into an boolean
     *
     * @param obj The object to convert.
     * @return a Boolean representing the boolean value of the Object (null if the object cannot be converted to Boolean)
     */
    public static Boolean safeJsonToBoolean(Object obj) {
        Boolean booleanValue;

        try {
            booleanValue = Boolean.parseBoolean(safeJsonToString(obj));
        } catch (NumberFormatException e) {
            booleanValue = null;
        }

        return booleanValue;
    }
    
    /**
     * Safely converts an object into an long
     *
     * @param obj The object to convert.
     * @return a Long representing the long value of the Object (null if the object cannot be converted to Long)
     */
    public static Long safeJsonToLong(Object obj) {
        Long longValue;

        try {
            longValue = Long.parseLong(safeJsonToString(obj));
        } catch (NumberFormatException e) {
            longValue = null;
        }

        return longValue;
    }
}
