# jReddit API methods

The purpose of this document is to provide listing and short documentation of API methods implemented in jReddit

## account
#### POST /api/login
Usage:

```java
User user = new User("some_username", "some_password");
user.connect();
```

#### GET /api/me.json
Usage:

```java
// Assuming user is a connected User instance.
// In case that the User is not connected, the method will return null.
user.getUserInformation();
```
#### POST /api/submit
Usage:

```java
// Assuming user is a connected User instance:
user.submitLink("Github","http://www.github.com","technology");
```

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
#### GET /captcha/iden
#### POST /api/new_captcha
Usage:

```java
// assuming a user is a connected to a User instance
Captcha captcha = new Captcha();
String iden = captcha.newCaptcha(user);
```
#### GET /api/needs_captcha.json
Usage:

```java
// assuming a user is connected to a User instance
Captcha captcha = new Captcha();
boolean needsCaptcha = captcha.needsCaptcha(user);
```

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

```java
// Assuming subm is a valid, initialized Submission instance:
subm.comment("Some comment");
```
#### POST /api/vote
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance:
subm.upVote();
subm.downVote();
```

#### POST /api/marknsfw
#### POST /api/unmarknsfw
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance:
subm.markNSFW();
subm.unmarkNSFW();
```

### POST /api/save
### POST /api/unsave
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance:
subm.save();
subm.save("Category");
subm.unsave();
```

### POST /api/hide
### POST /api/unhide

    // Assuming subm is a valid, initialize Submission instance:
    subm.hide();
    subm.unhide();

#### To be implemented:

* POST /api/del
* POST /api/editusertext
* GET /api/info
* POST /api/morechildren
* POST /api/report


## listings

#### GET /comments/article

```java
// Assuming 'comments' is a 'Comments' instance
List<Comment> commentsFromArticle = comments.commentsFromArticle("subreddit", "articleLink", "commentId", 0, 5, 10, CommentSort.TOP);
```

#### To be implemented:

* GET /controversial
* GET /hot
* GET /new
* GET /random
* GET /top

## private messages
#### POST /api/compose
Example usage:

```java
// Assuming user is a connected User instance:
new Messages().compose(user, "recipient_user", "Subject", "Message body", "captcha_iden", "captcha_solution");
```

#### GET /message/inbox
Example usage:

```java
// Assuming user is a connected User instance:
List<Message> inbox = new Messages().getMessages(user, 10, MessageType.INBOX);
```

#### GET /message/sent
Example usage:

```java
// Assuming user is a connected User instance:
List<Message> sent = new Messages().getMessages(user, 10, MessageType.SENT);
```

#### GET /message/unread
Example usage:

```java
// Assuming user is a connected User instance:
List<Message> unread = new Messages().getMessages(user, Messages.ALL_MESSAGES, MessageType.UNREAD);
```
	
#### POST /api/read_message
Example usage:
    
```java
// Assuming user is a connected User instance and we have a message instance of the Message class
new Messages().readMessage(message.getFullName(), user);
```

#### POST /api/unread_message
Example usage:

```java
// Assuming user is a connected User instance and we have a message instance of the Message class
new Messages().unreadMessage(message.getFullName(), user);
```    
#### To be implemented:

* POST /api/block

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
#### GET /subreddits/new
Example usage:

```java
List<Subreddit> subreddits = subreddits.getSubreddits(SubredditType.NEW);
```

#### GET /subreddits/popular
Example usage:

```java
List<Subreddit> subreddits = subreddits.getSubreddits(SubredditType.POPULAR);
```
#### GET /subreddits/banned
Example usage:

```java
List<Subreddit> subreddits = subreddits.getSubreddits(SubredditType.BANNED);
```

#### GET /subreddits/mine/subscriber.json
Example usage:

```java
// Assuming user is a connected User instance:
user.getSubscribed()
```

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
* GET /subreddits/search

## users
#### GET /user/username/disliked
Example usage:

```java
// Assuming user is a connected User instance:
List<Submission> sm = user.getDislikedSubmissions();
```

#### GET /user/username/hidden
Example usage:

```java
// Assuming user is a connected User instance:
List<Submission> sm = user.getHiddenSubmissions();
```

#### GET /user/username/liked
Example usage:

```java
// Assuming user is a connected User instance:
List<Submission> sm = user.getLikedSubmissions();
```

#### GET user/username/submitted
Example usage:

```java
// Assuming user is a connected User instance:
List<Submission> sm = user.getSubmissions();
```

#### GET /user/username/about.json
Example usage:

```java
UserInfo userInfo = User.about("some_username");
```

#### GET /user/username/comments
Example usage:

```java
// Assuming user is a connected User instance:
List<Comment> comments = user.comments();

// Retrieving comments without an existing User instance (comments is a Comments instance):
List<Comment> comments = Comments.comments("username");
    
// Additionally, by passing a CommentSort enum type (NEW, HOT, TOP, CONTROVERSIAL):
List<Comment> comments = Comments.comments("username", commentSort);
```

#### GET /user/username/saved
Example usage:

```java
// Assuming user is an initialized User instance:
List<Submission> saved = user.getSavedSubmissions();
```

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
