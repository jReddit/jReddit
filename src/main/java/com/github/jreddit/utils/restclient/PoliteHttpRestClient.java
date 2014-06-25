package com.github.jreddit.utils.restclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

/**
* Reddit's API access rules require that no more than 1 request is made every 2 seconds
* (i.e 30 requests per minute). This client makes it easy to track the time, and ensures that
* the 2 seconds pass between each request. Its a singleton class, use PoliteRestClient.get()
* to get an instance. This is so all classes / requests are limited by the same limitation
*/
public class PoliteHttpRestClient extends HttpRestClient
{

	/**
	 * Waiting time in milliseconds.
	 */
    private static final long WAIT_TIME = 2000;
    
    /**
     * Last time a request was made.
     */
    private long lastReqTime = 0;
    
    public PoliteHttpRestClient() {
    	super();
    }
    
    public PoliteHttpRestClient(HttpClient httpClient, ResponseHandler<Response> responseHandler) {
    	super(httpClient, responseHandler);
    }

    public Response get(String urlPath, String cookie)
    {
        waitIfNeeded();
        Response resp = super.get(urlPath, cookie);
        noteTime();
        return resp;
    }

    public Response post(String apiParams, String urlPath, String cookie)
    {
        waitIfNeeded();
        Response resp = super.post(apiParams, urlPath, cookie);
        noteTime();
        return resp;
    }

    private void noteTime()
    {
        lastReqTime = System.currentTimeMillis();
    }

    private void waitIfNeeded()
    {
        if (lastReqTime == 0)
            return;

        long elapsed = System.currentTimeMillis() - lastReqTime;

        if (elapsed >= WAIT_TIME)
            return;

        long toWait = WAIT_TIME - elapsed;
        try
        {
            Thread.sleep(toWait);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
}