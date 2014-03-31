package com.github.jreddit.model.domain;

public enum SubredditType {

    NEW("new"), BANNED("banned"), POPULAR("popular");

    private final String value;

    SubredditType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
