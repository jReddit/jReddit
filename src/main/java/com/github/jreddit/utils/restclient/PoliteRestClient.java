package com.github.jreddit.utils.restclient;

import java.util.concurrent.TimeUnit;

/**
 * Reddit's API access rules require that no more than 1 request is made every 2 seconds
 * (i.e 30 requests per minute). This client makes it easy to track the time, and ensures that
 * the 2 seconds pass between each request. Its a singleton class, use PoliteRestClient.get()
 * to get an instance. This is so all classes / requests are limited by the same limitation
 */
public class PoliteRestClient implements RestClient
{
    private final HttpRestClient client = new HttpRestClient();

    private static final long WAIT_TIME = 2000; //millisecnds

    private long lastReqTime = 0;


    private static final PoliteRestClient instance = new PoliteRestClient();

    public static PoliteRestClient get()
    {
        return instance;
    }

    private PoliteRestClient() {}

    @Override
    public Response get(String urlPath, String cookie)
    {
        waitIfNeeded();
        Response resp = client.get(urlPath, cookie);
        noteTime();
        return resp;
    }

    @Override
    public Response post(String apiParams, String urlPath, String cookie)
    {
        waitIfNeeded();
        Response resp =  client.post(apiParams, urlPath, cookie);
        noteTime();
        return resp;
    }

    @Override
    public void setUserAgent(String agent)
    {
        client.setUserAgent(agent);
    }

    private void noteTime()
    {
        lastReqTime = System.nanoTime();
    }

    private void waitIfNeeded()
    {
        if (lastReqTime == 0)
            return;

        long elapsed = System.nanoTime() - lastReqTime;
        elapsed = TimeUnit.NANOSECONDS.toMillis( elapsed );

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

