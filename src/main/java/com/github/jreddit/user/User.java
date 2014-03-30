package com.github.jreddit.user;

import com.github.jreddit.Thing;
import com.github.jreddit.model.json.response.*;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import com.github.jreddit.utils.restclient.RedditServices;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.jreddit.utils.CommentSort.NEW;
import static com.github.jreddit.utils.restclient.submissionbuilders.LinkPostBuilder.linkPost;
import static com.github.jreddit.utils.restclient.submissionbuilders.SelfPostBuilder.selfPost;

/**
 * This class represents a user connected to Reddit.
 *
 * @author Omer Elnour
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Benjamin Jakobus
 * @author Evin Ugur
 * @author Andrei Sfat
 */
public class User extends Thing {

    private final String username;
    private final RedditServices redditServices;
    private String modhash, cookie, password;

    public User(RedditServices redditServices, String username, String password) {
        this.redditServices = redditServices;
        this.username = username;
        this.password = password;
    }

    public String getModhash() {
        return modhash;
    }

    public String getCookie() {
        return cookie;
    }

    /**
     * Call this function to connect the user. <br /> By "connect" I mean
     * effectively sending a POST request to reddit and getting the modhash and
     * cookie, which are required for most reddit API functions.
     *
     * @throws Exception If connection fails.
     */
    public void connect() throws Exception {
        UserLogin userLogin = redditServices.userLogin(username, password);
        this.modhash = userLogin.getJson().getData().getModhash();
        this.cookie = userLogin.getJson().getData().getCookie();
    }

    /**
     * This function submits a link to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param link      The link to the submission
     * @param subreddit The subreddit to submit to
     * @throws IOException    If connection fails
     * @throws ParseException If JSON Parsing fails
     */
    public void submitLink(String title, String link, String subreddit) throws IOException, URISyntaxException {
        redditServices.submit(linkPost().withUrl(link).withTitle(title).withSubreddit(subreddit).withModhash(modhash));
    }

    /**
     * This function submits a self post to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param text      The text of the submission
     * @param subreddit The subreddit to submit to
     * @throws IOException    If connection fails
     * @throws ParseException If JSON Parsing fails
     */
    public void submitSelfPost(String title, String text, String subreddit) throws IOException, URISyntaxException {
        redditServices.submit(selfPost().withBody(text).withTitle(title).withSubreddit(subreddit).withModhash(modhash));
    }

    /**
     * Get info about the currently authenticated user.
     * Corresponds to the API "/me.json" method
     *
     * @return <code>UserInfo</code>object containing the user's info, or null if the retrieval fails
     */
    public com.github.jreddit.model.json.response.UserInfo getUserInformation() throws IOException, URISyntaxException {
        if (cookie == null || modhash == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }

        return redditServices.getUserInfo();
    }

    /**
     * Returns misc info about the user
     *
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @return UserInfo object consisting of information about the user identified by "username".
     */
    public UserAbout about(String username) throws IOException, URISyntaxException {
        return redditServices.getUserAbout(username);
    }

    // TODO: Move comment-related and submission-related methods from the User class

    /**
     * Returns a list of submissions made by this user.
     *
     * @return <code>List</code> of submissions made by this user.
     */
    public RedditListing<CommentListingItem> comments() throws IOException, URISyntaxException {
        return comments(username);
    }

    public RedditListing<CommentListingItem> comments(String username) throws IOException, URISyntaxException {
        return comments(username, NEW);
    }

    /**
     * Returns a list of comments made by this user.
     *
     *
     * @param username The username of the user whose comments you want
     *                 to retrieve.
     * @return <code>List</code> of top 500 comments made by this user.
     */
    public RedditListing<CommentListingItem> comments(String username, CommentSort commentSort) throws IOException, URISyntaxException {
        return redditServices.getComments(username, commentSort);
    }

    /**
     * Returns a list of submissions made by this user.
     *
     *
     * @param username The username of the user whose submissions you want
     *                 to retrieve.
     * @return <code>List</code> of submissions made by this user.
     */
    public RedditListing<SubmissionListingItem> submissions(String username) throws IOException, URISyntaxException {
        return getSubmissions(username, "submissions", Sort.HOT);
    }

