package com.github.jreddit.comment;


import com.github.jreddit.exception.InvalidUserException;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

/**
 * Deals with Comment-related functionality
 *
 * @author Raul Rene Lepsa
 */
public class Comments {

    private RestClient restClient;

    public Comments(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Returns a list of comments made by a user
     *
     * @param username The username of the user whose comments you want to retrieve.
     * @return <code>List</code> of top 500 comments made by this user, or an empty list if the user does not have any comments.
     * @throws com.github.jreddit.exception.InvalidUserException in case of a non-existent username
     */
    public List<Comment> comments(String username) throws InvalidUserException {
        return comments(username, CommentSort.NEW);
    }

    /**
     * Returns a list of comments made by a user
     *
     * @param username    The username of the user whose comments you want to retrieve.
     * @param commentSort <code>CommentSort</code> instance representing what type of comments are being retrieved
     * @return <code>List</code> of top 500 comments made by this user, or an empty list if the user does not have any comments.
     * @throws com.github.jreddit.exception.InvalidUserException in case of a non-existent username
     */
    public List<Comment> comments(String username, CommentSort commentSort) throws InvalidUserException {
        List<Comment> comments = new ArrayList<Comment>(500);

        try {
            JSONObject object = (JSONObject) restClient.get(String.format(ApiEndpointUtils.USER_COMMENTS,
                    username, commentSort.getValue()), null);

            if (object != null) {
                JSONObject data = (JSONObject) object.get("data");
                JSONArray children = (JSONArray) data.get("children");

                JSONObject obj;
                Comment comment;
                for (Object aChildren : children) {
                    // Get the object containing the comment
                    obj = (JSONObject) aChildren;
                    obj = (JSONObject) obj.get("data");

                    // Create a new comment
                    comment = new Comment(safeJsonToString(obj.get("body")), safeJsonToString(obj.get("edited")),
                            safeJsonToString(obj.get("created_utc")), safeJsonToString(obj.get("replies")),
                            Integer.parseInt(safeJsonToString(obj.get("ups"))),
                            Integer.parseInt(safeJsonToString(obj.get("downs"))));

                    // Add it to the submissions list
                    comments.add(comment);
                }
            } else {
                throw new InvalidUserException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
}
