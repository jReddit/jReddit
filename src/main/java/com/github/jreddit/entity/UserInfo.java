package com.github.jreddit.entity;

import org.json.simple.JSONObject;

/**
 * Encapsulates user information (regarding karma, emails, identifiers, statuses, created time and current modhash)
 *
 * @author Raul Rene Lepsa
 */
public class UserInfo {

    // User identifier
    private String id;

    // The user's name
    private String name;

    // Modhash token
    private String modhash;

    // Karma points for all the comments
    private long commentKarma;

    // Karma points for all the submitted links
    private long linkKarma;

    // Whether the user is a moderator
    private boolean isMod;

    // Whether or not the user has moderator email
    private Boolean hasModMail;

    // Whether the account is associated with an email address
    private Boolean hasMail;

    // Indicates whether the user has verified the email address
    private boolean hasVerifiedEmail;

    // Whether the user is a gold member
    private boolean isGold;

    // Timestamp of the creation date
    private double created;

    // UTC timestamp of creation date
    private double createdUTC;

    // Indicates whether this user is friends with the currently connected one. Believe it or not, you can actually be 
    // friends with yourself. http://www.reddit.com/r/reddit.com/comments/duf7q/random_reddit_protip_you_can_add_yourself_as_a/
    private boolean isFriend;

    // Indicates whether the user is over 18
    private Boolean over18;

    public UserInfo() {

    }

    public UserInfo(JSONObject info) {
        setHasMail((Boolean) info.get("has_mail"));
        setHasModMail((Boolean) info.get("has_mod_mail"));
        setCommentKarma((Long) info.get("comment_karma"));
        setCreatedUTC((Double) info.get("created_utc"));
        setGold((Boolean) info.get("is_gold"));
        setLinkKarma((Long) info.get("link_karma"));
        setMod((Boolean) info.get("is_mod"));
        setFriend((Boolean) info.get("is_friend"));
        setModhash((String) info.get("modhash"));
        setHasVerifiedEmail((Boolean) info.get("has_verified_email"));
        setId((String) info.get("id"));
        setOver18((Boolean) info.get("over_18"));
        setCreated((Double) info.get("created"));
        setName((String) info.get("name"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public long getCommentKarma() {
        return commentKarma;
    }

    public void setCommentKarma(long commentKarma) {
        this.commentKarma = commentKarma;
    }

    public long getLinkKarma() {
        return linkKarma;
    }

    public void setLinkKarma(long linkKarma) {
        this.linkKarma = linkKarma;
    }

    public boolean isMod() {
        return isMod;
    }

    public void setMod(boolean isMod) {
        this.isMod = isMod;
    }

    public Boolean getHasModMail() {
        return hasModMail;
    }

    public void setHasModMail(Boolean hasModMail) {
        this.hasModMail = hasModMail;
    }

    public Boolean getHasMail() {
        return hasMail;
    }

    public void setHasMail(Boolean hasMail) {
        this.hasMail = hasMail;
    }

    public boolean isHasVerifiedEmail() {
        return hasVerifiedEmail;
    }

    public void setHasVerifiedEmail(boolean hasVerifiedEmail) {
        this.hasVerifiedEmail = hasVerifiedEmail;
    }

    public boolean isGold() {
        return isGold;
    }

    public void setGold(boolean isGold) {
        this.isGold = isGold;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public double getCreatedUTC() {
        return createdUTC;
    }

    public void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public Boolean getOver18() {
        return over18;
    }

    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }
    
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	String newLine = System.lineSeparator();
    	
    	result.append("id: ").append(id).append(newLine)
    		  .append("name: ").append(name).append(newLine)
    		  .append("modhash: ").append(modhash).append(newLine)
    		  .append("commentKarma: ").append(commentKarma).append(newLine)
    		  .append("linkKarma: ").append(linkKarma).append(newLine)
    		  .append("isModerator: ").append(isMod).append(newLine)
    		  .append("hasModMail: ").append(hasModMail).append(newLine)
    		  .append("hasMail: ").append(hasMail).append(newLine)
    		  .append("hasVerifiedEmail: ").append(hasVerifiedEmail).append(newLine)
    		  .append("isGold: ").append(isGold).append(newLine)
    		  .append("Created: ").append(created).append(newLine)
    		  .append("CreatedUTC: ").append(createdUTC).append(newLine)
    		  .append("isFriend: ").append(isFriend).append(newLine)
    		  .append("over18: ").append(over18);
    	
    	return result.toString();
    }
}