    /**
     * Returns a list of submissions that this user liked.
     *
     * @return List of liked links with default sorting.
     */
    public RedditListing<SubmissionListingItem> getLikedSubmissions() throws IOException, URISyntaxException {
        return getLikedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of submissions that this user liked with a Reddit sort
     *
     *
     * @param sort Which sort you'd like to apply
     * @return List of liked submissions with a Reddit sort
     */
    public RedditListing<SubmissionListingItem> getLikedSubmissions(Sort sort) throws IOException, URISyntaxException {
        return getUserSubmissions("liked", sort);
    }

    /**
     * Returns a list of submissions that this user chose to hide. with the default sorting
     *
     * @return List of disliked links.
     */
    public RedditListing<SubmissionListingItem> getHiddenSubmissions() throws IOException, URISyntaxException {
        return getHiddenSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that this user chose to hide with a specified sorting
     *
     *
     * @param sort Which sort you'd like to apply
     * @return List of hidden Submissions with a Reddit sort
     */
    public RedditListing<SubmissionListingItem> getHiddenSubmissions(Sort sort) throws IOException, URISyntaxException {
        return getUserSubmissions("hidden", sort);
    }

    /**
     * Returns a list of Submissions that the user saved with the default sort
     *
     * @return List of saved links
     */
    public RedditListing<SubmissionListingItem> getSavedSubmissions() throws IOException, URISyntaxException {
        return getSavedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that the user saved with a specified sorting
     *
     *
     * @param sort Which sort you'd like to apply
     * @return List of saved links with a Reddit sort
     */
    public RedditListing<SubmissionListingItem> getSavedSubmissions(Sort sort) throws IOException, URISyntaxException {
        return getUserSubmissions("saved", sort);
    }

    /**
     * Returns a list of Submissions that the user submitted with the default Reddit sort
     *
     * @return List of submitted Submissions
     */
    public RedditListing<SubmissionListingItem> getSubmissions() throws IOException, URISyntaxException {
        return getSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that the user submitted with a specified Reddit sort
     *
     * @return List of submitted Submissions with a specified Reddit sort
     */
    public RedditListing<SubmissionListingItem> getSubmissions(Sort sort) throws IOException, URISyntaxException {
        return getUserSubmissions("submitted", sort);
    }

    /**
     * Returns a list of submissions that this user disliked with the default Reddit sort
     *
     * @return List of disliked links.
     */
    public RedditListing<SubmissionListingItem> getDislikedSubmissions() throws IOException, URISyntaxException {
        return getDislikedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that this user disliked with a specified Reddit sort
     *
     *
     * @param sort Which sort you'd like to apply
     * @return List of disliked sorts with a specified sort
     */
    public RedditListing<SubmissionListingItem> getDislikedSubmissions(Sort sort) throws IOException, URISyntaxException {
        return getUserSubmissions("disliked", sort);
    }

    public RedditListing<SubmissionListingItem> getUserOverview() throws IOException, URISyntaxException {
        return getUserSubmissions("overview", Sort.HOT);
    }

    /**
     * private function used to get submissions that a user interacts with on Reddit
     *
     *
     * @param where place of Submission - see http://www.reddit.com/dev/api#GET_user_{username}_{where}
     * @return Submissions from the specified location, null if the location is invalid
     */
    private RedditListing<SubmissionListingItem> getUserSubmissions(String where, Sort sort) throws IOException, URISyntaxException {
        return getSubmissions(username, where, sort);
    }

    private RedditListing<SubmissionListingItem> getSubmissions(String username, String where, Sort sort) throws IOException, URISyntaxException {
        //valid arguments for where the Submission can come from; should this be an enum?
        //TODO: not all of these JSONs return something that can be processed purely into a Submission class, until those are taken care of, I commented them out
        final Set<String> validLocations = new HashSet<String>(Arrays.asList(/*"overview",*/ "submitted", /*"comments",*/ "liked", "disliked", "hidden", "saved"));

        if (!validLocations.contains(where)) {
            System.err.println(where + " is an invalid location");
            return null;
        }

        return redditServices.getSubmissions(username, where, sort);
    }

    /**
     * Returns a list of Subreddits to which the user is subscribed.
     *
     * @return List of Subreddits
     */
    public RedditListing<SubredditListingItem> getSubscribed() throws IOException, URISyntaxException {
        return redditServices.getSubscribed();
    }
}
