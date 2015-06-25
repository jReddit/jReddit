package com.github.jreddit.request.retrieval.mixed;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.retrieval.param.CommentSort;

public class FullSubmissionRequest extends RedditGetRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/comments/%s.json?%s";

	/** Name of the user. */
	String submissionIdentifier;
	
	/**
	 * Constructor, with mandatory parameters for the request.
	 * 
	 * @param subreddit Subreddit (e.g. "funny")
	 */
	public FullSubmissionRequest(String submissionIdentifier) {
		this.submissionIdentifier = submissionIdentifier;
	}

	/**
	 * Set the sorting method.
	 * 
	 * @param sort Sorting method
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setSort(CommentSort sort) {
		this.addParameter("sort", sort.value());
		return this;
	}
	
	/**
	 * Set the limit of comments returned.
	 * 
	 * @param limit Limit
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setLimit(int limit) {
		this.addParameter("limit", String.valueOf(limit));
		return this;
	}
	
	/**
	 * Set the comment that will be the (highlighted) focal point of the 
	 * returned view and <i>context</i> will be the number of parents shown.
	 * 
	 * @param commentIdentifier Comment ID36 identifier
	 * 
	 * @return This builder
	 * 
	 * @see Comment#getIdentifier()
	 * @see {@link #setContext(int)}
	 */
	public FullSubmissionRequest setComment(String commentIdentifier) {
		this.addParameter("comment", commentIdentifier);
		return this;
	}
	
	/**
	 * Set the number of parents shown. This will only affect the result if
	 * {@link #setComment(String)} has been set.
	 * 
	 * @param context Maximum number of parents shown (integer between 0 and 8)
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setContext(int context) {
		this.addParameter("context", String.valueOf(context));
		return this;
	}
	
	/**
	 * Set the maximum depth of subtrees in the thread.
	 * 
	 * @param depth An integer indicating maximum depth
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setDepth(int depth) {
		this.addParameter("depth", String.valueOf(depth));
		return this;
	}
	
	/**
	 * Set whether or not to show the edits in the comments.
	 * 
	 * @param showEdits Should the edits be shown?
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setShowEdits(boolean showEdits) {
		this.addParameter("showedits", String.valueOf(showEdits));
		return this;
	}
	
	/**
	 * Set whether or not the "more" buttons should be shown.
	 * 
	 * @param showMore Should the more buttons be shown?
	 * 
	 * @return This builder
	 */
	public FullSubmissionRequest setShowMore(boolean showMore) {
		this.addParameter("showmore", String.valueOf(showMore));
		return this;
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, submissionIdentifier, this.generateParameters());
	}
	
}
