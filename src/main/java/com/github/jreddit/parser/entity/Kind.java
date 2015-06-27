package com.github.jreddit.parser.entity;

/**
 * Enumeration to represent the different types of Things.
 */
public enum Kind {

    COMMENT("t1"), ACCOUNT("t2"), LINK("t3"), MESSAGE("t4"), SUBREDDIT("t5"), AWARD("t6"), PROMO_CAMPAIGN("t8"), MORE("more"), LISTING("listing");

    private String value;

    /**
     * Type enumeration constructor.
     * @param value String representation
     */
    Kind(String value) {
        this.value = value;
    }

    /**
     * Retrieve the value of the type.
     * @return Type String representation.
     */
    public String value() {
        return value;
    }
    
    /**
     * Match a string with its respective kind.
     * 
     * @param t String kind (e.g. "t1" or "t5")
     * 
     * @return Match kind (null, it not found)
     */
    public static Kind match(String t) {
        for (Kind k : Kind.values()) {
            if (k.value().equals(t)) {
                return k;
            }
        }
        return null;
    }
    
}
