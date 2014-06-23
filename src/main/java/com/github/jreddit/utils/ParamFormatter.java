package com.github.jreddit.utils;

public class ParamFormatter {
    
    /**
     * Add a parameter to the parameter list that is appended to a URL.
     * @param params 	Current parameter list
     * @param name 		Parameter name
     * @param value 	Parameter value
     * @return 			New parameter list
     */
    public static String addParameter(String params, String name, String value) {
    	if (value != null && !value.equals("") && !value.equals("-1")) {
    		return params.concat("&" + name + "=" + value);
    	} else {
    		return params;
    	}
    }
    
}
