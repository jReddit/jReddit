package com.github.jreddit.oauth.client;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

public class RedditPoliteClient extends RedditClient {
	
	/** Wrapped reddit client. */
	RedditClient redditClient;
	
    /** Waiting time in milliseconds. */
    private static final long WAIT_TIME = 1000L;

    /** Last time a request was made. */
    private long lastReqTime = 0;
    
    /**
     * Polite wrapper around the reddit client.
     * 
     * @param redditClient Reddit client to wrap
     */
	public RedditPoliteClient(RedditClient redditClient) {
		this.redditClient = redditClient;
	}

	@Override
	public String post(RedditToken rToken, RedditPostRequest request) {
        waitIfNeeded();
		String result = redditClient.post(rToken, request);
		noteTime();
		return result;
	}

	@Override
	public String get(RedditToken rToken, RedditGetRequest request) {
        waitIfNeeded();
		String result = redditClient.get(rToken, request);
		noteTime();
		return result;
	}
	
	/**
	 * Note the current time.
	 */
    private void noteTime() {
        lastReqTime = System.currentTimeMillis();
    }
    
    /**
     * Wait if required.
     */
    private void waitIfNeeded() {

        // Calculate elapsed milliseconds
        long elapsed = System.currentTimeMillis() - lastReqTime;

        // If enough time has elapsed, no need to wait
        if (elapsed >= WAIT_TIME) {
        	return;
        }

        // If not enough time was elapsed, wait the remainder
        long toWait = WAIT_TIME - elapsed;
        try {
            Thread.sleep(toWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
	
}
