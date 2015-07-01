package com.github.jreddit.parser.entity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KindTest {
   
    @Test
    public void testMatchSuccess() {
        Assert.assertEquals(Kind.COMMENT, Kind.match(Kind.COMMENT.value()));
    }
    
    @Test
    public void testMatchFailure() {
        // Match a string that most likely will never become a Kind's value
        Assert.assertNull(Kind.match("djkaskjsf7s98f989389589a9f8a998935"));
    }
    
}
