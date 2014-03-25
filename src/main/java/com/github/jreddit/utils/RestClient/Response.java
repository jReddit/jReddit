package com.github.jreddit.utils.RestClient;

public interface Response {
    public int getStatusCode();

    public Object getResponseObject();

    public String getResponseText();
}
