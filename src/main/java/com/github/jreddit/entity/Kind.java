package com.github.jreddit.entity;

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
    
}
