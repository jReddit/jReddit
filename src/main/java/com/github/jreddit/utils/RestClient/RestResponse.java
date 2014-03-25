package com.github.jreddit.utils.RestClient;

import org.apache.http.HttpResponse;

public class RestResponse implements Response {
    private String responseText;
    private Object responseObject;
    private HttpResponse httpResponse;

    public RestResponse(String responseText, Object responseObject, HttpResponse httpResponse) {
        this.responseText = responseText;
        this.responseObject = responseObject;
        this.httpResponse = httpResponse;
    }

    @Override
    public Object getResponseObject() {
        return responseObject;
    }

    @Override
    public String getResponseText() {
        return responseText;
    }

    @Override
    public int getStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
