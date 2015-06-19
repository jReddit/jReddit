package com.github.jreddit.request.reddit.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HTTPClientExecutor extends Executor {

	private HttpClient httpClient;
	
	public HTTPClientExecutor(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	@Override
	public String execute(RedditToken rToken, String endpoint) {
		
	    try {
	    	
	    	// Create post request
	        HttpGet request = new HttpGet("abc"); // Something with oauth.reddit.com...
	        
	        // Set header to JSON
	        request.addHeader("Authorization", "bearer " + rToken.getAccessToken());
	        
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

}
