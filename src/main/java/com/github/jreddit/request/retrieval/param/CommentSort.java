package com.github.jreddit.request.retrieval.param;

/**
 * Enum to represent comment sorts on Reddit. You see these on a page that lists comments.
 *
 * @author Evin Ugur
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public enum CommentSort {

	CONFIDENCE("confidence"),
    NEW("new"), 
    TOP("top"), 
    CONTROVERSIAL("controversial"), 
    OLD("old"), 
    QA("qa");

    private final String value;

    CommentSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
