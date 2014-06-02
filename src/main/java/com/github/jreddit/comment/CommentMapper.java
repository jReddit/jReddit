package com.github.jreddit.comment;

import org.json.simple.JSONObject;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToInteger;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

/**
 * Map a JSON response comment to a Comment class
 *
 * @author Raul Rene Lepsa
 */
public class CommentMapper {

    /**
     * Map a JSON object to a <code>Comment</code> class
     *
     * @param obj JSON object to map
     * @return <code>Comment</code> object instance, or NULL if an error occurs during mapping
     */
    public static Comment mapMessage(JSONObject obj) {
        Comment comment = null;

        try {
            comment = new Comment();
            comment.setId(safeJsonToString(obj.get("id")));
            comment.setAuthor(safeJsonToString(obj.get("author")));
            comment.setFullname(safeJsonToString(obj.get("name")));
            comment.setParentId(safeJsonToString(obj.get("parent_id")));
            comment.setBody(safeJsonToString(obj.get("body")));
            comment.setEdited(safeJsonToString(obj.get("edited")));
            comment.setCreated(safeJsonToString(obj.get("created_utc")));
            comment.setReplies(safeJsonToString(obj.get("replies")));
            comment.setUpvotes(safeJsonToInteger(obj.get("ups")));
            comment.setDownvotes(safeJsonToInteger(obj.get("downs")));

        } catch (Exception e) {
            System.err.println("Error mapping JSONObject to Comment");
        }

        return comment;
    }
}
