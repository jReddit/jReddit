package com.github.jreddit.entity;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToInteger;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * A Reddit comment. Contains the edited timestamp, the body
 *
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public class Comment extends Thing {

    private String author;			// Username of the author
    private String parentId;		// Parent identifier
    private String body;            // The actual body
    private String edited;          // Edited timestamp
    private String created;         // Created timestamp
    private String createdUTC;      // Created UTC timestamp
    private boolean hasReplies;		// If replies exist on reddit
    private List<Comment> replies;  // Replies if retrieved
    private Integer gilded;        	// Amount of times the comment been gilded
    private Integer score;        	// Karma score
    private Integer upvotes;        // Number of upvotes that this body received
    private Integer downvotes;      // Number of downvotes that this body received

    public Comment(JSONObject obj) {
    	super(safeJsonToString(obj.get("name")));
    	
        try {
        	
            this.setAuthor(safeJsonToString(obj.get("author")));
            this.setParentId(safeJsonToString(obj.get("parent_id")));
            this.setBody(safeJsonToString(obj.get("body")));
            this.setEdited(safeJsonToString(obj.get("edited")));
            this.setCreated(safeJsonToString(obj.get("created")));
            this.setCreatedUTC(safeJsonToString(obj.get("created_utc")));
            hasReplies = (obj.get("replies") != null) ? !safeJsonToString(obj.get("replies")).isEmpty() : false;
            this.replies = new LinkedList<Comment>();
            this.setGilded(safeJsonToInteger(obj.get("gilded")));
            this.setScore(safeJsonToInteger(obj.get("score")));
            this.setUpvotes(safeJsonToInteger(obj.get("ups")));
            this.setDownvotes(safeJsonToInteger(obj.get("downs")));

        } catch (Exception e) {
        	e.printStackTrace();
        	throw new IllegalArgumentException("JSON Object could not be parsed into a Comment. Provide a JSON Object with a valid structure.");
        }

    }
    
    /**
     * Add a reply to this comment.
     * @param c Reply comment
     */
    public void addReply(Comment c) {
    	this.replies.add(c);
    }
    
    /**
     * If the comment is retrieved recursively, this might have the replies.
     * @return Replies
     */
    public List<Comment> getReplies() {
    	return this.replies;
    }
    
    public void setReplies(List<Comment> replies) {
    	this.replies = replies;
    }
    
    /**
     * Return whether the comment has replies, this is only set if the comment
     * is retrieved recursively.
     * @return Whether there are replies on Reddit for this comment
     */
    public boolean hasRepliesSomewhere() {
    	return hasReplies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public Integer getGilded() {
		return gilded;
	}

	public void setGilded(Integer gilded) {
		this.gilded = gilded;
	}

	public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
    
    public String getCreatedUTC() {
		return createdUTC;
	}

	public void setCreatedUTC(String createdUTC) {
		this.createdUTC = createdUTC;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
    public String toString() {
    	return "Comment(" + identifier + ")<" + ((body.length() > 10) ? body.substring(0, 10) : body) + ">";
    }    
    
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Comment && this.getFullName().equals(((Comment) other).getFullName()));
    }

	public int compareTo(Thing o) {
		return this.getFullName().compareTo(o.getFullName());
	}
    
}
