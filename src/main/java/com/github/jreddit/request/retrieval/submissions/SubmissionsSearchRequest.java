package com.github.jreddit.request.retrieval.submissions;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.QuerySyntax;
import com.github.jreddit.request.retrieval.param.SearchSort;
import com.github.jreddit.request.retrieval.param.TimeSpan;

public class SubmissionsSearchRequest extends ListingRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/search.json?%s";

	/**
	 * Constructor.
	 * 
	 * @param query Search query (e.g. "programming Java"), its syntax depends on what is set using {@link #setSyntax(QuerySyntax)}.
	 */
	public SubmissionsSearchRequest(String query) {
		this.addParameter("q", query);
	}

	public SubmissionsSearchRequest setSyntax(QuerySyntax syntax) {
		this.addParameter("syntax", syntax.value());
		return this;
	}
	
	public SubmissionsSearchRequest setSort(SearchSort sort) {
		this.addParameter("sort", sort.value());
		return this;
	}
	
	public SubmissionsSearchRequest setTimeSpan(TimeSpan time) {
		this.addParameter("t", time.value());
		return this;
	}
	
	public SubmissionsSearchRequest setShowAll() {
		this.addParameter("show", "all");
		return this;
	}

	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, this.generateParameters());
	}
	
}
