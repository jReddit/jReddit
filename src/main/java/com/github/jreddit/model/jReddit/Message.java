package com.github.jreddit.model.jReddit;

/**
 * Encapsulates the private messages.
 * Corresponds to the <code>TypePrefix.MESSAGES</code>, which is has the value t4 for the Reddit API
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class Message {

    // The ID of this message
    private String id;

    // Name  - a combination of the Message Type (t4) and the ID of the message
    private String fullName;

    // Name of the author of the message
    private String author;

    // Recipient of the message
    private String recipient;

	// The body of the message
	private String body;

    // HTML version of the Body
    private String bodyHtml;

	// If the message was a comment or not
	private boolean isComment;
	
	// Timestamp of when the message was created
	private String created;

	// UTC timestamp of when the message was created
	private String createdUTC;
		
	// The content of the message
	private String context;
	
	// The subject of the message
	private String subject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean isComment) {
        this.isComment = isComment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedUTC() {
        return createdUTC;
    }

    public void setCreatedUTC(String createdUTC) {
        this.createdUTC = createdUTC;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public class Comment extends Message {

        /**
         * A comment in list of unread messages. This is different from
         * com.github.jreddit.model.jReddit.Comment
         */

        private String link_title;
        private String subreddit; // The subreddit where this im.goel.jreddit.message was posted

        /**
         * @return the link_title
         */
        public String getLink_title() {
            return link_title;
        }

        /**
         * Sets the link_title
         * @param link_title the link_title to set
         */
        public void setLink_title(String link_title) {
            this.link_title = link_title;
        }

        /**
         * @return the subreddit
         */
        public String getSubreddit() {
            return subreddit;
        }

        /**
         * Sets the subreddit
         * @param subreddit the subreddit to set
         */
        public void setSubreddit(String subreddit) {
            this.subreddit = subreddit;
        }

    }
}
