package com.omrlnr.jreddit;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.omrlnr.jreddit.utils.Utils;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * This class represents a reddit comment
 *
 * @author <a href="https://github.com/jasonsimpson">Jason Simpson</a>
 * 
 */
public class Comment extends Thing {

    public Comment(JSONObject jsonObj) {
        super(jsonObj);
    }

    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        String thing = super.toString(indent);
        return thing + 
            indent + "   Comment:    "   + getBody()     + "\n" +
            indent + "       author: "   + getAuthor()   + "\n" +
            // indent + "       score: "    + getScore()    + "\n" +
            indent + "       up: "       + getUpVotes()  + "\n" +
            indent + "       down: "     + getDownVotes() + "\n";
            // indent + Utils.getJSONDebugString(_data, indent);

    }

    public String getBody() { 
        return (String)((JSONObject)_data.get("data")).get("body");
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

    /**
     * Get the replies to this comment.
     */
    public List<Comment> getReplies() {
        List<Comment> ret = new ArrayList();
       
        
        JSONObject data = (JSONObject)_data.get("data");
        JSONObject replies = (JSONObject)data.get("replies");
        JSONObject replyData = (JSONObject)replies.get("data");
        JSONArray children = (JSONArray)replyData.get("children");

        for (int i = 0; i < children.size(); i++) {
            JSONObject jsonData = (JSONObject)children.get(i);
            Comment comment = new Comment(jsonData);

            //
            // TODO handle this pagination somehow. ???
            //
            if(!comment.getKind().equals("more")) {
                ret.add(new Comment(jsonData));
            }
        }

        return ret;
    }

}
