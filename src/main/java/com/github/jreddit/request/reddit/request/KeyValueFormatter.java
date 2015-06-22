package com.github.jreddit.request.reddit.request;

import java.util.Map;
import java.util.Set;

public class KeyValueFormatter {

	public static String format(Map<String, String> keyValueParameters) {
		
		// Key set
		Set<String> keys = keyValueParameters.keySet();
		
		// Iterate over keys
		String paramsString = "";
		boolean start = true;
		for (String key : keys) {
			
			// Add separation ampersand
			if (!start) {
				paramsString = paramsString.concat("&");
			} else {
				start = false;
			}
			
			// Add key-value pair
			paramsString = paramsString.concat(key + "=" + keyValueParameters.get(key));
			
		}
		
		// Return final parameters
		return paramsString;
		
	}
	
}
