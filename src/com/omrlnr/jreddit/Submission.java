package com.omrlnr.jreddit;

import org.json.simple.JSONObject;

import com.omrlnr.jreddit.utils.Utils;

/**
 *
 * This class represents a reddit Submission or "Article"
 *
 * @author <a href="https://github.com/jasonsimpson">Jason Simpson</a>
 * 
 */
public class Submission extends Thing {

    public Submission(JSONObject data) {
        super(data);
    }

    public String toString() {
        String thing = super.toString();
        return thing +
                "   Submission: "   + getTitle()        + "\n" +
                "       author: "   + getAuthor()       + "\n" +
                "       url:    "   + getUrl()          + "\n" +
                "       score:  "   + getScore()        + "\n" +
                "       up:     "   + getUpVotes()      + "\n" +
                "       down:   "   + getDownVotes()    + "\n";
                // Utils.getJSONDebugString(_data);

    }

    public String getUrl() { 
        return (String)((JSONObject)_data.get("data")).get("url");
    }

    public long getUpVotes() { 
        return (Long)((JSONObject)_data.get("data")).get("ups");
    }

    public long getDownVotes() { 
        return (Long)((JSONObject)_data.get("data")).get("downs");
    }

    public long getScore() { 
        return (Long)((JSONObject)_data.get("data")).get("score");
    }

    public String getAuthor() { 
        return (String)((JSONObject)_data.get("data")).get("author");
    }

    public String getTitle() { 
        return (String)((JSONObject)_data.get("data")).get("title");
    }


}
