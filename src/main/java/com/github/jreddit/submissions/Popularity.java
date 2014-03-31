package com.github.jreddit.submissions;

public enum Popularity {
    HOT("/hot"),
    NEW("/new"),
    RISING("/rising"),
    CONTROVERSIAL("/controversial"),
    TOP("/top");

    private String value;

    private Popularity(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}