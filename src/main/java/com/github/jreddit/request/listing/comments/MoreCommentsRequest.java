package com.github.jreddit.request.listing.comments;

import java.util.List;

import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.param.CommentSort;
import com.github.jreddit.request.util.KeyValueFormatter;

public class MoreCommentsRequest extends RedditGetRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/api/morechildren.json?%s";
	
	/**
	 * Create request to retrieve more comments.
	 * 
	 * @param submissionFullname Fullname of the submission (e.g. "t3_dajdkjf")
	 * @param commentIdentifiers List of comment identifiers (e.g. ["jdafid9", "jdafid10"])
	 */
	public MoreCommentsRequest(String submissionFullname, List<String> commentIdentifiers) {
		// Neglected optional "id" parameter, as it is only relevant for HTML
		this.addParameter("api_type", "json");
		this.addParameter("link_id", submissionFullname);
		this.addParameter("children", KeyValueFormatter.formatCommaSeparatedList(commentIdentifiers));
	}
	
	/**
	 * Set the sorting method.
	 * 
	 * @param sort Sorting method
	 * 
	 * @return This builder
	 */
	public MoreCommentsRequest setSort(CommentSort sort) {
		this.addParameter("sort", sort.value());
		return this;
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, this.generateParameters());
	}
	
}
