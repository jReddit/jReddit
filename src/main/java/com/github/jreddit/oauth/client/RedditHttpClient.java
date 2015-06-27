package com.github.jreddit.oauth.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

/**
 * HTTP client implementation for a <i>RedditClient</i>.
 * 
 * @author Simon Kassing
 * 
 * @see RedditClient
 */
public class RedditHttpClient extends RedditClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedditHttpClient.class);
    
    private final String userAgent;
    
    private final HttpClient httpClient;
    
    /**
     * @param userAgent User agent of your application
     * @param httpClient HTTP client to use for the requests
     */
    public RedditHttpClient(String userAgent, HttpClient httpClient) {
        this.userAgent = userAgent;
        this.httpClient = httpClient;
    }
    
    @Override
    public String post(RedditToken rToken, RedditPostRequest redditRequest) {
        
        try {
        
            // Create post request
            HttpPost request = new HttpPost(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());
    
            // Add parameters to body
            request.setEntity(new StringEntity(redditRequest.generateBody()));
            
            // Add authorization
            addAuthorization(request, rToken);
            
            // Add user agent
            addUserAgent(request);
            
            // Add content type
            request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            
            return executeHttpRequest(request);
        
        } catch (UnsupportedEncodingException uee) {
            LOGGER.warn("Unsupported Encoding Exception thrown in POST request when encoding body", uee);
        }
        
        return null;
        
    }
    
    @Override
    public String get(RedditToken rToken, RedditGetRequest redditRequest) {
        
        // Create get request
        HttpGet request = new HttpGet(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());

        // Add authorization
        addAuthorization(request, rToken);
        
        // Add user agent
        addUserAgent(request);

        return executeHttpRequest(request);
        
    }
    
    /**
     * Execute the given HTTP request.
     * 
     * @param request HTTP request
     * 
     * @return Result, <i>null</i> if failed
     */
    private String executeHttpRequest(HttpUriRequest request) {
        try {
            
            // Attempt to do execute request
            HttpResponse response = httpClient.execute(request);
            
            // Return response if successful
            if (response != null) {
                return EntityUtils.toString(response.getEntity());
            }
            
        } catch (UnsupportedEncodingException uee) {
            LOGGER.warn("Unsupported Encoding Exception thrown in request", uee);
        } catch (ClientProtocolException cpe) {
            LOGGER.warn("Client Protocol Exception thrown in request", cpe);
        } catch (IOException ioe) {
            LOGGER.warn("I/O Exception thrown in request", ioe);
        }
        
        return null;
        
    }
    
    /**
     * Add authorization to the HTTP request.
     * 
     * @param request HTTP request
     * @param rToken Reddit token (generally of the "bearer" type)
     */
    private void addAuthorization(HttpRequest request, RedditToken rToken) {
        request.addHeader("Authorization", rToken.getTokenType() + " " + rToken.getAccessToken());
    }
    
    /**
     * Add user agent to the HTTP request.
     * 
     * @param request HTTP request
     */
    private void addUserAgent(HttpRequest request) {
        request.addHeader("User-Agent", userAgent);
    }

}
