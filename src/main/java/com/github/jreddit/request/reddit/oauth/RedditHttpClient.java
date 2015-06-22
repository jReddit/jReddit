package com.github.jreddit.request.reddit.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.github.jreddit.request.reddit.request.RedditGetRequest;
import com.github.jreddit.request.reddit.request.RedditPostRequest;

public class RedditHttpClient extends RedditClient {

	private HttpClient httpClient;
	
	/**
	 * 
	 * @param httpClient
	 */
	public RedditHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	@Override
	public String post(RedditToken rToken, RedditPostRequest redditRequest) {
		
	    try {
	    	
	    	// Create post request
	        HttpPost request = new HttpPost(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());
	        // TODO: Add parameters to body
	        
	        // Add authorization
	        addAuthorization(request, rToken);
	        
	        // Attempt to do execute request
	        HttpResponse response = httpClient.execute(request);
	        
	        // Return response if successful
	        if (response != null) {
		        return EntityUtils.toString(response.getEntity());
	        }
	        
	    } catch (UnsupportedEncodingException uee) {
	    	//LOGGER.log(Level.SEVERE, "Unsupported Encoding Exception thrown", uee);
	    } catch (ClientProtocolException cpe) {
	    	//LOGGER.log(Level.SEVERE, "Client Protocol Exception thrown", cpe);
	    } catch (IOException ioe) {
	    	//LOGGER.log(Level.WARNING, "I/O Exception thrown", ioe);
	    }
	    
	    return null;
	    
	}
	
	@Override
	public String get(RedditToken rToken, RedditGetRequest redditRequest) {
		
	    try {
	    	
	    	// Create post request
	        HttpGet request = new HttpGet(OAUTH_API_DOMAIN + redditRequest.generateRedditURI());
	       
	        // Add authorization
	        addAuthorization(request, rToken);
	        
	        // Attempt to do execute request
	        HttpResponse response = httpClient.execute(request);//, context
	        
	        // Return response if successful
	        if (response != null) {
		        return EntityUtils.toString(response.getEntity());
	        }
	        
	    } catch (UnsupportedEncodingException uee) {
	    	//LOGGER.log(Level.SEVERE, "Unsupported Encoding Exception thrown", uee);
	    } catch (ClientProtocolException cpe) {
	    	//LOGGER.log(Level.SEVERE, "Client Protocol Exception thrown", cpe);
	    } catch (IOException ioe) {
	    	//LOGGER.log(Level.WARNING, "I/O Exception thrown", ioe);
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

}
