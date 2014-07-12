package com.github.jreddit.retrieval.params;

/**
 * Enum to represent submission sorts on Reddit. You see these on a page that lists Submissions.
 *
 * @author Evin Ugur
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public enum SubmissionSort {

    HOT("hot"), 
    NEW("new"), 
    RISING("rising"), 
    CONTROVERSIAL("controversial"), 
    TOP("top");

    private final String value;

    SubmissionSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
