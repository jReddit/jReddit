package com.github.jreddit.model.domain;

/**
 * Enum to represent comment sorts on Reddit. You see these on a page that lists comments.
 *
 * @author Evin Ugur
 * @author Raul Rene Lepsa
 */
public enum CommentSortType {

    NEW("new"), HOT("hot"), TOP("top"), CONTROVERSIAL("controversial");

    private final String value;

    CommentSortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
