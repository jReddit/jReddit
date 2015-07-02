package com.github.jreddit.parser.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.util.JsonUtils;

/**
 * MORE entity. Can only exist in a comment tree, and thus
 * implements the <i>CommenTreeElement</i> interface.
 * 
 * @author Simon Kassing
 */
public class More extends Thing implements CommentTreeElement {

    /** List of comment identifiers (ID36) that are his children. */
    private List<String> children;
    
    /** Counting number assigned by reddit (does not tell much in a comment tree). */
    private Long count;
    
    /** Parent comment fullname. */
    private String parentId;
    
    /**
     * Construct a "More" thing.
     * 
     * @param obj JSON object
     */
    public More(JSONObject obj) {
        super(Kind.MORE.value() + "_NONE");
        
        // The obj.get("name") and obj.get("id") are neglected, as these
        // are already implicitly included in the children array.
        
        // Retrieve count from JSON
        this.count = JsonUtils.safeJsonToLong(obj.get("count"));
        
        // Retrieve parent identifier from JSON
        this.parentId = JsonUtils.safeJsonToString(obj.get("parent_id"));
        
        // Iterate over children
        this.children = new ArrayList<String>();
        JSONArray jsonChildren = (JSONArray) obj.get("children");
        for (Object child : jsonChildren) {
            this.children.add(JsonUtils.safeJsonToString(child));
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
     * Retrieve the counting number assigned by reddit (does not tell much in this case).
     * 
     * @return The counting number
     */
    public Long getCount() {
        return count;
    }
    
    /**
     * Retrieve how many children (comments) the MORE hides.
     * 
     * @return How many comments the more hides ({@link #getChildren()}'s size)
     */
    public int getChildrenSize() {
        return children.size();
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
    
    @Override
    public String toString() {
        return "More()<" + this.getChildrenSize() + " more directly underneath>";
    }
    
}
