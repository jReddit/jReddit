package com.github.jreddit.utils.restclient;

import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.List;

public interface MethodBuilderRestClient {

    /**
     * Perform a get request to the Url specified using the cookie specified
     *
     * @param getMethodBuilder A methodbuilder for the request
     *
     * @return <code>Response</code> an object conforming to the Response interface
     */
    public BasicResponse get(HttpGetMethodBuilder getMethodBuilder) throws IOException;

    /**
     * Perform a post request to the Url specified using the cookie specified
     *
     * @param postMethodBuilder A methodbuilder for the request
     * @param params A list of NameValuePair to be posted to the url
     *
     * @return <code>Response</code> an object conforming to the Response interface
     */
    public BasicResponse post(HttpPostMethodBuilder postMethodBuilder, List<NameValuePair> params) throws IOException;


    /**
     * Set the userAgent to be used when making http requests
     *
     * @param agent the string to be used as the userAgent
     */
    public void setUserAgent(String agent);
}
