package com.reddit.dev.api.jreddit.utils;

public enum TypePrefixes {

    COMMENT("t1"), ACCOUNT("t2"), LINK("t3"), MESSAGE("t4"), SUBREDDIT("t5"), AWARD("t6"), PROMO_CAMPAIGN("t8");

    private String value;

    TypePrefixes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
