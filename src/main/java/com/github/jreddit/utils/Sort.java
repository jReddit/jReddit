package com.github.jreddit.utils;

/**
 * Enum to represent submission sorts on Reddit. You see these on a page that lists Submissions.
 *
 * @author Evin Ugur
 * @author Raul Rene Lepsa
 */
public enum Sort {

    CONFIDENCE("confidence"), TOP("top"), NEW("new"), HOT("hot"), CONTROVERSIAL("controversial"), OLD("old"), RANDOM("random");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
