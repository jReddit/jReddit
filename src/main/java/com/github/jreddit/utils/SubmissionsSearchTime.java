package com.github.jreddit.utils;

/**
 * Enumeration to represent the different submission search times.
 * @author Simon Kassing
 */
public enum SubmissionsSearchTime {

    HOUR		("hour"), 
    DAY			("day"), 
    WEEK		("week"), 
    MONTH		("month"), 
    YEAR		("year"),
    ALL			("all");

    private final String value;

    SubmissionsSearchTime(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
