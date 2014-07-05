package com.github.jreddit.action;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmissionActions {

    private RestClient restClient;
    private User user;

    /**
     * Constructor. Global default user (null) is used.
     * @param restClient REST Client instance
     */
    public SubmissionActions(RestClient restClient) {
        this.restClient = restClient;
        this.user = null;
    }
    
    /**
     * Constructor.
     * @param restClient REST Client instance
     * @param actor User instance
     */
    public SubmissionActions(RestClient restClient, User actor) {
    	this.restClient = restClient;
        this.user = actor;
    }
    
    /**
     * Switch the current user for the new user who will
     * be used when invoking retrieval requests.
     * 
     * @param new_actor New user
     */
    public void switchActor(User new_actor) {
    	this.user = new_actor;
    }
    

    /**
     * This function comments on this submission saying the comment specified in
     * <code>text</code> (CAN INCLUDE MARKDOWN).
     *
     * @param subm Submission
     * @param text The text to comment
     */
    public void comment(Submission subm, String text) throws IOException, ParseException {
        JSONObject object = (JSONObject) restClient.post("thing_id=" + subm.getFullName() + "&text=" + text
                + "&uh=" + user.getModhash(), ApiEndpointUtils.SUBMISSION_COMMENT, user.getCookie()).getResponseObject();

        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            throw new InvalidCookieException("Cookie not present");
        } else {
            System.out.println("Commented on thread id " + subm.getFullName()
                    + " saying: \"" + text + "\"");
        }
    }

    /** 
     * Mark a post as NSFW 
     * @param subm Submission
    **/
    public void markNSFW(Submission subm) {
        restClient.post(
                "id=" + subm.getFullName() + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, user.getCookie());
    }

    /** 
     * Unmark a post as NSFW 
     * 
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
    **/
    public void unmarkNSFW(Submission subm) {
        restClient.post(
                "id=" + subm.getFullName() + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, user.getCookie());
    }

    /**
     * This function upvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public boolean upVote(Submission subm) {
        JSONObject object = voteResponse(subm, 1);
        if (!(object.toJSONString().length() > 2)) {
            // Will return "{}"
            System.out.println("Successful upvote!");
            return true;
        } else {
            System.out.println(object.toJSONString());
            return false;
        }
    }

    /**
     * This function downvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public boolean downVote(Submission subm) {
        JSONObject object = voteResponse(subm, -1);
        if (!(object.toJSONString().length() > 2)) {
            System.out.println("Successful downvote!");
            return true;
        } else {
            System.out.println(object.toJSONString());
            return false;
        }
    }

    /**
     * This function saves a submission with a specific category (Reddit Gold feature).
     *
     * @param category - a category name
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void save(Submission subm, String category) {
        restClient.post(
                "category=" + category + "&id=" + subm.getFullName() + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_SAVE, user.getCookie());
    }

    /**
     * This function saves a submission with no category.
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void save(Submission subm) {
        restClient.post(
                "id=" + subm.getFullName() + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_SAVE, user.getCookie());
    }

    /**
     * This function unsaves a submission.
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void unsave(Submission subm) throws IOException, ParseException {
        restClient.post(
                "id=" + subm.getFullName() + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNSAVE, user.getCookie());
    }

    /**
     * Upvote/downvote a submission 
     * 
     * @param dir 				Direction (precondition: either 1 or -1)
     * @return Response from reddit.
     */
    private JSONObject voteResponse(Submission subm, int dir) {
        return (JSONObject) restClient.post(
                "id=" 		+ subm.getFullName() + 
                "&dir=" 	+ dir + 
                "&uh=" 		+ user.getModhash(),
                ApiEndpointUtils.SUBMISSION_VOTE, 
                user.getCookie()
         ).getResponseObject();
    }
    
}
