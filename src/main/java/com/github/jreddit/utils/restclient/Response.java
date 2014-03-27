package com.github.jreddit.utils.restclient;

public interface Response {
    public int getStatusCode();

    public Object getResponseObject();

    public String getResponseText();
}
