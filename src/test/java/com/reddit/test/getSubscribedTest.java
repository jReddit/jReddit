package com.reddit.test;

import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.user.User;



public class getSubscribedTest {

	/**
	 * @author Karan Goel
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User("jReddittest", "jReddittest"); // Add your username and password
		try {
			user.connect();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		for (Subreddit sub : user.getSubscribed()) {
			System.out.println(sub.getName());
		}
	}

}
