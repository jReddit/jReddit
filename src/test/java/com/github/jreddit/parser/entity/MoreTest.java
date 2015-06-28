package com.github.jreddit.parser.entity;

import junit.framework.Assert;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class MoreTest {
    
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor() {
        
        // Variables
        long count = 2894;
        String parent_id = "djk9fa";
        String child_id_1 = "ddafe2";
        String child_id_2 = "ddaf22";
        
        // Create JSON Object
        JSONObject data = new JSONObject();
        data.put("count", count);
        data.put("parent_id", parent_id);
        JSONArray array = new JSONArray();
        array.add(child_id_1);
        array.add(child_id_2);
        data.put("children", array);
        
        // Parse
        More m = new More(data);
        
        Assert.assertEquals((Long) count, m.getCount());
        Assert.assertEquals(parent_id, m.getParentId());
        Assert.assertEquals(2, m.getChildrenSize());
        Assert.assertEquals(child_id_1, m.getChildren().get(0));
        Assert.assertEquals(child_id_2, m.getChildren().get(1));
        
        // Test that the toString does not throw an exception an is not null
       Assert.assertNotNull(m.toString());
        
    }
    
}
