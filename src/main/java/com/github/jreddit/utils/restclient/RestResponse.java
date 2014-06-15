package com.github.jreddit.utils.restclient;

import org.apache.http.HttpResponse;

public class RestResponse implements Response {
    private final String responseText;
    private final Object responseObject;
    private final HttpResponse httpResponse;

    public RestResponse(String responseText, Object responseObject, HttpResponse httpResponse) {
        this.responseText = responseText;
        this.responseObject = responseObject;
        this.httpResponse = httpResponse;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public String getResponseText() {
        return responseText;
    }

    public int getStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
