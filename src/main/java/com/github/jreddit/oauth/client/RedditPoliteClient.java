package com.github.jreddit.oauth.client;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

/**
 * Wrapper for any reddit client, which makes it <i>polite</i>.
 * Polite means that it will only send requests in an interval,
 * and does not overload reddit. It will also mean you will get
 * less denial messages from reddit *hint* *hint*.
 * 
 * @author Simon Kassing
 */
public class RedditPoliteClient extends RedditClient {
	
	/** Wrapped reddit client. */
	RedditClient redditClient;
	
    /** Waiting time in milliseconds. */
    private static final long DEFAULT_INTERVAL = 1000L;
    
    /** Default interval in milliseconds. */
    private long interval;

    /** Last time a request was made. */
    private long lastReqTime = 0;
    
    /**
     * Polite wrapper around the reddit client.
     * 
     * @param redditClient Reddit client to wrap
     */
	public RedditPoliteClient(RedditClient redditClient) {
		this(redditClient, DEFAULT_INTERVAL);
	}
	
    /**
     * Polite wrapper around the reddit client with configurable time.
     * 
     * @param redditClient Reddit client to wrap
     * @param interval Interval in milliseconds
     */
	public RedditPoliteClient(RedditClient redditClient, long interval) {
		this.redditClient = redditClient;
		this.interval = interval;
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
        if (elapsed >= interval) {
        	return;
        }

        // If not enough time was elapsed, wait the remainder
        long toWait = interval - elapsed;
        try {
            Thread.sleep(toWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
	
}
