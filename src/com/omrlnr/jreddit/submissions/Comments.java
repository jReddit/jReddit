package com.omrlnr.jreddit.submissions;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.Fullname;
import com.omrlnr.jreddit.user.User;
import com.omrlnr.jreddit.utils.Utils;

/**
 * This class represents a comment
 * 
 * @author Jason Simpson
 *
 */
public class Comments {

    /**
     * This function returns a list of comments
     * 
     * @param linkFullname      The fullname of the link/article/submission
     * @param user              The user
     *
     * @return A list containing Comments
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public static List<Comment> getComments(    String articleId, 
                                                User user) 
                                    throws IOException, ParseException {

        ArrayList<Comment> comments = new ArrayList<Comment>();

        String urlString = "http://www.reddit.com/comments/" + articleId;
        urlString += ".json";

        URL url = new URL(urlString);

        JSONArray array = (JSONArray)Utils.get("", url, user.getCookie());
        
        if(array.size() > 0) { 
            JSONObject replies = (JSONObject)array.get(1);
            JSONObject data = (JSONObject)replies.get("data");
            JSONArray children = (JSONArray)data.get("children");

            for (int i = 0; i < children.size(); i++) {
                JSONObject jsonData = (JSONObject)children.get(i);
                comments.add(new Comment(jsonData));
            }
        }

        return comments;
    }

}
