package com.github.jreddit.utils;


/**
 * Some constants that are used for specifying Reddit API endpoints (example: /api/new_captcha)
 *
 * @author Andrei Sfat
 */
public class ApiEndpointUtils {

    public static final String REDDIT_BASE_URL = "http://www.reddit.com";

    public static final String REDDIT_BASE_API_ENDPOINT = "/api";

    /* Captcha specific constants */
    
    public static final String CAPTCHA_NEW = REDDIT_BASE_API_ENDPOINT + "/new_captcha";

    public static final String CAPTCHA_NEEDS = REDDIT_BASE_API_ENDPOINT + "/needs_captcha.json";
    
    /* Message specific constants */
    
    public static final String MESSAGE_COMPOSE = REDDIT_BASE_API_ENDPOINT + "/compose";
    
    public static final String MESSAGE_READ = REDDIT_BASE_API_ENDPOINT + "/read_message";

    public static final String MESSAGE_GET = "/message/%s.json";
    
    /* API functions */
    
    public static final String COMMENT = REDDIT_BASE_API_ENDPOINT + "/comment";
    
    public static final String SUBMISSION_MARK_AS_NSFW = REDDIT_BASE_API_ENDPOINT + "/marknsfw";
    
    public static final String SUBMISSION_UNMARK_AS_NSFW = REDDIT_BASE_API_ENDPOINT + "/unmarknsfw";
    
    public static final String VOTE = REDDIT_BASE_API_ENDPOINT + "/vote";

    public static final String SAVE = REDDIT_BASE_API_ENDPOINT + "/save";

    public static final String UNSAVE = REDDIT_BASE_API_ENDPOINT + "/unsave";
    
    public static final String HIDE = REDDIT_BASE_API_ENDPOINT + "/hide";
    
    public static final String UNHIDE = REDDIT_BASE_API_ENDPOINT + "/unhide";
    
    public static final String DELETE = REDDIT_BASE_API_ENDPOINT + "/delete";
    
    public static final String REPORT = REDDIT_BASE_API_ENDPOINT + "/report";

    public static final String SUBMISSION_COMMENTS = "/comments/%s.json?%s";
    
    public static final String SUBMISSIONS_SEARCH = "/search.json?%s";
    
    public static final String SUBMISSIONS_GET = "/r/%s.json?%s";
    
    /* User specific constants */
    
    public static final String USER_LOGIN = REDDIT_BASE_API_ENDPOINT + "/login/%s";
    
    public static final String USER_SUBMIT = REDDIT_BASE_API_ENDPOINT + "/submit";

    public static final String USER_ABOUT = "/user/%s/about.json";

    public static final String USER_COMMENTS = "/user/%s/comments.json?%s";

    public static final String USER_GET_SUBSCRIBED = "/subreddits/mine/subscriber.json";

    public static final String USER_SUBMISSIONS_INTERACTION = "/user/%s/%s.json?%s";

    public static final String USER_SUBMISSIONS = "/user/%s/submitted.json";

    public static final String USER_INFO = REDDIT_BASE_API_ENDPOINT + "/me.json";
    
    public static final String USER_UPDATE = REDDIT_BASE_API_ENDPOINT + "/update";

    /* Subreddits specific constants */

    public static final String SUBREDDITS = "/subreddits.json";

    public static final String SUBREDDITS_GET = "/subreddits/%s.json?%s";
    
    public static final String SUBREDDITS_SEARCH = "/subreddits/search.json?%s";

}
