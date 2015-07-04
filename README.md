jReddit
=====

[![Build Status](https://travis-ci.org/jReddit/jReddit.png?branch=master)](https://travis-ci.org/jReddit/jReddit)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.jreddit/jreddit/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.jreddit/jreddit)
[![Coverage Status](https://coveralls.io/repos/jReddit/jReddit/badge.svg)](https://coveralls.io/r/jReddit/jReddit)

### What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. Project started by Omer Elnour. Taken over for further development and maintenance by [Karan Goel](https://github.com/karan), [Andrei Sfat](https://github.com/sfat), and [Simon Kassing](https://github.com/snkas).

### How to use jReddit?
*We are currently in the transitional phase between cookie-based authentication and OAuth2. The previous version of jReddit (1.0.3), which uses the former, will no longer be supported by reddit beginning August. The latter is supported by the latest version of jReddit (1.0.4).*

#### Latest version: 1.0.4
The latest version (1.0.4) can only be included by forking or directly copying source. When the build is stable, we will distribute it via Maven (as done with previous versions).

#### Old version (deprecated): 1.0.3

At the moment, jReddit 1.0.3 can be included in your project using:

##### Maven
```
<dependency>
        <groupId>com.github.jreddit</groupId>
        <artifactId>jreddit</artifactId>
        <version>1.0.3</version>
</dependency>
```

##### Gradle
```
dependencies {
    compile group: 'com.github.jreddit', name: 'jreddit', version: '1.0.3'
}
```
### What can it do?
jReddit supports the creation of clients and bots using the Java language. It communicates using the reddit api, and all its actions are limited by the functionality offered there. It can for example authenticate apps using OAuth2, retrieve subreddits, submissions and comments, and perform various actions such as flairing, hiding and saving.

### Examples
Examples can be found in [Examples package](https://github.com/jReddit/jReddit/tree/master/src/main/java/examples). A full example Gradle (Android) project can be found in the [Gradle example folder](https://github.com/jReddit/jReddit/tree/master/examples/Jreddit-sample-project). To give you an impression, here is a code snippet for a simple example:

Connect a user
```java
// Information about the app
String userAgent = "jReddit: Reddit API Wrapper for Java";
String clientID = "JKJF3592jUIisfjNbZQ";
String redirectURI = "https://www.example.com/auth";

// Reddit application
RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);    
RedditClient client = new RedditHttpClient(userAgent, HttpClientBuilder.create().build());

// Create a application-only token (will be valid for 1 hour)
RedditToken token = agent.tokenAppOnly(false);

// Create parser for request
SubmissionsListingParser parser = new SubmissionsListingParser();

// Create the request
SubmissionsOfSubredditRequest request = (SubmissionsOfSubredditRequest) new SubmissionsOfSubredditRequest("programming", SubmissionSort.HOT).setLimit(100);

// Perform and parse request, and store parsed result
List<Submission> submissions = parser.parse(client.get(token, request));

// Now print out the result (don't care about formatting)
System.out.println(submissions);
```

*Note: please see the [reddit OAuth2 information page](https://github.com/reddit/reddit/wiki/OAuth2) for more information on acquiring a client identifier, secret, and redirect URI.*

### What's next for jReddit?
The plan is to implement every feature documented [here](http://www.reddit.com/dev/api). To see which methods have been implemented, and which have not, see [this file](https://github.com/karan/jReddit/blob/master/implemented_methods.md).

### How to contribute?
It is recommend to start with forking and reading through the source code, in oder to understand the general structure and standards used. Then check the [implemented_methods.md](https://github.com/karan/jReddit/blob/master/implemented_methods.md) file to see which methods have not yet been implemented. Choose the ones you'd like to contribute to. When you implement a new request, make sure it is fully covered by testing (via JUnit), and that all other tests pass as well. Thorough documentation of your addition is appreciated.

Send in a pull request, including test, and it will be gladly merged to improve the library! :-)

### Dependencies
1. [JSON-simple](http://code.google.com/p/json-simple/)

2. [Apache HttpComponents](https://hc.apache.org/)

3. [Apache Commons IO](https://commons.apache.org/proper/commons-io/)
