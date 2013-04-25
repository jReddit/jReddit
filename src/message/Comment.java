package message;

public class Comment extends Message {

	/**
	 * @author Karan Goel
	 */

	private String link_title;

	// The subreddit where this message was posted
	private String subreddit;

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
