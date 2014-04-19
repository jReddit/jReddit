package com.github.jreddit.utils.restclient.methodbuilders;

import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder.httpGetMethod;
import static org.junit.Assert.assertTrue;

public class HttpGetMethodBuilderTest {

    private static final String URL = "http://www.example.com";
    private HttpGetMethodBuilder underTest;

    @Before
    public void createUnderTest() {
        underTest = httpGetMethod();
    }

    @Test(expected = URISyntaxException.class)
    public void throwUriSyntaxException() throws URISyntaxException {
        underTest.withUrl("∞¢¥†ƒ¥¨†ƒ∞¢  &^");
    }

    @Test
    public void buildUri() throws URISyntaxException {
        underTest.withUrl(URL);
        HttpGet builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
    }

    @Test
    public void buildUriWithCookie() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withCookie("someCookieString");
        HttpGet builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("cookie").getValue().equals("reddit_session=someCookieString"));
    }

    @Test
    public void buildUriWithUserAgent() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withUserAgent("userAgentString");
        HttpGet builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("User-Agent").getValue().equals("userAgentString"));
    }

    @Test
    public void buildUriWithUserAgentAndCookie() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withCookie("someCookieString");
        underTest.withUserAgent("userAgentString");
        HttpGet builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("cookie").getValue().equals("reddit_session=someCookieString"));
        assertTrue(builtMethod.getFirstHeader("User-Agent").getValue().equals("userAgentString"));
    }
}
