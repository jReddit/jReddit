package com.github.jreddit.parser.entity.imaginary;

/**
 * Interface to abstract over the two possible elements in a
 * mixed listing, namely a {@link Comment} or a {@link Submission} thing. 
 * If an object is of the type of this interface, it means that it <i>must</i>
 * be either of the respective {@link Comment} or {@link Submission} class. 
 * 
 * @author Simon Kassing
 * 
 * @see com.github.jreddit.parser.entity.Comment
 * @see com.github.jreddit.parser.entity.Submission
 */
public interface MixedListingElement {
 // Empty because only an abstraction for either a Comment or a Submission thing
}
