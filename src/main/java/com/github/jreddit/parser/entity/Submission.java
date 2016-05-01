package com.github.jreddit.parser.entity;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToLong;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.parser.entity.imaginary.MixedListingElement;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a vote on a link submission on Reddit.
 *
 * @author Omer Elnour
 * @author Andrei Sfat
 * @author Raul Rene Lepsa
 * @author Jonny Krysh
 * @author Danny Tsegai
 * @author Simon Kassing
 */
public class Submission extends Thing implements MixedListingElement {
    
    /* All String values */
    private String url;
    private String permalink;
    private String author;
    private String title;
    private String subreddit;
    private String subredditId;
    private String thumbnail;
    private String selftext;
    private String selftextHTML;
    private String domain;
    private String bannedBy;
    private String approvedBy;
    private String authorFlairCSSClass;
    private String linkFlairCSSClass;
    private String authorFlairText;
    private String linkFlairText;
    private String distinguished;
    private String from;
    private String fromId;
    private String removalReason;
    private String fromKind;
    
    /* All Long values */
    private Long gilded;
    private Long commentCount;
    private Long reportCount;
    private Long score;
    private Long upVotes;
    private Long downVotes;

    /* All Double values */
    private Double created;
    private Double createdUTC;
    private Double upvoteRatio;
    
    /* All Boolean values */
    private Boolean visited;
    private Boolean self;
    private Boolean saved;
    private Boolean edited;
    private Boolean stickied;
    private Boolean nsfw;
    private Boolean hidden;
    private Boolean clicked;
    private Boolean likes;

    /* All images values */
    private Image source;
    private List<Image> resolutions = new ArrayList<>();

    /**
     * Create a Submission from a JSONObject
     *
     * @param obj The JSONObject to load Submission data from
     */
    public Submission(JSONObject obj) {
        super(safeJsonToString(obj.get("name")));

        setURL(safeJsonToString(obj.get("url")));
        setPermalink(safeJsonToString(obj.get("permalink")));
        setAuthor(safeJsonToString(obj.get("author")));
        setTitle(safeJsonToString(obj.get("title")));
        setSubreddit(safeJsonToString(obj.get("subreddit")));
        setSubredditId(safeJsonToString(obj.get("subreddit_id")));
        setThumbnail(safeJsonToString(obj.get("thumbnail")));
        setSelftext(safeJsonToString(obj.get("selftext")));
        setSelftextHTML(safeJsonToString(obj.get("selftext_html")));
        setDomain(safeJsonToString(obj.get("domain")));
        setBannedBy(safeJsonToString(obj.get("banned_by")));
        setApprovedBy(safeJsonToString(obj.get("approved_by")));
        setAuthorFlairCSSClass(safeJsonToString(obj.get("author_flair_css_class")));
        setLinkFlairCSSClass(safeJsonToString(obj.get("link_flair_css_class")));
        setDistinguished(safeJsonToString(obj.get("distinguished")));
        setAuthorFlairText(safeJsonToString(obj.get("author_flair_text")));
        setLinkFlairText(safeJsonToString(obj.get("link_flair_text")));
        setFrom(safeJsonToString(obj.get("from")));
        setFromId(safeJsonToString(obj.get("from_id")));
        setRemovalReason(safeJsonToString(obj.get("removal_reason")));
        setFromKind(safeJsonToString(obj.get("from_kind")));
        
        setGilded(safeJsonToLong(obj.get("gilded")));
        setCommentCount(safeJsonToLong(obj.get("num_comments")));
        setReportCount(safeJsonToLong(obj.get("num_reports")));
        setScore(safeJsonToLong(obj.get("score")));
        setUpVotes(safeJsonToLong(obj.get("ups")));
        setDownVotes(safeJsonToLong(obj.get("downs")));
        
        setCreated(safeJsonToDouble(obj.get("created")));
        setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
        setUpvoteRatio(safeJsonToDouble(obj.get("upvote_ratio")));
        
        setVisited(safeJsonToBoolean(obj.get("visited")));
        setSelf(safeJsonToBoolean(obj.get("is_self")));
        setSaved(safeJsonToBoolean(obj.get("saved")));
        setEdited(safeJsonToBoolean(obj.get("edited")));
        setStickied(safeJsonToBoolean(obj.get("stickied")));
        setNSFW(safeJsonToBoolean(obj.get("over_18")));
        setHidden(safeJsonToBoolean(obj.get("hidden")));
        setClicked(safeJsonToBoolean(obj.get("clicked")));
        setLikes(safeJsonToBoolean(obj.get("likes")));

        fillImages(obj);
    }

    private void fillImages(JSONObject data) {
        JSONObject preview = (JSONObject) data.get("preview");
        if (preview == null) return;

        Object imagesNode = preview.get("images");
        if (imagesNode == null) return;

        JSONObject images = (JSONObject) ((JSONArray)imagesNode).get(0);

        this.source = new Image((JSONObject) images.get("source"));

        JSONArray resolutions = (JSONArray) images.get("resolutions");

        if (resolutions == null) return;

        for (Object img : resolutions) {
            this.resolutions.add(new Image((JSONObject) img));
        }
    }

    public Image getSource() {
        return source;
    }

    public List<Image> getResolutions() {
        return resolutions;
    }

    public String getFromKind() {
        return fromKind;
    }

    private void setFromKind(String fromKind) {
        this.fromKind = fromKind;
    }

    public String getRemovalReason() {
        return removalReason;
    }

    private void setRemovalReason(String removalReason) {
        this.removalReason = removalReason;
    }

    public String getFrom() {
        return from;
    }

    private void setFrom(String from) {
        this.from = from;
    }

    public String getFromId() {
        return fromId;
    }

