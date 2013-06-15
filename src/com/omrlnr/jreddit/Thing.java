package com.omrlnr.jreddit;

import org.json.simple.JSONObject;


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
    protected JSONObject _data;

    public Thing(JSONObject data) {
        _data = data;
    }

    /*
    public Thing(String id, String name, String kind, Object data) {
        this._id = id;
        this._name = name;
        this._kind = kind;
        this._data = (JSONObject)data;
    }
    */

    public String getId() {
        return (String)((JSONObject)(_data.get("data"))).get("id");
    }

    public String getName() { 
        return (String)((JSONObject)(_data.get("data"))).get("name");
    }

    public String getKind() {
        return (String)_data.get("kind");
        // return (String)((JSONObject)(_data.get("data"))).get("kind");
    }

    /**
     * This class and its subclasses should provide convenience methods for
     * accessing data. But if the underlying
     * json data changes or we do not provide the caller with
     * the required methods, they can obtain all underlying data directly 
     * using this method.
     */
    public JSONObject getData() { return _data; }

    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        return  indent + "Thing: \n" +
                indent + "   id: "   + getId()   + "\n" +
                indent + "   name: " + getName() + "\n" +
                indent + "   kind: " + getKind() + "\n";
    }

}
