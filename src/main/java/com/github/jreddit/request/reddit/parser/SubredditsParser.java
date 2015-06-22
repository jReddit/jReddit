package com.github.jreddit.request.reddit.parser;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Kind;
import com.github.jreddit.entity.Subreddit;

public class SubredditsParser {

	private JSONParser jsonParser;
	
	public SubredditsParser() {
		jsonParser = new JSONParser();
	}
	
	public List<Subreddit> parse(String jsonText) throws ParseException {
		
    	// List of subreddits
        List<Subreddit> subreddits = new LinkedList<Subreddit>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        if (!(response instanceof JSONObject)) {
        	return null;
        }
        
        JSONObject object = (JSONObject) response;
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        // Iterate over the subreddit results
        for (Object element : array) {
            JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
			if (kind != null && kind.equals(Kind.SUBREDDIT.value())) {

                // Create and add subreddit
                data = ((JSONObject) data.get("data"));
                subreddits.add(new Subreddit(data));
                
			}
        }
        
        return subreddits;
        
	}
	
}
