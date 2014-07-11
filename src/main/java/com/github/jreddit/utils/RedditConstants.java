package com.github.jreddit.utils;

public class RedditConstants {

	public static final int MAX_LIMIT_LISTING = 100;
	public static final int DEFAULT_LIMIT = 25;
	public static final int MAX_LIMIT_COMMENTS = 500;
	
	/**
	 * Approximately the maximum listing size, including pagination until
	 * the end. This differs from request to request, but after some observations
	 * this is a nice upper bound.
	 */
	public static final int APPROXIMATE_MAX_LISTING_AMOUNT = 1300;
	
}
