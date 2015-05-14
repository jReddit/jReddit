package com.github.jreddit.utils.restclient;

public interface Response {

    /**
     * @return <code>int</code> the Http response code
     */
    int getStatusCode();

    /**
     * @return <code>Object</code> the JSONSimple interpretation of the response body
     */
    Object getResponseObject();

    /**
     * @return <code>String</code> the response body
     */
    String getResponseText();
}
