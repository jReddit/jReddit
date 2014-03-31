package com.github.jreddit.restclient.methodbuilders;

import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static com.github.jreddit.restclient.methodbuilders.HttpPostMethodBuilder.httpPostMethod;
import static org.junit.Assert.assertFalse;
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

    @Test
    public void hashCodeIsSameWithSameURL() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL);
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL);
        assertTrue(first.hashCode() == second.hashCode());
    }

    @Test
    public void hashCodeIsDifferentWithDifferentURL() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL);
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL + "foo");
        assertFalse(first.hashCode() == second.hashCode());
    }

    @Test
    public void hashCodeIsSameWithSameCookie() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL).withCookie("cookie");
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL).withCookie("cookie");
        assertTrue(first.hashCode() == second.hashCode());
    }

    @Test
    public void hashCodeIsDifferentWithDifferentCookie() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL).withCookie("cookie");
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL).withCookie("foo");
        assertFalse(first.hashCode() == second.hashCode());
    }

    @Test
    public void equalityWithSameURL() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL);
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL);
        assertTrue(first.equals(second));
    }

    @Test
    public void inequalityWithDifferentURL() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL);
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL + "foo");
        assertFalse(first.equals(second));
    }

    @Test
    public void equalityWithSameCookie() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL).withCookie("cookie");
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL).withCookie("cookie");
        assertTrue(first.equals(second));
    }

    @Test
    public void inequalityWithDifferentCookie() throws URISyntaxException {
        HttpPostMethodBuilder first = httpPostMethod().withUrl(URL).withCookie("cookie");
        HttpPostMethodBuilder second = httpPostMethod().withUrl(URL).withCookie("foo");
        assertFalse(first.equals(second));
    }
}
