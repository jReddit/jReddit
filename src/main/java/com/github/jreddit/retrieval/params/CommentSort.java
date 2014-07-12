package com.github.jreddit.retrieval.params;

/**
 * Enum to represent comment sorts on Reddit. You see these on a page that lists comments.
 *
 * @author Evin Ugur
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public enum CommentSort {

    NEW("new"), 
    HOT("hot"), 
    TOP("top"), 
    CONTROVERSIAL("controversial"), 
    OLD("old"), 
    RANDOM("random"), 
    CONFIDENCE("confidence");

    private final String value;

    CommentSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
