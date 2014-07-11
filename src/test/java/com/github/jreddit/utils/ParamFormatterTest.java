package com.github.jreddit.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test the static parameter formatter class.
 * 
 * @author Simon Kassing
 */
public class ParamFormatterTest {

	/**
	 * Test response if current list is empty and value is null.
	 */
    @Test
    public void testParamsIsEmptyAndValueNull() {
    	String params = "";
    	String name = "field";
    	String value = null;
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response if current list is empty and value is empty.
	 */
    @Test
    public void testParamsIsEmptyAndValueIsEmpty() {
    	String params = "";
    	String name = "field";
    	String value = "";
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response if current list is empty and value is -1.
	 */
    @Test
    public void testParamsIsEmptyAndValueIsNegativeOne() {
    	String params = "";
    	String name = "field";
    	String value = "-1";
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
	/**
	 * Test response if current list is not empty and value is null.
	 */
    @Test
    public void testParamsIsNotEmptyAndValueNull() {
    	String params = "&a=b";
    	String name = "field";
    	String value = null;
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response current list is not empty and if value is empty.
	 */
    @Test
    public void testParamsIsNotEmptyAndValueIsEmpty() {
    	String params = "&a=b";
    	String name = "field";
    	String value = "";
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response if current list is not empty and value is -1.
	 */
    @Test
    public void testParamsIsNotEmptyAndValueIsNegativeOne() {
    	String params = "&a=b";
    	String name = "field";
    	String value = "-1";
    	assertEquals(params, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response if current list is empty and value is not default.
	 */
    @Test
    public void testParamsIsEmptyAndAdded() {
    	String params = "";
    	String name = "field";
    	String value = "value";
    	assertEquals(params + "&" + name + "=" + value, ParamFormatter.addParameter(params, name, value));
    }
    
    /**
	 * Test response if  current list is not empty and value is not default.
	 */
    @Test
    public void testParamsIsNotEmptyAndAdded() {
    	String params = "&a=b";
    	String name = "field";
    	String value = "value";
    	assertEquals(params + "&" + name + "=" + value, ParamFormatter.addParameter(params, name, value));
    }
	
}
