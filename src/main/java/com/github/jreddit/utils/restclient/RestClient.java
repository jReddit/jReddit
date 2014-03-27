package com.github.jreddit.utils.restclient;

public interface RestClient {
    public Response get(String urlPath, String cookie);

    public Response post(String apiParams, String urlPath, String cookie);

    public void setUserAgent(String agent);
}
