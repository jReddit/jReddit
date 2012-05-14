package org.jreddit.api;

/**
 * This class represents a reddit "thing"
 * @see <a href="https://github.com/reddit/reddit/wiki/API">Reddit API Reference</a>
 *
 * @author <a href="https://www.github.com/OmerE">Omer Elnour</a>
 */
public abstract class Thing {
	/**
	 * The kind of this thing. (i.e "t2" for users)
	 */
	public String kind;

	/**
	 * The full name of this thing.
	 * @see <a href="https://github.com/reddit/reddit/wiki/API">Reddit API Reference</a>
	 */
	public String fullName;
}