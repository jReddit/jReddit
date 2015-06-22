package com.github.jreddit.utils.restclient;

import static com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder.httpGetMethod;
import static com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder.httpPostMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.apache.http.protocol.HTTP;
import org.json.simple.parser.ParseException;

import com.github.jreddit.exception.ActionFailedException;
import com.github.jreddit.exception.InvalidURIException;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;

/**
 * HTTP implementation of the REST Client interface.
 *
 * @author Simon Kassing
 */
public class HttpRestClient implements RestClient {

    private final HttpClient httpClient;

    /**
     * Response handler instance.
     */
    private final ResponseHandler<Response> responseHandler;

    /**
     * Default User Agent
     */
    private String userAgent = "jReddit: Reddit API Wrapper for Java";

    public HttpRestClient() {
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectionRequestTimeout(10000).build();
        this.httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        this.responseHandler = new RestResponseHandler();
    }

    /**
     * Constructor with option to define own client and response handler.
     *
     * @param httpClient      HTTP Client
     * @param responseHandler HTTP Request response handler
     */
    public HttpRestClient(HttpClient httpClient, ResponseHandler<Response> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }

    public Response get(String urlPath, String cookie) throws RetrievalFailedException {

        try {
            Response result = get(httpGetMethod().withUrl(ApiEndpointUtils.REDDIT_BASE_URL + urlPath).withCookie(cookie));
            if (result == null) {
                throw new RetrievalFailedException("The given URI path does not exist on Reddit: " + urlPath);
            } else {
                return result;
            }
        } catch (URISyntaxException e) {
            throw new RetrievalFailedException("The syntax of the URI path was incorrect: " + urlPath);
        } catch (InvalidURIException e) {
            throw new RetrievalFailedException("The URI path was invalid: " + urlPath);
        } catch (IOException e) {
            throw new RetrievalFailedException("Input/output failed when retrieving from URI path: " + urlPath);
        } catch (ParseException e) {
            throw new RetrievalFailedException("Failed to parse the response from GET request to URI path: " + urlPath);
        }
    }

    public Response get(HttpGetMethodBuilder getMethodBuilder) throws IOException, ParseException, InvalidURIException {

        // Build HTTP GET request
        getMethodBuilder.withUserAgent(userAgent);
        HttpGet request = getMethodBuilder.build();

        // Execute request
        Response response = httpClient.execute(request, responseHandler);

        // A HTTP error occurred
        if (response != null && response.getStatusCode() >= 300) {
            throw new RetrievalFailedException("HTTP Error (" + response.getStatusCode() + ") occurred for URI path: " + request.getURI().toString());
        }

        return response;
    }

    public Response post(String apiParams, String urlPath, String cookie) {

        try {
            Response result = post(
                    httpPostMethod()
                            .withUrl(ApiEndpointUtils.REDDIT_BASE_URL + urlPath)
                            .withCookie(cookie),
                    convertRequestStringToList(apiParams)
            );
            if (result == null) {
                throw new ActionFailedException("Due to unknown reasons, the response was undefined for URI path: " + urlPath);
            } else {
                return result;
            }
        } catch (URISyntaxException e) {
            throw new ActionFailedException("The syntax of the URI path was incorrect: " + urlPath);
        } catch (IOException e) {
            throw new ActionFailedException("Input/output failed when retrieving from URI path: " + urlPath);
        } catch (ParseException e) {
            throw new ActionFailedException("Failed to parse the response from GET request to URI path: " + urlPath);
        }

    }

    public Response post(HttpPostMethodBuilder postMethodBuilder, NameValuePair... params) throws IOException, ParseException {
        return post(postMethodBuilder, Arrays.asList(params));
    }

    public Response post(HttpPostMethodBuilder postMethodBuilder, List<NameValuePair> params) throws IOException, ParseException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);

        // Assign user agent
        postMethodBuilder.withUserAgent(userAgent);

        // Set entity
        HttpPost request = postMethodBuilder.build();
        request.setEntity(entity);

        // Execute request
        Response response = httpClient.execute(request, responseHandler);

        // A HTTP error occurred
        if (response != null && response.getStatusCode() >= 300) {
            throw new ActionFailedException("HTTP Error (" + response.getStatusCode() + ") occurred for URI path: " + request.getURI().toString());
        }

        return response;

    }

    /**
     * Convert a API parameters to a appropriate list.
     *
     * @param apiParams Input string, for example 'a=2894&b=194'
     * @return List of name value pairs to pass with the POST request
     */
    private List<NameValuePair> convertRequestStringToList(String apiParams) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (apiParams != null && !apiParams.isEmpty()) {
            String[] valuePairs = apiParams.split("&");
            for (String valuePair : valuePairs) {
                String[] nameValue = valuePair.split("=");
                if (nameValue.length == 1) { //there is no cookie if we are not signed in
                    params.add(new BasicNameValuePair(nameValue[0], ""));
                } else {
                    params.add(new BasicNameValuePair(nameValue[0], nameValue[1]));
                }
            }
        }
        return params;
    }

}
