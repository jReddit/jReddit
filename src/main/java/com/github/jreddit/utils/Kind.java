package com.github.jreddit.utils;

public enum Kind {

    COMMENT("t1"), ACCOUNT("t2"), LINK("t3"), MESSAGE("t4"), SUBREDDIT("t5"), AWARD("t6"), PROMO_CAMPAIGN("t8"), MORE("more"), LISTING("listing");

    private String value;

    Kind(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
