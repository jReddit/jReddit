package com.github.jreddit.parser.entity.imaginary;

/**
 * Interface to abstract over the two possible elements in a
 * comment tree, namely a {@link Comment} or a {@link More} thing. If an object
 * is of the type of this interface, it means that it <i>must</i>
 * be either an {@link Comment} or a {@link More} thing.
 * 
 * @author Simon Kassing
 * 
 * @see com.github.jreddit.parser.entity.Comment
 * @see com.github.jreddit.parser.entity.More
 */
public interface CommentTreeElement {
    // Empty because only an abstraction for either a Comment or a More thing
}
