package com.github.jreddit.comment;


import com.github.jreddit.exception.InvalidUserException;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Kind;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                    comment = CommentMapper.mapMessage(obj);

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


    /**
     * Get the comment tree from a given link article
     *
     * @param subreddit    the subreddit in which the article resides
     * @param articleLink  the ID of the article
     * @param commentId    (Optional) ID of a comment. If specified, this comment will be the focal point of the returned view
     * @param parentsShown an integer between 0 and 8 representing the number of parents shown for the comment identified by <code>commentId</code>
     * @param depth        (Optional) integer representing the maximum depth of subtrees in the thread
     * @param limit        (Optional) integer representing the maximum number of comments to return
     * @param commentSort  CommentSort enum indicating the type of sorting to be applied (e.g. HOT, NEW, TOP, etc)
     * @return Comments for an article
     */
    public List<Comment> commentsFromArticle(String subreddit, String articleLink, String commentId, int parentsShown, Integer depth, Integer limit, CommentSort commentSort) {
        String parameters = "";
        if (depth != null) {
            parameters += "&depth=" + depth;
        }
        if (limit != null) {
            parameters += "&limit=" + limit;
        }
        if (commentSort != null) {
            parameters += "&sort=" + commentSort.getValue();
        }
        if (commentId != null) {
            parameters += "&comment=" + commentId;
        }
        parameters += "&context=" + parentsShown;

        Response response = restClient.get(String.format(ApiEndpointUtils.SUBMISSION_COMMENTS, subreddit, articleLink, parameters), null);
        List<Comment> comments = new ArrayList<Comment>();

        if (response != null) {

            // The Response Object is an array of 2 elements:
            // the first contains details about the post, while the second the Comments we are interested in
            JSONObject data = (JSONObject) ((JSONArray) response.getResponseObject()).get(1);

            // The data is a map that holds on the first child the actual data and on the second the kind of the data (i.e. Listing)
            JSONArray children = (JSONArray) ((JSONObject) data.get("data")).get("children");

            JSONObject obj;
            for (Object aChildren : children) {

                // Get the object containing the comment
                obj = (JSONObject) aChildren;

                // We are only interested in comments
                if (obj.get("kind").equals(Kind.COMMENT.getValue())) {
                    obj = (JSONObject) obj.get("data");

                    // Create a new comment and add it to the submissions list
                    comments.add(CommentMapper.mapMessage(obj));
                }
            }

        }

        return comments;
    }
}
