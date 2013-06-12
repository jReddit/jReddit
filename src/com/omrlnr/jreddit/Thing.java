package com.omrlnr.jreddit;

/**
 *
 * This class represents a reddit "thing"
 * @see <a href="https://github.com/reddit/reddit/wiki/API">Reddit API Reference</a>
 * @see <a href="https://github.com/reddit/reddit/wiki/JSON#thing-reddit-base-class">thing (reddit base class)</a>
 *
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author <a href="https://github.com/jasonsimpson">Jason Simpson</a>
 *
 */
public abstract class Thing {

    /**
     * this item's identifier, e.g. "8xwlg"
     */
    protected String _id;

	/**
	 * The full name of this thing.
	 */
	protected String _name;

	/**
	 * The kind of this thing. (i.e "t2" for users)
	 */
	protected String _kind;

    /**
     * A custom data structure used to hold valuable information. 
     * This object's format will follow the data structure respective 
     * of its kind. 
     */
    protected Object _data;

    public Thing(String id, String name, String kind, Object data) {
        this._id = id;
        this._name = name;
        this._kind = kind;
        this._data = data;
    }

    public String getId() { return _id; }
    public String getName() { return _name; }
    public String getKind() { return _kind; }
    public Object getData() { return _data; }

}
