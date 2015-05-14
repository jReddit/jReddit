package com.github.jreddit.utils.restclient;

import java.util.Locale;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

/**
 * Factory and utility methods for {@link RestClient}
 *
 * @author Andrei Sfat
 */
public class RestClientFactory {

    /** The default scheme is "http". */
    public static final String DEFAULT_SCHEME_NAME = "http";

    public static RestClient newHttpRestClient() {
        return new HttpRestClient();
    }

    public static RestClient newHttpRestClient(HttpClient httpClient) {
        return new HttpRestClient(httpClient, new RestResponseHandler());
    }

    public static RestClient newPoliteHttpRestClient() {
        return new PoliteHttpRestClient();
    }

    public static RestClient newPoliteHttpRestClient(HttpClient httpClient) {
        return new PoliteHttpRestClient(httpClient, new RestResponseHandler());
    }

    public static RestClient newProxyHttpClient(String hostname, int port) {

        return newProxyHttpClient(hostname, port, null);
    }

    public static RestClient newProxyHttpClient(String hostname, int port, String scheme) {
        final RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .setConnectionRequestTimeout(10000).build();
        String schemeName;
        if (scheme != null) {
            schemeName = scheme.toLowerCase(Locale.ENGLISH);
        } else {
            schemeName = DEFAULT_SCHEME_NAME;
        }
        HttpHost proxy = new HttpHost(hostname, port, schemeName);
        final HttpClient httpClient = HttpClients.custom()
                .setProxy(proxy)
                .setDefaultRequestConfig(globalConfig).build();

        return new HttpRestClient(httpClient, new RestResponseHandler());
    }

}
