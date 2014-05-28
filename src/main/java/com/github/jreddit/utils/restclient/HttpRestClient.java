package com.github.jreddit.utils.restclient;

import com.github.jreddit.exception.InvalidURIException;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder.httpGetMethod;
import static com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder.httpPostMethod;

public class HttpRestClient implements RestClient {
    private final HttpClient httpClient;
    private final ResponseHandler<Response> responseHandler;
    private final RequestConfig globalConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
            .setConnectionRequestTimeout(10000)
            .build();
    private String userAgent = "Omer's Reddit API Java Wrapper";

    public HttpRestClient() {
        // As we're currently managing cookies elsewhere we need to set our config to ignore them
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();
        this.responseHandler = new RestResponseHandler();
    }

    public HttpRestClient(HttpClient httpClient, ResponseHandler<Response> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    @Override
    public Response get(String urlPath, String cookie) {
        try {
            return get(httpGetMethod()
                    .withUrl(ApiEndpointUtils.REDDIT_BASE_URL + urlPath)
                    .withCookie(cookie)
            );
        }
        catch (URISyntaxException e) {
            System.err.println("Error making creating URI bad path: " + urlPath);
        }
        catch (InvalidURIException e) {
            System.err.println("Error making GET request to invalid URI path: " + urlPath);
        }
        catch (IOException e) {
            System.err.println("Error making GET request to URL path: " + urlPath);
        }
        catch (ParseException e) {
            System.err.println("Error parsing response from POST request for URL path: " + urlPath);
        }
        return null;
    }

    public Response get(HttpGetMethodBuilder getMethodBuilder) throws IOException, ParseException, InvalidURIException {
        getMethodBuilder.withUserAgent(userAgent);
        HttpGet request = getMethodBuilder.build();

        Response response = httpClient.execute(request, responseHandler);
        if (response.getStatusCode() == 404) {
            throw new InvalidURIException();
        }

        return response;
    }

    @Override
    public Response post(String apiParams, String urlPath, String cookie) {
        try {
            return post(
                    httpPostMethod()
                            .withUrl(ApiEndpointUtils.REDDIT_BASE_URL + urlPath)
                            .withCookie(cookie),
                    convertRequestStringToList(apiParams)
            );
        }
        catch (URISyntaxException e) {
            System.err.println("Error making creating URI bad path: " + urlPath);
        }
        catch (IOException e) {
            System.err.println("Error making GET request to URL path: " + urlPath);
        }
        catch (ParseException e) {
            System.err.println("Error parsing response from POST request for URL path: " + urlPath);
        }
        return null;
    }

    public Response post(HttpPostMethodBuilder postMethodBuilder, NameValuePair... params) throws IOException, ParseException {
        return post(postMethodBuilder, Arrays.asList(params));
    }

    public Response post(HttpPostMethodBuilder postMethodBuilder, List<NameValuePair> params) throws IOException, ParseException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);

        postMethodBuilder.withUserAgent(userAgent);
        HttpPost request = postMethodBuilder.build();
        request.setEntity(entity);
        return httpClient.execute(request, responseHandler);
    }

    @Override
    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }

    private List<NameValuePair> convertRequestStringToList(String apiParams) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (apiParams != null && !apiParams.isEmpty()) {
            String[] valuePairs = apiParams.split("&");
            for (String valuePair : valuePairs) {
                String[] nameValue = valuePair.split("=");
                params.add(new BasicNameValuePair(nameValue[0], nameValue[1]));
            }
        }
        return params;
    }
}
