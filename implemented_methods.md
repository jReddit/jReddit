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
#### POST /api/delete_user
Usage:

```java
//assuming PA is a ProfileActions instance
PA.delete(true, "I don't want to user reddit anymore", "userspassword");
```
#### POST /api/update
Usage:

```java
//assuming PA is a ProfileActions instance
PA.update("userspassword", "mynewemail@provider.com", "");
```
#### POST /api/register
Usage:

```java
//assuming PA is a ProfileActions instance, and restClient is a RestClient instance
Captcha cp = new Captcha(restClient);
String myIdentifier = cp.newCaptcha(new User(restClient, null, null));
//...ask the user to solve captcha here, store result in the String mySolution
PA.register("coolusername273", "", "securePassword22", "securePassword22", myIdentifier, mySolution);
```

#### To be implemented:

* POST /api/clear_sessions


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

#### POST /api/clearflairtemplates
Usage:

```java
// Assuming a flairAction is an initialized instance of the FlairActions class
// Clear all user flair templates in the subreddit 'myblueprints'
flairActions.clearFlairTemplates("USER_FLAIR", "myblueprints");
//Clear all the link flair templates in the subreddit 'myblueprints'
flairActions. clearFlairTemplates ("LINK_FLAIR", "my blueprints");
```

#### POST /api/deleteflair
Usage:

```java
// Assuming a flairAction is an initialized instance of the FlairActions class
//Delete the flair from the user 'Tridentac' in the subreddit 'myblueprints'
flairActions.deleteFlair("Tridentac", "myblueprints");
```

#### POST /api/flair
Usage:

```java
// Assuming a flairAction is an initialized instance of the FlairActions class
//Add the flair 'Blueprinter' to the post 't3_2r86db' in the subreddit 'myblueprints' with the css class 'red'
flairActions.flair("red", "t3_2r86db", null, "Blueprinter", "myblueprints");
//Add the flair 'Blueprinter' to the user 'Tridentac' in the subreddit 'myblueprints' with the css class 'red'
flairActions.flair("red", null, "Tridentac", "Blueprinter", "myblueprints");
```

#### POST /api/flairconfig
Usage:

```java
//Set the flair configs as so:
  //allow user and link flairs
  //user flairs on the left
  //link flairs on the right
  //users can assign link flairs
  //users cannot assign user flairs
flairActions.flairConfig(true, "left", false, "right", true, "myblueprints");
```

#### POST /api/flairtemplate
Usage:

```java
//Assuming flairActions is an initialized instance of the Flair Actions class
flairActions.flairTemplate("cssClass", "templateID", "USER_FLAIR/LINK_FLAIR", "Text", false(textEditable), "subreddit");
//For example. Add the user flair template "BLUE-TAG" with the text "Blue Team" and the css class "blueteam" in the subreddit "/r/somegenericgame" with users not be able to edit the string.
flairActions.flairTemplate("blueteam", "BLUE-TAG", "USER_FLAIR", "Blue Team", false, "somegenericgame");
```

#### POST /api/selectflair
Usage:

```java
//Assuming flairActions is an initialized instance of the Flair Actions class
flairActions.selectFlair("flair-id", "link", "user", "text", "subreddit");
//For example. Give the user /u/somegenericgamer the flair "Blue team" from the flair-template "BLUE-TAG" in the subreddit /r/somegenericgame
flairActions.selectFlair("BLUE-TAG", null, "somegenericgamer", "Blue team", "somegenericgame");
```

#### POST /api/setflairenabled
Usage:

```java
//Assuming flairActions is an initialized instance of the Flair Actions class
flairActions.setFlairEnabled(true/false(enabled), "subreddit");
//For example. Enable flair for the currently logged in user in the subreddit /r/somegenericgame
flairActions.setFlairEnabled(true, "somegenericgame");
```

#### To be implemented:

* POST /api/flaircsv

#### To be repaired:

* GET /api/flairlist
* POST /api/flairselector
* POST /api/deleteflairtemplate

## links & comments
#### POST /api/comment
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance, and SA is a SubmitActions instance
SA.comment(subm.getFullName(), "Some comment");
```
#### POST /api/vote
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance, and MA is a MarkActions instance
MA.vote(subm.getFullName(), 1); //upvote
MA.vote(subm.getFullName(), -1); //downvote
```

#### POST /api/marknsfw
#### POST /api/unmarknsfw
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance, and MA is a MarkActions instance
MA.markNSFW(subm.getFullName());
MA.unmarkNSFW(subm.getFullName());
```

### POST /api/save
### POST /api/unsave
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance, and MA is a MarkActions instance
MA.save(subm.getFullName());
MA.save(subm.getFullName(), "Category");
MA.unsave(subm.getFullName());
```

### POST /api/hide
### POST /api/unhide

```java
// Assuming subm is a valid, initialized Submission instance, and MA is a MarkActions instance
MA.hide(subm.getFullName());
MA.unhide(subm.getFullName());
```

#### POST /api/del
Example usage:

```java
// Assuming subm is a valid, initialized Submission instance, and SA is a SubmitActions instance
SA.delete(subm.getFullName());
```

#### POST /api/editusertext

```java
// Assuming subm is a valid, initialized Submission instance, and SA is a SubmitActions instance
SA.editUserText(subm.getFullName(),"new text");
// Assuming comment is a valid, initialized Comment instance, and SA is a SubmitActions instance
SA.editUserText(comment.getFullName(),"new text");
```

#### To be implemented:

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

## live threads

#### POST /api/live/create

```java
// Assuming SA is a SubmitActions instance
SA.createLive("Thread title.", "Thread description.");
```

#### POST /api/live/thread/update

```java
// Assuming SA is a SubmitActions instance
SA.updateLive("Thread id.", "Update message.");
// TODO: LiveThread object so the user doesn't need to know the thread id.
```

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
