package com.github.jreddit.comment;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.submissions.Submission;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Kind;
import com.github.jreddit.utils.ParamFormatter;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Deals with Comment-related functionality
 *
 * @author Raul Rene Lepsa
 * @author Simon Kassing
 */
public class Comments {

    private RestClient restClient;

    /**
     * Constructor.
     * @param restClient REST Client instance
     */
    public Comments(RestClient restClient) {
        this.restClient = restClient;
    }
    
    /**
     * Parses a JSON feed from Reddit (URL) into a nice tree set of Comment objects.
     * 
     * @param user		User
     * @param url		URL for the request
     * @param article	Are it comments of an article (submission) or from a listing (e.g. from a user overview)
     * 
     * @return Parsed list of comments.
     */
    public List<Comment> parse(User user, String url, boolean article) {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of submissions
        List<Comment> comments = new LinkedList<Comment>();
        
        // Send request to reddit server via REST client
        Object response = restClient.get(url, cookie).getResponseObject();
        System.out.println(url);
        
        if (response instanceof JSONObject || response instanceof JSONArray) {
        	
	        JSONObject object =  (JSONObject) ( (article) ? ((JSONArray) response).get(1) : response );
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
	        
	        // Iterate over the submission results
	        JSONObject data;
	        Comment comment;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            
	            // Make sure it is of the correct kind
	            String kind = safeJsonToString(data.get("kind"));
	            if (kind.equals(Kind.COMMENT.value())) {
	            	
	            	// Contents of the comment
	            	data = ((JSONObject) data.get("data"));
	            	
		            // Create and add the new comment
		            comment = new Comment(data);
		            comments.add(comment);
		            
	            }

	        }
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of submissions 
        return comments;
        
    }
    
    public List<Comment> parseRecursive(User user, String url) {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of submissions
        List<Comment> comments = new LinkedList<Comment>();
        
        // Send request to reddit server via REST client
        Object response = restClient.get(url, cookie).getResponseObject();
        
        if (response instanceof JSONObject || response instanceof JSONArray) {
        	
	        JSONObject object =  (JSONObject) ((JSONArray) response).get(1);
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
	        
	        // Iterate over the submission results
	        JSONObject data;
	        Comment comment;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            
	            // Make sure it is of the correct kind
	            String kind = safeJsonToString(data.get("kind"));
	            if (kind.equals(Kind.COMMENT.value())) {
	            	
	            	// Contents of the comment
	            	data = ((JSONObject) data.get("data"));
	            	
		            // Create and add the new comment
		            comment = new Comment(data);
		            comments.add(comment);
		            System.out.println("Parent: " + comment);
		            
		            Object o = data.get("replies");
		            if (o instanceof JSONObject) {
		            	
		            	// Dig towards the replies
		            	JSONObject replies = (JSONObject) o;
			            replies = (JSONObject) replies.get("data");
			            JSONArray children = (JSONArray) replies.get("children");
			            
			            for (Object replyObj : children) {
			            	JSONObject reply = (JSONObject) replyObj;
			            	
	
					        kind = safeJsonToString(reply.get("kind"));
				            if (kind.equals(Kind.COMMENT.value())) {
				            	
				            	// Contents of the comment
				            	JSONObject data2 = ((JSONObject) reply.get("data"));
				            	
					            // Create and add the new comment
					            comment = new Comment(data2);
					            comments.add(comment);
					            System.out.println("\t> Reply comment: " + comment);
					            
				            } else if (kind.equals(Kind.MORE.value())) {
				            	
					            replies = (JSONObject) reply.get("data");
					            children = (JSONArray) replies.get("children");
					            System.out.println("\t+ More children: " + children);
				            }
			            
			            }

		            }
		            
	            }

	            System.out.println();
	        }
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of submissions 
        return comments;
        
    }
    
  //  private addComment(List<Comment> list, JSONObject commentObject) {
    	
   // }
    
