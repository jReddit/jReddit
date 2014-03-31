package com.github.jreddit.restclient;

public class RedditResponse implements BasicResponse {

    private String responseBody;
    private int statusCode;

    public RedditResponse(String responseBody, int statusCode) {
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getResponseBody() {
        return responseBody;
    }
}
