package com.github.jreddit.parser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Safe utilities (not throwing exceptions) for the conversion of JSON
 * data into basic types such as Integer, Boolean, Long, and Double.
 */
public final class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    
    private JsonUtils() {
        // forbid creating JsonUtils instance
    }

    /**
     * Safely converts an object into string (used because sometimes JSONObject's get() method returns null).
     *
     * @param obj The object to convert.
     * @return The string.
     */
    public static String safeJsonToString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * Safely converts an object into an integer
     *
     * @param obj The object to convert.
     * @return an Integer representing the integer value of the Object (null if the object cannot be converted to an Integer)
     */
    public static Integer safeJsonToInteger(Object obj) {
        Integer intValue = null;

        try {
            String str = safeJsonToString(obj);
            intValue = str != null ? Integer.parseInt(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Integer failed", e);
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
        Double doubleValue = null;

        try {
            String str = safeJsonToString(obj);
            doubleValue = str != null ? Double.parseDouble(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Double failed", e);
        }

        return doubleValue;
    }
    
    
    /**
     * Safely converts an object into an boolean
     *
     * @param obj The object to convert.
     * @return a Boolean representing the boolean value of the Object (null only if the object was also null)
     */
    public static Boolean safeJsonToBoolean(Object obj) {
        String str = safeJsonToString(obj);
        Boolean booleanValue = str != null ? Boolean.parseBoolean(str) : null;
        return booleanValue;
    }
    
    /**
     * Safely converts an object into an long
     *
     * @param obj The object to convert.
     * @return a Long representing the long value of the Object (null if the object cannot be converted to Long)
     */
    public static Long safeJsonToLong(Object obj) {
        Long longValue = null;

        try {
            String str = safeJsonToString(obj);
            longValue = str != null ? Long.parseLong(str) : null;
        } catch (NumberFormatException e) {
            LOGGER.warn("Safe JSON conversion to Long failed", e);
        }

        return longValue;
    }
    
}
