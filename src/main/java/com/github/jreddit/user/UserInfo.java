package com.github.jreddit.user;

/**
 * Encapsulates user information (which consists of comment karma, mod mail identifier,
 * created timestamp, gold member identifier, mod identifier, link karma and mail identifier).
 *
 * @author Benjamin Jakobus
 */
public class UserInfo {

    // Comment karma
    private int commentKarma;

    // Whether or not the user has moderator email
    private boolean hasModMail = false;

    // Whether the user is a gold member
    private boolean isGold = false;

    // Whether the user is a moderator
    private boolean isMod = false;

    // Link karma
    private int linkKarma;

    // UTC timestamp of creation date
    private float createdUTC;

    // Whether the account is associated with an email address
    private boolean hasMail = false;

    /**
     * Constructor.
     *
     * @param commentKarma Comment karma.
     * @param linkKarma    Link karma.
     * @param createdUTC   Created timestamp (UTC).
     * @param isGold       True if the user has gold membership; false if not.
     * @param isMod        True if the user is a moderator; false if not.
     * @param hasMail      True if the account is associated with an email address;
     *                     false if not.
     * @param hasModMail   True if the account has a moderator address; false if not.
     * @author Benjamin Jakobus
     */
    public UserInfo(int commentKarma, int linkKarma, float createdUTC, boolean isGold,
                    boolean isMod, boolean hasMail, boolean hasModMail) {
        this.commentKarma = commentKarma;
        this.linkKarma = linkKarma;
        this.createdUTC = createdUTC;
        this.isGold = isGold;
        this.isMod = isMod;
        this.hasMail = hasMail;
        this.hasModMail = hasModMail;
    }

    /**
     * Sets the comment karma.
     *
     * @param commentKarma Comment karma.
     * @author Benjamin Jakobus
     */
    public void setCommentKarma(int commentKarma) {
        this.commentKarma = commentKarma;
    }

    /**
     * Sets the UTC timestamp of when the account was created.
     *
     * @param createdUTC UTC timestamp of when the account was created.
     * @author Benjamin Jakobus
     */
    public void setCreatedUTC(float createdUTC) {
        this.createdUTC = createdUTC;
    }

    /**
     * Sets the flag identifying whether or not the account is associated
     * with an email address.
     *
     * @param hasMail True if the account is associated with an email address;
     *                false if not.
     * @author Benjamin Jakobus
     */
    public void setHasMail(boolean hasMail) {
        this.hasMail = hasMail;
    }

    /**
     * Sets the flag identifying whether or not the account is associated
     * with a moderator email address.
     *
     * @param hasModMail True if the account is associated with a moderator email address;
     *                   false if not.
     * @author Benjamin Jakobus
     */
    public void setHasModMail(boolean hasModMail) {
        this.hasModMail = hasModMail;
    }

    /**
     * Sets the flag identifying whether or not the account has Gold membership.
     *
     * @param isGold True if the account has Gold membership;
     *               false if not.
     * @author Benjamin Jakobus
     */
    public void setIsGold(boolean isGold) {
        this.isGold = isGold;
    }

    /**
     * Sets whether or not this user is a moderator.
     *
     * @param isMod True if the user is a moderator; false if not.
     * @author Benjamin Jakobus
     */
    public void setIsMod(boolean isMod) {
        this.isMod = isMod;
    }

    /**
     * Sets the link karma.
     *
     * @param linkKarma The link karma.
     * @author Benjamin Jakobus
     */
    public void setLinkKarma(int linkKarma) {
        this.linkKarma = linkKarma;
    }

    /**
     * Returns the comment karma.
     *
     * @return Comment karma.
     * @author Benjamin Jakobus
     */
    public int getCommentKarma() {
        return commentKarma;
    }

    /**
     * Returns the UTC timestamp of when the account was created.
     *
     * @return UTC timestamp of when the account was created.
     * @author Benjamin Jakobus
     */
    public float getCreatedUTC() {
        return createdUTC;
    }

    /**
     * Returs the link karma.
     *
     * @return Link karma.
     * @author Benjamin Jakobus
     */
    public int getLinkKarma() {
        return linkKarma;
    }

    /**
     * Returns true if the account is associated with an email address; false
     * if not.
     *
     * @return True if the account is associated with an email address; false
     * if not.
     * @author Benjamin Jakobus
     */
    public boolean hasMail() {
        return hasMail;
    }

    /**
     * Returns true if the account is a moderator's account; false if not.
     *
     * @return True if the account is a moderator's account; false if not.
     * @author Benjamin Jakobus
     */
    public boolean isMod() {
        return isMod;
    }

    /**
     * Returns true if the account is associated with a moderator email address;
     * false if not.
     *
     * @return True if the account is associated with a moderator email address;
     * false if not.
     * @author Benjamin Jakobus
     */
    public boolean hasModMail() {
        return hasModMail;
    }

    /**
     * Returns true if the account has Gold membership; false if not.
     *
     * @return True if the account has Gold membership; false if not.
     * @author Benjamin Jakobus
     */
    public boolean isGold() {
        return isGold;
    }
}
