package com.github.jreddit.parser.util;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {
    
    @Test
    public void testSafeJsonToString() {
        Assert.assertNull(JsonUtils.safeJsonToString(null));
        Assert.assertEquals("123", JsonUtils.safeJsonToString(123));
        Assert.assertEquals("abcd", JsonUtils.safeJsonToString("abcd"));
        Assert.assertEquals("", JsonUtils.safeJsonToString(""));
    }
    
    @Test
    public void testSafeJsonToDouble() {
        Assert.assertNull(JsonUtils.safeJsonToDouble(null));
        Assert.assertNull(JsonUtils.safeJsonToDouble("abcd"));
        Assert.assertNull(JsonUtils.safeJsonToDouble(""));
        Assert.assertEquals((Double) (double) 35141, JsonUtils.safeJsonToDouble("35141"), 0);
        Assert.assertEquals((Double) (double) 0, JsonUtils.safeJsonToDouble("0"), 0);
    }
    
    @Test
    public void testSafeJsonToInteger() {
        Assert.assertNull(JsonUtils.safeJsonToInteger(null));
        Assert.assertNull(JsonUtils.safeJsonToInteger("abcd"));
        Assert.assertNull(JsonUtils.safeJsonToInteger(""));
        Assert.assertEquals((Integer) 355, (Integer) JsonUtils.safeJsonToInteger("355"));
        Assert.assertNull(JsonUtils.safeJsonToInteger("25275738927589278572891"));
        Assert.assertNull(JsonUtils.safeJsonToInteger("-25275738927589278572891"));
        Assert.assertEquals((Integer) 0, JsonUtils.safeJsonToInteger("0"));
    }
    
    @Test
    public void testSafeJsonToBoolean() {
        Assert.assertNull(JsonUtils.safeJsonToBoolean(null));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("abcd"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean(""));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("3522"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("25275738927589278572891"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("-25275738927589278572891"));
        Assert.assertTrue(JsonUtils.safeJsonToBoolean("true"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("false"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("0"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("1"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("yes"));
        Assert.assertFalse(JsonUtils.safeJsonToBoolean("no"));
    }
    
    @Test
    public void testSafeJsonToLong() {
        Assert.assertNull(JsonUtils.safeJsonToLong(null));
        Assert.assertNull(JsonUtils.safeJsonToLong("abcd"));
        Assert.assertNull(JsonUtils.safeJsonToLong(""));
        Assert.assertEquals((Long) (long) 355, (Long) JsonUtils.safeJsonToLong("355"));
        Assert.assertNull(JsonUtils.safeJsonToLong("25275738927589278572891"));
        Assert.assertNull(JsonUtils.safeJsonToLong("-25275738927589278572891"));
        Assert.assertEquals((Long) Long.MAX_VALUE, (Long) JsonUtils.safeJsonToLong("" + Long.MAX_VALUE));
        Assert.assertEquals((Long) Long.MIN_VALUE, (Long) JsonUtils.safeJsonToLong("" + Long.MIN_VALUE));
        Assert.assertEquals((Long) (long) 0, JsonUtils.safeJsonToLong("0"));
    }
    
}
