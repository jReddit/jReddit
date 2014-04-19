package com.github.jreddit.utils.restclient.methodbuilders;

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder.httpPostMethod;
import static org.junit.Assert.assertTrue;

public class HttpPostMethodBuilderTest {

    private static final String URL = "http://www.example.com";
    private HttpPostMethodBuilder underTest;

    @Before
    public void createUnderTest() {
        underTest = httpPostMethod();
    }

    @Test(expected = URISyntaxException.class)
    public void throwUriSyntaxException() throws URISyntaxException {
        underTest.withUrl("∞¢¥†ƒ¥¨†ƒ∞¢  &^");
    }

    @Test
    public void buildUri() throws URISyntaxException {
        underTest.withUrl(URL);
        HttpPost builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
    }

    @Test
    public void buildUriWithCookie() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withCookie("someCookieString");
        HttpPost builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("cookie").getValue().equals("reddit_session=someCookieString"));
    }

    @Test
    public void buildUriWithUserAgent() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withUserAgent("userAgentString");
        HttpPost builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("User-Agent").getValue().equals("userAgentString"));
    }

    @Test
    public void buildUriWithUserAgentAndCookie() throws URISyntaxException {
        underTest.withUrl(URL);
        underTest.withCookie("someCookieString");
        underTest.withUserAgent("userAgentString");
        HttpPost builtMethod = underTest.build();
        assertTrue(builtMethod.getURI().toString().equals(URL));
        assertTrue(builtMethod.getFirstHeader("cookie").getValue().equals("reddit_session=someCookieString"));
        assertTrue(builtMethod.getFirstHeader("User-Agent").getValue().equals("userAgentString"));
    }
}
