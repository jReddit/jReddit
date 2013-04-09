/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omrlnr.jreddit.user;

/**
 * A Reddit comment. Contains the edited timestamp, the body
 *
 * @author Benjamin Jakobus
 */
public class Comment {
    // The actual comment

    private String comment;
    // Edited timestamp
    private String edited;
    // Created UTC timestamp
    private String created;
    // Replies
    private String replies;
    // Number of upvotes that this comment received
    private int upvotes;
    // Number of downvotes that this comment received
    private int downvotes;

    /**
     * The constructor.
     *
     * @param comment   The comment.
     * @param edited    The edited timestamp.
     * @param created   The UTC created timestamp.
     * @param replies   The replies.
     * @param upvotes   Number of upvotes that this comment received.
     * @param downvotes Number of downvotes that this comment received.
     * @author Benjamin Jakobus
     */
    public Comment(String comment, String edited, String created, String replies,
                   int upvotes, int downvotes) {
        this.comment = comment;
        this.edited = edited;
        this.created = created;
        this.replies = replies;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    /**
     * Returns the comment.
     *
     * @return The comment.
     * @author Benjamin Jakobus
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns the edited timestamp.
     *
     * @return The edited timestamp.
     * @author Benjamin Jakobus
     */
    public String getEdited() {
        return edited;
    }

    /**
     * Returns the UTC created timestamp.
     *
     * @return The UTC created timestamp.
     * @author Benjamin Jakobus
     */
    public String getCreated() {
        return created;
    }

    /**
     * Returns the replies to this comment.
     *
     * @return The replies to this comment.
     * @author Benjamin Jakobus
     */
    public String getReplies() {
        return replies;
    }

    /**
     * Sets the comment.
     *
     * @param comment The comment.
     * @author Benjamin Jakobus
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the edited timestamp.
     *
     * @param edited The edited timestamp.
     * @author Benjamin Jakobus
     */
    public void setEdited(String edited) {
        this.edited = edited;
    }

    /**
     * Sets the UTC created timestamp.
     *
     * @param created The UTC created timestamp.
     * @author Benjamin Jakobus
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Sets the replies.
     *
     * @param replies The replies.
     * @author Benjamin Jakobus
     */
    public void setReplies(String replies) {
        this.replies = replies;
    }

    /**
     * Sets the downvotes.
     *
     * @param downvotes The downvotes.
     * @author Benjamin Jakobus
     */
    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    /**
     * Sets the upvotes.
     *
     * @param upvotes The upvotes.
     * @author Benjamin Jakobus
     */
    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
}
