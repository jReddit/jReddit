package com.github.jreddit.utils.restclient;

public interface Response {

    /**
     * @return <code>int</code> the Http response code
     */
    public int getStatusCode();

    /**
     * @return <code>Object</code> the JSONSimple interpretation of the response body
     */
    public Object getResponseObject();

    /**
     * @return <code>String</code> the response body
     */
    public String getResponseText();
}
