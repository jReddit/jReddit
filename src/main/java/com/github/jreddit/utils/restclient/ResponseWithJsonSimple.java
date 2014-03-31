package com.github.jreddit.utils.restclient;

public interface ResponseWithJsonSimple extends BasicResponse{

    /**
     * @return <code>Object</code> the JSONSimple interpretation of the response body
     */
    public Object getResponseObject();
}
