package com.github.jreddit.request.util;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyValueFormatterTest {
    
    @Test
    public void testFormatEmpty() {
        HashMap<String, String> params = new HashMap<String, String>();
        Assert.assertEquals("", KeyValueFormatter.format(params, false));
        Assert.assertEquals("", KeyValueFormatter.format(params, true));
    }
    
    @Test
    public void testFormatSingleNoUTF8() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("a ", "b,");
        Assert.assertEquals("a =b,", KeyValueFormatter.format(params, false));
    }
    
    @Test
    public void testFormatSingleUTF8() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("a ", "b, ");
        Assert.assertEquals("a =b%2C+", KeyValueFormatter.format(params, true));
    }
    
    @Test
    public void testFormatMultipleUTF8() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("a ", "b, ");
        params.put("c", "32626&");

        String s = KeyValueFormatter.format(params, true);
        Assert.assertTrue(
                ("a =b%2C+&c=32626%26").equals(s)
                ||
                ("c=32626%26&a =b%2C+").equals(s)
                );
    }
    
    @Test
    public void testFormatMultiple() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("a", "b");
        params.put("a", "b");
        params.put("b", "c");

        String s = KeyValueFormatter.format(params, true);
        Assert.assertTrue(
                ("a=b&b=c").equals(s)
                ||
                ("b=c&a=b").equals(s)
                );
    }
    
    @Test
    public void testFormatCommaSeparatedListEmpty() {
        ArrayList<String> list = new ArrayList<String>();
        Assert.assertEquals("", KeyValueFormatter.formatCommaSeparatedList(list));
    }
    
    @Test
    public void testFormatCommaSeparatedListSingle() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        Assert.assertEquals("a", KeyValueFormatter.formatCommaSeparatedList(list));
    }
    
    @Test
    public void testFormatCommaSeparatedListMultiple() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        Assert.assertEquals("a,b,c,d", KeyValueFormatter.formatCommaSeparatedList(list));
    }
    
}
