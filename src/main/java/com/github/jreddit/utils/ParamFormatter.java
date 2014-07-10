package com.github.jreddit.utils;

public class ParamFormatter {
    
    /**
     * Add a parameter to the parameter list that is appended to a URL.
     * Precondition: current list of parameters is not null.
     * Postcondition: if the value is not a default value (null, empty or -1) it is added to the parameter list.
     * 
     * @param params 	Current parameter list
     * @param name 		Parameter name
     * @param value 	Parameter value
     * 
     * @return 			New parameter list
     */
    public static String addParameter(String params, String name, String value) {
    	assert(params != null);
    	
    	if (value != null && !value.equals("") && !value.equals("-1")) {
    		return params.concat("&" + name + "=" + value);
    	} else {
    		return params;
    	}
    	
    }
    
}
