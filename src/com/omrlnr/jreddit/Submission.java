package com.omrlnr.jreddit;

import org.json.simple.JSONObject;

import com.omrlnr.jreddit.utils.Utils;

/**
 *
 * This class represents a reddit Submission or "Article"
 * 
 */
public class Submission {

    private JSONObject _jsonObject;

    public Submission(JSONObject jsonObj) {
        _jsonObject = jsonObj;
    }

    public String toString() {
        return  "Submission: " + getTitle()     + "\n" +
                    "   score: " + getScore()   + "\n" +
                    "   up: " + getUpVotes()    + "\n" +
                    "   down: " + getDownVotes() + "\n" +
                    "   author: " + getAuthor() + "\n" +
                    "   id: " + getId() ;

        // return _jsonObject.toString();
        // return Utils.getJSONDebugString(_jsonObject, "");
    }

    public String getUrl() { 
        return (String)((JSONObject)_jsonObject.get("data")).get("url");
    }

    public long getUpVotes() { 
        return (Long)((JSONObject)_jsonObject.get("data")).get("ups");
    }

    public long getDownVotes() { 
        return (Long)((JSONObject)_jsonObject.get("data")).get("downs");
    }

    public long getScore() { 
        return (Long)((JSONObject)_jsonObject.get("data")).get("score");
    }

    public String getId() { 
        return (String)((JSONObject)_jsonObject.get("data")).get("id");
    }

    public String getAuthor() { 
        return (String)((JSONObject)_jsonObject.get("data")).get("author");
    }

    public String getTitle() { 
        return (String)((JSONObject)_jsonObject.get("data")).get("title");
    }


    /**
     * This class should provide convenience methods for 
     * obtaining Submission related data. But if the underlying
     * json data changes or we do not provide the caller with
     * the required methods, they can obtain all underlying data using
     * this method.
     */
    public JSONObject getJSONObject() { return _jsonObject; }


}
