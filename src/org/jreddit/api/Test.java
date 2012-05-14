package org.jreddit.api;

import org.jreddit.api.submissions.Submission;
import org.jreddit.api.submissions.Submissions;
import org.jreddit.api.user.User;

public final class Test {
	public static void main(String[] args) throws Exception {
		User user = new User("username", "password");
		user.connect();

		System.out.println(user.commentKarma());
		System.out.println(user.linkKarma());
		System.out.println(user.hasMail());
		System.out.println(user.isGold());
		System.out.println(user.getModhash());

		Submission submission = Submissions.getSubmissions("programming",
				Submissions.HOT, Submissions.FRONTPAGE, user).get(0);

		System.out.println(submission.commentCount());
		System.out.println(submission.downVotes());
		System.out.println(submission.upVotes());
		System.out.println(submission.getAuthor());
		System.out.println(submission.getScore());
	}
}