    private void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * @return Up-vote ratio (total number of up-votes divided by total number of votes)
     */
    public Double getUpvoteRatio() {
        return upvoteRatio;
    }

    private void setUpvoteRatio(Double upvoteRatio) {
        this.upvoteRatio = upvoteRatio;
    }


    /**
     * @return The CSS class of the author
     */
    public String getAuthorFlairCSSClass() {
        return authorFlairCSSClass;
    }

    private void setAuthorFlairCSSClass(String authorFlairCSSClass) {
        this.authorFlairCSSClass = authorFlairCSSClass;
    }
    
    /**
     * @return The text of the flair of the author
     */
    public String getAuthorFlairText() {
        return authorFlairText;
    }

    private void setAuthorFlairText(String authorFlairText) {
        this.authorFlairText = authorFlairText;
    }


    /**
     * @return Flair text of the submission ('link')
     */
    public String getLinkFlairText() {
        return linkFlairText;
    }

    private void setLinkFlairText(String linkFlairText) {
        this.linkFlairText = linkFlairText;
    }


    /**
     * @return The CSS class of the submission ('link') flair
     */
    public String getLinkFlairCSSClass() {
        return linkFlairCSSClass;
    }

    private void setLinkFlairCSSClass(String linkFlairCSSClass) {
        this.linkFlairCSSClass = linkFlairCSSClass;
    }

    /**
     * @return Whether the post is distinguished (e.g. by a "moderator")
     */
    public String getDistinguished() {
        return distinguished;
    }

    private void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    /**
     * @return Whether the user that authenticated the retrieval of this submission upvoted it
     */
    public Boolean getLikes() {
        return likes;
    }

    private void setLikes(Boolean likes) {
        this.likes = likes;
    }
    
    /**
     * @return The username of the one that approved this submission to the subreddit it belongs to
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    private void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * @return Whether the user that authenticated the retrieval of this submission has hidden it
     */
    public Boolean isHidden() {
        return hidden;
    }

    private void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return Whether the user that authenticated the retrieval of this submission has already clicked on it before
     */
    public Boolean isClicked() {
        return clicked;
    }

    private void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public void setUpVotes(Long upVotes) {
        this.upVotes = upVotes;
    }

    /**
     * @return The score (number of upvotes minus number of downvotes)
     */
    public Long getScore() {
        return score;
    }
    
    private void setScore(Long score) {
        this.score = score;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedUTC(Double createdUTC) {
        this.createdUTC = createdUTC;
    }

    public void setDownVotes(Long downVotes) {
        this.downVotes = downVotes;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Long getUpVotes() {
        return upVotes;
    }

    public Long getDownVotes() {
        return downVotes;
    }



    public Double getCreatedUTC() {
        return createdUTC;
    }

    public String getAuthor() {
        return author;
    }

    /**
     * @return Title of the submission
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Subreddit name (e.g. "programming')
     */
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * @return Subreddit ID36 identifier
     */
    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    /**
     * @return Thumbnail image URL
     */
    public String getThumbnail() {
        return thumbnail;
    }

    private void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return The self text of the submission (written by the author)
     */
    public String getSelftext() {
        return selftext;
    }

    private void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    /**
     * @return The self text of the submission in HTML (written by the author)
     */
    public String getSelftextHTML() {
        return selftextHTML;
    }

    private void setSelftextHTML(String selftextHTML) {
        this.selftextHTML = selftextHTML;
    }

    /**
     * @return The domain of the URL this submission links to
     */
    public String getDomain() {
        return domain;
    }

    private void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return The username of the moderator that banned this submission
     */
    public String getBannedBy() {
        return bannedBy;
    }

    private void setBannedBy(String bannedBy) {
        this.bannedBy = bannedBy;
    }

    /**
     * @return Count of how many times this submission received gold ('was gilded')
     */
    public Long getGilded() {
        return gilded;
    }

    private void setGilded(Long gilded) {
        this.gilded = gilded;
    }

    /**
     * @return Count of how many times this submission was reported
     */
    public Long getReportCount() {
        return reportCount;
    }

    private void setReportCount(Long reportCount) {
        this.reportCount = reportCount;
    }

    /**
     * @return The created time-stamp (ms since Unix epoch)
     */
    public Double getCreated() {
        return created;
    }

    private void setCreated(Double created) {
        this.created = created;
    }

    /**
     * @return Whether the user that authenticated the retrieval of this submission has already visited it before
     */
    public Boolean isVisited() {
        return visited;
    }

    private void setVisited(Boolean visited) {
        this.visited = visited;
    }

    /**
     * @return Whether it is a self post
     */
    public Boolean isSelf() {
        return self;
    }

    private void setSelf(Boolean self) {
        this.self = self;
    }

    /**
     * @return Whether the user that authenticated the retrieval of this submission has saved it
     */
    public Boolean isSaved() {
        return saved;
    }

    private void setSaved(Boolean saved) {
        this.saved = saved;
    }

    /**
     * @return Whether the submission has been edited
     */
    public Boolean isEdited() {
        return edited;
    }

    private void setEdited(Boolean edited) {
        this.edited = edited;
    }

    /**
     * @return Whether the submission is sticked to the top of the subreddit
     */
    public Boolean isStickied() {
        return stickied;
    }

    private void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    /**
     * @return Whether the post is Not Suited For Work (contains adult content)
     */
    public Boolean isNSFW() {
        return nsfw;
    }

    private void setNSFW(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    @Override
    public String toString() {
        return "Submission(" + this.getFullName() + ")<" + title + ">";
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Submission && this.getFullName().equals(((Submission) other).getFullName());
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() * this.getFullName().hashCode();
    }

    @Override
    public int compareTo(Thing o) {
        return this.getFullName().compareTo(o.getFullName());
    }
    
}