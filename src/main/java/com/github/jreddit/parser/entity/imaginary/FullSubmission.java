package com.github.jreddit.parser.entity.imaginary;

import java.util.List;

import com.github.jreddit.parser.entity.Submission;

public class FullSubmission {

    private Submission submission;
    private List<CommentTreeElement> commentTree;
    
    public FullSubmission(Submission submission, List<CommentTreeElement> commentTree) {
        this.submission = submission;
        this.commentTree = commentTree;
    }

    /**
     * 
     * @return the submission
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * 
     * @return the commentTree
     */
    public List<CommentTreeElement> getCommentTree() {
        return commentTree;
    }
    
    
    
}
