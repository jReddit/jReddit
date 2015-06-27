package com.github.jreddit.parser.util;

import java.util.ArrayList;
import java.util.List;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;

public class CommentTreeUtils {
    
    private CommentTreeUtils() {
        // Empty to disallow the invocation of the default constructor for this utility class
    }
    
    /**
     * Flatten the comment tree.
     * The original comment tree is not overwritten.
     * 
     * @param cs        List of comments that you get returned from one of the other methods here
     * 
     * @return    Flattened comment tree.
     */
    public static List<CommentTreeElement> flattenCommentTree(List<CommentTreeElement> commentTree) {
        List<CommentTreeElement> target = new ArrayList<CommentTreeElement>();
        flattenCommentTree(commentTree, target);
        return target;
    }
    
    /**
     * Flatten the comment tree.
     * The original comment tree is not overwritten.
     * 
     * @param cs        List of comments that you get returned from one of the other methods here
     * @param target    List in which to place the flattened comment tree.
     */
    private static void flattenCommentTree(List<CommentTreeElement> commentTree, List<CommentTreeElement> target) {
        for (CommentTreeElement c : commentTree) {
            target.add(c);
            if (c instanceof Comment) {
                flattenCommentTree(((Comment)c).getReplies(), target);
            }
        }
    }
    
    /**
     * Get printable version of the given comment tree.
     * 
     * @param cs     List of comment tree elements
     * 
     * @return Printable comment tree
     */
    public static String printCommentTree(List<CommentTreeElement> cs) {
        StringBuilder builder = new StringBuilder();
        for (CommentTreeElement c : cs) {
            builder.append(printCommentTree(c, 0));
        }
        return builder.toString();
    }
    
    /**
     * Get printable version of a comment at a specific level.<br>
     * <br>
     * <i>Note: uses unsafe recursion</i>
     * 
     * @param c            Comment
     * @param level        Level to place at
     * 
     * @return Printable comment tree
     */
    private static String printCommentTree(CommentTreeElement c, int level) {
        
        // Initialize empty buffer
        StringBuilder builder = new StringBuilder();
        
        // Add tabulation
        for (int i = 0; i < level; i++) {
            builder.append("\t");
        }
        
        // Comment string
        builder.append(c.toString());
        builder.append("\n");
        
        // Iterate over children
        if (c instanceof Comment) {
            for (CommentTreeElement child : ((Comment) c).getReplies()) {
                builder.append(printCommentTree(child, level + 1));
            }
        }
        
        // Return the buffer
        return builder.toString();
        
    }
    
}
