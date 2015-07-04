# jReddit API methods

The purpose of this document is to provide listing and short documentation of API methods implemented in jReddit

#### account
*None have been implemented.*

#### captcha
*None have been implemented.*

#### flair

##### POST [/r/subreddit]/api/deleteflair
    DeleteFlairRequest request = new DeleteFlairRequest("username");
    client.post(token, request)
*NOTE: a parser has not yet been implemented.*

#### reddit gold
*None have been implemented.*

#### links & comments
*TODO: fill in.*

#### listings

#### private messages
*None have been implemented.*

#### misc
*None have been implemented.*

#### moderation
*None have been implemented.*

#### multis
*None have been implemented.*

#### search (fully implemented)


#### GET [/r/subreddit]/search
    SubmissionsSearchRequest request = new SubmissionsSearchRequest("query");
    SubmissionsListingParser parser = new SubmissionsListingParser();
    List<Submission> submissions = parser.parse(client.get(token, request));

#### subreddits

#### GET /subreddits/search
    SubredditsSearchRequest request = new SubredditsSearchRequest("query");
    SubredditsListingParser parser = new SubredditsListingParser();
    List<Subreddit> subreddits = parser.parse(client.get(token, request));

#### users
*None have been implemented.*

#### wiki
*None have been implemented.*

