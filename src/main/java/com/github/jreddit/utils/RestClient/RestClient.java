package com.github.jreddit.utils.RestClient;

import org.json.simple.JSONObject;

public interface RestClient {
    public Object get(String urlPath, String cookie);

    public JSONObject post(String apiParams, String urlPath, String cookie);

    public void setUserAgent(String agent);
}
