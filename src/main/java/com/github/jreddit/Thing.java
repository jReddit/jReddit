package com.github.jreddit;

import com.github.jreddit.utils.Kind;

/**
 * This class represents a reddit "thing"
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author Simon Kassing
 * @see <a href="http://www.reddit.com/dev/api#fullname">Reddit API Reference</a>
 */
public abstract class Thing {
    
	/**
	 * Constructor. Must be called.
	 * @param name
	 */
	public Thing(String name) {
		this.fullName = name;
		String[] split = name.split("_");
		this.kind = split[0];
		this.identifier = split[1];
	}
	
    /**
     * The kind ('type') of this thing.
     * 
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'type prefixes')</a>
     */
    protected final String kind;

    /**
     * The identifier of this thing.
     * 
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'identifier')</a>
     */
    protected final String identifier;
    
    /**
     * The full name of this thing.
     * Combination of its kind ({@link #getKind() getKind}) and its unique ID.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names</a>
     */
    protected final String fullName;

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
     * Retrieve the identifier of this thing.
     * Example: 15bfi0.
     *
     * @see <a href="http://www.reddit.com/dev/api#fullnames">Reddit API Reference for full names (section 'identifier')</a>
     */
    public String getIdentifier() {
        return identifier;
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