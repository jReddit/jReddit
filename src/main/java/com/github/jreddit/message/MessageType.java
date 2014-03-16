package com.github.jreddit.message;

/**
 * Deal with different types of messages
 *
 * @author Raul Rene Lepsa
 */
public enum MessageType {

    INBOX("inbox"), SENT("sent"), UNREAD("unread");

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
