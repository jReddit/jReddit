# jReddit API methods

The purpose of this document is to provide listing and short documentation of API methods implemented in jReddit

## account
#### POST /api/login
Usage:

    User user = new User("some_username", "some_password");
    user.connect();
#### GET /api/me.json
Usage:

    //Most of this data is accessible through getters in a connected user instance
    //Example(where user is an initialized User instance):
    Boolean has_mail = user.hasMail();
    int lk = user.linkKarma();
    int ck = user.commentKarma();
#### POST /api/submit
Usage:

    //assuming user is a connected User instance:
    user.submitLink("Github","http://www.github.com","technology");

#### To be implemented:

* POST /api/clear_sessions
* POST /api/delete_user
* POST /api/register
* POST /api/update

## apps

#### To be implemented:

* POST /api/adddeveloper
* POST /api/deleteapp
* POST /api/removedeveloper
* POST /api/revokeapp
* POST /api/setappicon
* POST /api/updateapp

## captcha
#### POST /api/new_captcha
#### GET /captcha/iden
Usage:

    //assuming user is a connected User instance:
    Captcha c = new Captcha();
    String iden = c.new_captcha(user);

#### To be implemented:

* GET /api/needs_captcha.json

## flair

#### To be implemented:

* POST /api/clearflairtemplates
* POST /api/deleteflair
* POST /api/deleteflairtemplate
* POST /api/flair
* POST /api/flairconfig
* POST /api/flaircsv
* GET /api/flairlist
* POST /api/flairtemplate
* POST /api/selectflair
* POST /api/setflairenabled

## links & comments
#### POST /api/comment
Example usage:

    //Assuming subm is a valid, initialized Submission instance:
    subm.comment("Some comment");

#### POST /api/vote
Example usage:

    //Assuming subm is a valid, initialized Submission instance:
    subm.upVote();
    subm.downVote();

#### To be implemented:

* POST /api/del
* POST /api/editusertext
* POST /api/hide
* GET /api/info
* POST /api/marknsfw
* POST /api/morechildren
* POST /api/report
* POST /api/save
* POST /api/unhide
* POST /api/unmarknsfw
* POST /api/unsave

## listings

#### To be implemented:

* GET /comments/article
* GET /controversial
* GET /hot
* GET /new
* GET /random
* GET /top

## private messages
#### POST /api/compose
Example usage:

    //assuming user is a connected User instance:
    new Messages().compose(user, "some_user", "Some subject", "Some message", 
                           "some captcha iden(see above)", 
                           "some captcha solution");

#### GET /message/inbox
Example usage:

    //assuming user is a connected User instance:
    List<Message> inbox = new Messages().inbox(user, 10);

#### GET /message/sent
Example usage:

    //assuming user is a connected User instance:
    List<Message> sent = new Messages().sent(user, 10);

#### GET /message/unread
Example usage:

    //assuming user is a connected User instance:
    List<Message> unread = new Messages().unread(user);

#### To be implemented:

* POST /api/block
* POST /api/read_message
* POST /api/unread_message

###moderation

#### To be implemented:

* POST /api/approve
* POST /api/distinguish
* POST /api/ignore_reports
* POST /api/leavecontributor
* POST /api/leavemoderator
* POST /api/remove
* POST /api/unignore_reports
* GET /moderationlog
* GET /stylesheet

###search

#### To be implemented:

* GET /search

## subreddits
#### GET /subreddits/popular
Example usage:

    //assuming user is a connected User instance:
    List<Subreddit> subreddits = Subreddits.list(user,"popular");


#### GET /subreddits/banned
Example usage:

    //assuming user is a connected User instance:
    List<Subreddit> subreddits = Subreddits.list(user,"banned");

#### GET /subreddits/mine/subscriber.json
Example usage:

    //assuming user is a connected User instance:
    user.getSubscribed()

#### To be implemented:

* POST /api/accept_moderator\_invite
* POST /api/delete_sr\_header
* POST /api/delete_sr\_img
* POST /api/site_admin
* POST /api/subreddit_stylesheet
* GET /api/subreddits_by\_topic.json
* POST /api/subscribe
* POST /api/upload_sr_img
* GET /r/subreddit/about.json
* GET /subreddits/mine/contributor
* GET /subreddits/mine/moderator
* GET /subreddits/new
* GET /subreddits/search

## users
#### GET /user/username/disliked
Example usage:

    //assuming user is a connected User instance:
    List<Submission> sm = user.getDislikedSubmissions();

#### GET /user/username/hidden
Example usage:

    //assuming user is a connected User instance:
    List<Submission> sm = user.getHiddenSubmissions();

#### GET /user/username/liked
Example usage:

    //assuming user is a connected User instance:
    List<Submission> sm = user.getLikedSubmissions();


#### GET user/username/submitted
Example usage:

    //assuming user is a connected User instance:
    List<Submission> sm = user.getSubmissions();


#### GET /user/username/about.json
Example usage:

    UserInfo ui = User.about("some_username");

#### GET /user/username/comments
Example usage:

    List<Comment> comments = User.comments("some_username");

#### GET /user/username/saved
Example usage:

    //assuming user is an initialized User instance:
    List<Submission> saved = user.getSavedSubmissions();

#### To be implemented:

* POST /api/friend
* POST /api/setpermissions
* POST /api/unfriend
* GET /api/username_available.json
* GET /user/username/overview

## wiki

#### To be implemented:

* POST /api/wiki/alloweditor/add
* POST /api/wiki/alloweditor/del
* POST /api/wiki/alloweditor/act
* POST /api/wiki/edit
* POST /api/wiki/hide
* POST /api/wiki/revert
