package com.github.jreddit.parser.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.util.JsonUtils;

public class More extends CommentTreeElement {

	/** List of comment identifiers (ID36) that are his children. */
	private List<String> children;
	
	/** How many comments are hiding underneath this "More". */
	private int count;
	
	/** Parent comment fullname. */
	private String parentId;
	
	/**
	 * Construct a "More" thing.
	 * 
	 * @param obj JSON object
	 */
    public More(JSONObject obj) {
    	super(Kind.MORE.value() + "_");
    	
    	// The obj.get("name") and obj.get("id") are neglected, as these
    	// are already included in the children array.
    	
    	// Retrieve count from JSON
    	this.count = JsonUtils.safeJsonToInteger(obj.get("count"));
    	
    	// Retrieve parent identifier from JSON
    	this.parentId = JsonUtils.safeJsonToString(obj.get("parent_id"));
    	
    	// Iterate over children
    	this.children = new ArrayList<String>();
    	JSONArray children = (JSONArray) obj.get("parent_id");
    	for (Object child : children) {
    		this.children.add((String) child);
    	}
    	
    }

	/**
	 * Retrieve the children ID36 comment identifiers.
	 * 
	 * @return The children (e.g. ["dja241", "dsfjak24"])
	 */
	public List<String> getChildren() {
		return children;
	}

	/**
	 * Retrieve the count (how many comments are hiding underneath this "More").
	 * 
	 * @return The count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Retrieve the parent fullname comment identifier.
	 * 
	 * @return The parent identifier (e.g. "t1_38942f")
	 */
	public String getParentId() {
		return parentId;
	}

	@Override
	public int compareTo(Thing o) {
		if (!(o instanceof More)) {
			return 1;
		} else {
			return ((More) o).getChildren().equals(this.getChildren()) ? 0 : -1;
		}
		
	}
	
}
