package com.github.jreddit.oauth.param;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RedditScopeBuilderTest {

    RedditScopeBuilder builder;
    
    @Before
    public void setup() {
        builder = new RedditScopeBuilder();
    }
    
    @Test
    public void testEmpty() {
        assertEquals("", builder.build());
    }
    
    @Test
    public void testAddRemove() {
        builder.addScope(RedditScope.EDIT);
        builder.removeScope(RedditScope.EDIT);
        assertEquals("", builder.build());
    }
    
    @Test
    public void testAddRemoveMultiple() {
        builder.addScopes(RedditScope.EDIT, RedditScope.MODPOSTS);
        builder.removeScopes(RedditScope.EDIT, RedditScope.MODPOSTS, RedditScope.MODCONFIG);
        assertEquals("", builder.build());
    }
    
    @Test
    public void testAdd() {
        builder.addScope(RedditScope.EDIT);
        assertEquals(RedditScope.EDIT.value(), builder.build());
        builder.removeScope(RedditScope.EDIT);
    }
    
    @Test
    public void testAddDouble() {
        builder.addScopes(RedditScope.EDIT, RedditScope.EDIT);
        builder.addScope(RedditScope.EDIT);
        assertEquals(RedditScope.EDIT.value(), builder.build());
        builder.removeScope(RedditScope.EDIT);
        assertEquals("", builder.build());
    }
    
    @Test
    public void testAddMultiple() {
        builder.addScopes(RedditScope.EDIT, RedditScope.FLAIR);
        assertTrue(
                (RedditScope.EDIT.value() + RedditScope.SEPARATOR + RedditScope.FLAIR.value()).equals(builder.build()) 
                ||
                (RedditScope.FLAIR.value() + RedditScope.SEPARATOR + RedditScope.EDIT.value()).equals(builder.build()) 
                );
        builder.removeScopes(RedditScope.EDIT, RedditScope.FLAIR);
    }
    
    
}
