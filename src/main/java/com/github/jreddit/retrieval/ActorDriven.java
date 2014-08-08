package com.github.jreddit.retrieval;

import com.github.jreddit.entity.User;

/**
 * This interface indicates that the functions in that class and
 * how they perform are dependent on an actor ('user'). This
 * can be default null, which indicates a general actor (visitor).
 * 
 * @author Simon Kassing
 */
public interface ActorDriven {
	public void switchActor(User new_actor);
}
