package com.github.jreddit.message;

/**
 * A comment in list of unread messages.
 * This is different from <code>com.github.jreddit.comment.Comment</code>
 *
 * @author ThatBox
 * @author Raul Rene Lepsa
 */
public class MessageComment extends Message {

    private String link_title;
    private String subreddit;       // Subreddit where this Comment was posted to

    public String getLink_title() {
        return link_title;
    }

    public void setLink_title(String link_title) {
        this.link_title = link_title;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

}
