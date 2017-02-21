package com.bac.components;

/**
 * Allows Entities and Relationships to be aware of all Contexts and provides a
 * default method to pass through the Context.
 * 
 * @author Simon Baird
 *
 */
public interface ContextAware {

	/**
	 * The default context processing method. It always returns true allowing it
	 * to pass through any {@code Context} that is not implemented in the body
	 * of the implementing class.
	 * 
	 * @param a
	 *            subclass of the {@code Context} interface.
	 * 
	 * @return true
	 */
	default boolean accept(Context context) {

		return true;
	}
}
