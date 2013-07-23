package im.goel.jreddit.message;

/**
 * Encapsulates the private messages
 * @author Karan Goel
 *
 */
public class Message {
	
	/**
	 * "t4" = message
	 */

	// The body of the message
	private String body;

	// If the message was a comment or not
	private boolean was_comment;
	
	private String name;
	
	// Timestamp of when the message was created
	private String created;
	
	private String dest;
	
	// Name of the author of the message
	private String author;

	// UTC timestamp of when the message was created
	private String createdUTC;
	
	private String body_html;
		
	// The content of the message
	private String context;
	
	// The ID of this message
	private String id;
	
	// The subject of the message
	private String subject;


	/**
	 * @return the body
	 * @author Karan Goel
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body
	 * @param body to set
	 * @author Karan Goel
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the was_comment
	 * @author Karan Goel
	 */
	public boolean isWas_comment() {
		return was_comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the was_comment
	 * @param was_comment to set
	 * @author Karan Goel
	 */
	public void setWas_comment(boolean was_comment) {
		this.was_comment = was_comment;
	}

	/**
	 * @return the author
	 * @author Karan Goel
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author
	 * @param author to set
	 * @author Karan Goel
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the created
	 * @author Karan Goel
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * Sets the created
	 * @param created to set
	 * @author Karan Goel
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the created UTC timestamp
	 * @author Karan Goel
	 */
	public String getCreatedUTC() {
		return createdUTC;
	}

	/**
	 * Sets the created UTC timestamp
	 * @param createdUTC to set
	 * @author Karan Goel
	 */
	public void setCreatedUTC(String createdUTC) {
		this.createdUTC = createdUTC;
	}

	public String getBody_html() {
		return body_html;
	}

	public void setBody_html(String body_html) {
		this.body_html = body_html;
	}

	/**
	 * @return the content
	 * @author Karan Goel
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Sets the content
	 * @param context to set
	 * @author Karan Goel
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the id
	 * @author Karan Goel
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id to set
	 * @author Karan Goel
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the subject
	 * @author Karan Goel
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject
	 * @param subject to set
	 * @author Karan Goel
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
