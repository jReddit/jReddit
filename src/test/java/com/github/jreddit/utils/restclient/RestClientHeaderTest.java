package com.github.jreddit.utils.restclient;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RestClientHeaderTest {

    @Test
    public void hashCodeMatches() {
        RestClientHeader first = new RestClientHeader("cookie", "reddit_session=cookie");
        RestClientHeader second = new RestClientHeader("cookie", "reddit_session=cookie");
        assertTrue(first.hashCode() == second.hashCode());
    }
    
    @Test
    public void hashCodeDiffers() {
        RestClientHeader first = new RestClientHeader("cookie", "reddit_session=cookie");
        RestClientHeader second = new RestClientHeader("cookie", "reddit_session=foo");
        assertFalse(first.hashCode() == second.hashCode());
    }

    @Test
    public void equalityMatches() {
        RestClientHeader first = new RestClientHeader("cookie", "reddit_session=cookie");
        RestClientHeader second = new RestClientHeader("cookie", "reddit_session=cookie");
        assertTrue(first.equals(second));
    }

    @Test
    public void equalityDiffers() {
        RestClientHeader first = new RestClientHeader("cookie", "reddit_session=cookie");
        RestClientHeader second = new RestClientHeader("cookie", "reddit_session=foo");
        assertFalse(first.equals(second));
    }
}
