package com.omrlnr.jreddit;

/**
 * This class represents a reddit "thing"
 * @see <a href="https://github.com/reddit/reddit/wiki/API">Reddit API Reference</a>
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
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