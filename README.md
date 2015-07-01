jReddit
=====

[![Build Status](https://travis-ci.org/jReddit/jReddit.png?branch=master)](https://travis-ci.org/jReddit/jReddit)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.jreddit/jreddit/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.jreddit/jreddit)
[![Coverage Status](https://coveralls.io/repos/jReddit/jReddit/badge.svg)](https://coveralls.io/r/jReddit/jReddit)

### What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. Project started by Omer Elnour. Taken over for further development and maintainence by [Karan Goel](http://www.goel.im).
### How to use jReddit?
At the moment, jReddit can be included in your project using:

#### Maven
```
<dependency>
        <groupId>com.github.jreddit</groupId>
        <artifactId>jreddit</artifactId>
        <version>1.0.3</version>
</dependency>
```

#### Gradle
```
dependencies {
    compile group: 'com.github.jreddit', name: 'jreddit', version: '1.0.3'
}
```
### What can it do?
jReddit can login with a user, retrieve user information, submit new links, and vote/comment on submissions, send and receive messages and notifications among other things.

### Examples

Connect a user
```java
// Initialize REST Client
RestClient restClient = new HttpRestClient();
restClient.setUserAgent("bot/1.0 by name");

// Connect the user 
User user = new User(restClient, "username", "password");
try {
    user.connect();
} catch (Exception e) {
    e.printStackTrace();
}
```

Retrieve top 100 submissions of /r/programming

```java
// Handle to Submissions, which offers the basic API submission functionality
Submissions subms = new Submissions(restClient, user);

// Retrieve submissions of a submission
List<Submission> submissionsSubreddit = subms.ofSubreddit("programming", SubmissionSort.TOP, -1, 100, null, null, true);

```

Submit a link and self post

```java

// Handle to SubmitActions, which offers the basic API functionality to submit comments and posts
SubmitActions submitActions = new SubmitActions(restClient, user);

// Submit a link
submitActions.submitLink(
        "Oracle V Google judge is a programmer!",
        "http://www.i-programmer.info/news/193-android/4224-oracle-v-google-judge-is-a-programmer.html",
        "programming");
        
// Submit a self post
submitActions.submitSelfPost("What's the difference between a duck?",
        "One of its legs are both the same!", "funny");
```

Send a message to another user

```java
import com.github.jreddit.message.Messages;
import com.github.jreddit.user.User;

/**
 * @author Karan Goel
 */
public class ComposeTest {

    public static void main(String[] args) {
        User user = null;
        String recipientUsername = "other_user";
        try {
            user = new User("username", "password"); // Add your username and password
            user.connect();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        new Messages().compose(user, recipientUsername, "this is the title", "the message", "", "");
    }
}
```

# Possibility to configure RestClient
### Normal instantiation

There are 2 implementation at the moment for ```RestClient```

* ```HttpRestClient```
* ```PoliteHttpRestClient``` *recommended RestClient since Reddit API has a limitation to make only 1 request every 2 seconds.*

### Through RestClientFactory

Factory and utility class to easily instantiate ```RestClient```.
Available methods:

* ```newHttpRestClient()```
* ```newHttpRestClient(HttpClient)```
* ```newPoliteHttpRestClient()```
* ```newPoliteHttpRestClient(HttpClient)```
* ```newProxyHttpClient(hostname, port)```
* ```newProxyHttpClient(hostname, port, scheme)```

### Other examples
## Java 
You can find other examples [here](https://github.com/karan/jReddit/tree/master/src/main/java/examples)
## Android
You can find a small example of how to integrate jReddit in an Android application [here](https://github.com/jReddit/jReddit/tree/master/examples/Jreddit-sample-project)


### What's next for jReddit?
The plan is to implement every feature documented [here](http://www.reddit.com/dev/api). To see which methods have been implemented, and which have not, see [this file](https://github.com/karan/jReddit/blob/master/implemented_methods.md).

### How to contribute?
Personally, I would suggest reading through the source code to understand the general structure and standards used. Then check the [implemented_methods.md](https://github.com/karan/jReddit/blob/master/implemented_methods.md) file to see which methods have not yet been implemented. Choose the ones you'd like to contribute to. After you write the method (and maybe commit it?), write a test to see if it works fine and as expected. Then make sure other tests are working too and your code does not break anty other method.

Send in a pull request with the test and I'll be happy to merge! :-)

### Dependencies
1. [JSON-simple](http://code.google.com/p/json-simple/)

2. [Apache HttpComponents](https://hc.apache.org/)

3. [Apache Commons IO](https://commons.apache.org/proper/commons-io/)
