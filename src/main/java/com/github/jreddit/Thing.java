package com.github.jreddit;

/**
 * This class represents a reddit "thing"
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author Simon Kassing
 * @see <a href="http://www.reddit.com/dev/api#fullname">Reddit API Reference</a>
 */
public abstract class Thing {
	
    /**
     * The kind ('type') of this thing.
     * 
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'type prefixes')</a>
     */
    protected String kind;

    /**
     * The full name of this thing.
     * Combination of its kind ({@link #getKind() getKind}) and its unique ID.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names</a>
     */
    protected String fullName;

    /**
     * Retrieve the kind ('type') of this thing.
     * Example: t3 indicates a type 3 (a link).
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'type prefixes')</a>
     */
    public String getKind() {
        return kind;
    }

    /**
     * Retrieve the full name of this thing.
     * Combination of its kind (see {@link #getKind() getKind}) and its unique ID, combined with a underscore.
     * Example: t3_15bfi0 indicates a type 3 (a link) and as unique identifier 15bfi0.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names</a>
     */
    public String getFullName() {
        return fullName;
    }
    
}