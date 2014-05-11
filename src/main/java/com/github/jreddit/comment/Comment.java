package com.github.jreddit.comment;

/**
 * A Reddit comment. Contains the edited timestamp, the body
 *
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 */
public class Comment {


    private String comment;     // The actual comment
    private String edited;      // Edited timestamp
    private String created;     // Created UTC timestamp
    private String replies;     // Replies
    private int upvotes;        // Number of upvotes that this comment received
    private int downvotes;      // Number of downvotes that this comment received

    /**
     * The constructor.
     *
     * @param comment   The comment.
     * @param edited    The edited timestamp.
     * @param created   The UTC created timestamp.
     * @param replies   The replies.
     * @param upvotes   Number of upvotes that this comment received.
     * @param downvotes Number of downvotes that this comment received.
     */
    public Comment(String comment, String edited, String created, String replies, int upvotes, int downvotes) {
        this.comment = comment;
        this.edited = edited;
        this.created = created;
        this.replies = replies;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}
