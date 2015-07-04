# jReddit API methods

The purpose of this document is to provide listing and short documentation of API methods implemented in jReddit

### account (0/6)
*None have been implemented.*

### captcha (0/3)
*None have been implemented.*

### flair (0.5/11)

##### POST [/r/subreddit]/api/deleteflair
    DeleteFlairRequest request = new DeleteFlairRequest("username");
    client.post(token, request)
*NOTE: a parser has not yet been implemented.*

### reddit gold (0/2)
*None have been implemented.*

### links & comments (?/20)
*TODO: fill in.*

### listings (?/8)
*TODO: fill in.*

### live threads (0/17)
*None have been implemented.*

### private messages (0/7)
*None have been implemented.*

### misc (0/1)
*None have been implemented.*

### moderation (0/11)
*None have been implemented.*

### multis (0/13)
*None have been implemented.*

### search (1/1)

##### GET [/r/subreddit]/search
    SubmissionsSearchRequest request = new SubmissionsSearchRequest("query");
    SubmissionsListingParser parser = new SubmissionsListingParser();
    List<Submission> submissions = parser.parse(client.get(token, request));

### subreddits (1/20)

##### GET /subreddits/search
    SubredditsSearchRequest request = new SubredditsSearchRequest("query");
    SubredditsListingParser parser = new SubredditsListingParser();
    List<Subreddit> subreddits = parser.parse(client.get(token, request));

### users (1/12)
*TODO: fill in.*

### wiki (0/11)
*None have been implemented.*

