package com.github.jreddit.message;

/**
 * Encapsulates the private messages.
 * Corresponds to the <code>Kind.MESSAGES</code>, which is has the value t4 for the Reddit API
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

    // If it is a comment, it has a parent
    private String parentId;
	
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
