package com.github.jreddit.oauth.client;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

public abstract class RedditClient {
    
    /** API Domain of OAuth */
    public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
    
    /**
     * Perform a POST reddit request authenticated with the given reddit token.<br>
     * <br>
     * Does the following: (a) generates the URI (including query parameters) and appends
     * it to the reddit base, (b) adds the POST body parameters,
     * (c) adds the authorization from the token to the request, and
     * (d) executes the request.<br>
     * <br>
     * <i>Exception handling: if any function raises an exception,
     * it will be logged using SLF4J. The result would be null.</i>
     * 
     * @param rToken Reddit token
     * @param request Reddit POST request
     * 
     * @return Response from reddit (raw), if failed <i>null</i>
     */
    public abstract String post(RedditToken rToken, RedditPostRequest request);
    
    /**
     * Perform a GET reddit request authenticated with the given reddit token.<br>
     * <br>
     * Does the following: (a) generates the URI (including query parameters) and appends
     * it to the reddit base, (b) adds the authorization from the token to
     * the request, and (c) executes the request.<br>
     * <br>
     * <i>Exception handling: if any function raises an exception,
     * it will be logged using SLF4J. The result would be null.</i>
     * 
     * @param rToken Reddit token
     * @param request Reddit GET request
     * 
     * @return Response from reddit (raw), if failed <i>null</i>
     */
    public abstract String get(RedditToken rToken, RedditGetRequest request);
    
}