    /**
     * Get the comment tree of the given user.
     * In this variant all parameters are Strings.
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param username	 		Username of the user you want to retrieve from.
     * @param sort	    		(Optional, set null if not used) Sorting method.
     * @param time		 		(Optional, set null is not used) Time window
     * @param count        		(Optional, set null if not used) Number at which the counter starts
     * @param limit        		(Optional, set null if not used) Integer representing the maximum number of comments to return
     * @param after				(Optional, set null if not used) After which comment to retrieve
     * @param before			(Optional, set null if not used) Before which comment to retrieve
     * @param show				(Optional, set null if not used) Show parameter ('given' is only acceptable value)
     * 
     * @return Comments of a user.
     */
    public List<Comment> ofUser(User user, String username, String sort, String time, String count, String limit, String after, String before, String show) {
        
    	// Format parameters
    	String params = "";
    	params = ParamFormatter.addParameter(params, "sort", sort);
    	params = ParamFormatter.addParameter(params, "time", time);
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	params = ParamFormatter.addParameter(params, "show", show);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.USER_COMMENTS, username, params), false);
        
    }
    
    /**
     * Get the comment tree of the given user (username).
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param username	 		Username of the user you want to retrieve from.
     * @param sort	    		(Optional, set null if not used) Sorting method.
     * @param time		 		(Optional, set null is not used) Time window
     * @param count        		(Optional, set -1 if not used) Number at which the counter starts
     * @param limit        		(Optional, set -1 if not used) Integer representing the maximum number of comments to return
     * @param after				(Optional, set null if not used) After which comment to retrieve
     * @param before			(Optional, set null if not used) Before which comment to retrieve
     * @param show_given		(Optional, set false if not used) Only show the given comments
     * 
     * @return Comments of a user.
     */
    public List<Comment> ofUser(User user, String username, UserOverviewSort sort, SubmissionsSearchTime time, int count, int limit, Comment after, Comment before, boolean show_given) {
       
    	if (username == null || username.isEmpty()) {
    		throw new IllegalArgumentException("The username must be set.");
    	}
        
    	return ofUser(
    			user,
    			username,
    			(sort != null) ? sort.value() : null,
    			(time != null) ? time.value() : null,
    			String.valueOf(count),
    			String.valueOf(limit),
    			(after != null) ? after.getId() : null,
    			(before != null) ? before.getId() : null, 
    			(show_given) ? "given" : null
    	);
    	
    }
    
    /**
     * Get the comment tree of the given user (object).
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param username	 		Username of the user you want to retrieve from.
     * @param sort	    		(Optional, set null if not used) Sorting method.
     * @param time		 		(Optional, set null is not used) Time window
     * @param count        		(Optional, set -1 if not used) Number at which the counter starts
     * @param limit        		(Optional, set -1 if not used) Integer representing the maximum number of comments to return
     * @param after				(Optional, set null if not used) After which comment to retrieve
     * @param before			(Optional, set null if not used) Before which comment to retrieve
     * @param show_given		(Optional, set false if not used) Only show the given comments
     * 
     * @return Comments of a user.
     */
    public List<Comment> ofUser(User user, User target, UserOverviewSort sort, SubmissionsSearchTime time, int count, int limit, Comment after, Comment before, boolean show_given) {
    	return ofUser(user, target.getUsername(), sort, time, count, limit, after, before, show_given);
    }

    /**
     * Get the comment tree from a given submission.
     * In this variant all parameters are Strings.
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param submissionId 		Submission ID36 identifier
     * @param commentId    		(Optional, set null if not used) ID of a comment. If specified, this comment will be the focal point of the returned view.
     * @param parentsShown 		(Optional, set null is not used) An integer between 0 and 8 representing the number of parents shown for the comment identified by <code>commentId</code>
     * @param depth        		(Optional, set null if not used) Integer representing the maximum depth of subtrees in the thread
     * @param limit        		(Optional, set null if not used) Integer representing the maximum number of comments to return
     * @param sort  		(Optional, set null if not used) CommentSort enum indicating the type of sorting to be applied (e.g. HOT, NEW, TOP, etc)
     * @return Comments for an article.
     */
    public List<Comment> ofSubmission(User user, String submissionId, String commentId, String parentsShown, String depth, String limit, String sort) {
    	
    	// Format parameters
    	String params = "";
    	params = ParamFormatter.addParameter(params, "comment", commentId);
    	params = ParamFormatter.addParameter(params, "context", parentsShown);
    	params = ParamFormatter.addParameter(params, "depth", depth);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "sort", sort);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.SUBMISSION_COMMENTS, submissionId, params), true);
        
    }
    
    /**
     * Get the comment tree from a given submission (ID36)
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param submissionId 		Submission ID36 identifier
     * @param commentId    		(Optional, set null if not used) ID of a comment. If specified, this comment will be the focal point of the returned view.
     * @param parentsShown 		(Optional, set -1 is not used) An integer between 0 and 8 representing the number of parents shown for the comment identified by <code>commentId</code>
     * @param depth        		(Optional, set -1 if not used) Integer representing the maximum depth of subtrees in the thread
     * @param limit        		(Optional, set -1 if not used) Integer representing the maximum number of comments to return
     * @param sort  		(Optional, set null if not used) CommentSort enum indicating the type of sorting to be applied (e.g. HOT, NEW, TOP, etc)
     * @return Comments for an article.
     */
    public List<Comment> ofSubmission(User user, String submissionId, String commentId, int parentsShown, int depth, int limit, CommentSort sort) {
       
    	if (submissionId == null || submissionId.isEmpty()) {
    		throw new IllegalArgumentException("The identifier of the submission must be set.");
    	}
    	
    	if (depth < -1 || depth > 8) {
    		throw new IllegalArgumentException("The depth must be between 1 and 8 (or for default -1).");
    	}
        
    	return ofSubmission(
    			user,
    			submissionId,
    			commentId,
    			String.valueOf(parentsShown),
    			String.valueOf(depth),
    			String.valueOf(limit),
    			sort.value()
    	);
    	
    }
    
    /**
     * Get the comment tree from a given submission (object).
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param submission 		Submission object
     * @param commentId    		(Optional, set null if not used) ID of a comment. If specified, this comment will be the focal point of the returned view.
     * @param parentsShown 		(Optional, set -1 is not used) An integer between 0 and 8 representing the number of parents shown for the comment identified by <code>commentId</code>
     * @param depth        		(Optional, set -1 if not used) Integer representing the maximum depth of subtrees in the thread
     * @param limit        		(Optional, set -1 if not used) Integer representing the maximum number of comments to return
     * @param sort  		(Optional, set null if not used) CommentSort enum indicating the type of sorting to be applied (e.g. HOT, NEW, TOP, etc)
     * @return Comments for an article.
     */
    public List<Comment> ofSubmission(User user, Submission submission, String commentId, int parentsShown, int depth, int limit, CommentSort sort) {
        return ofSubmission(user, submission.getIdentifier(), commentId, parentsShown, depth, limit, sort);
    }
    
}
