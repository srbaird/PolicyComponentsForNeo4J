package com.bac.application;

import java.util.Set;

/**
 * Specification for a class to generate {@code RelationshipType} instances from
 * a string key
 * 
 * @author Simon Baird
 *
 */
public interface RelationshipTypeResolver<T> {

	/**
	 * Returns the {@code RelationshipType} implementation which is mapped by
	 * the String.
	 * 
	 * @param relationshipTypeName
	 *            the String representation of the {@code RelationshipType}
	 * 
	 * @return an implementation of the {@code RelationshipType } interface or
	 *         null if it cannot be mapped
	 */
	T getRelationshipType(String relationshipTypeName);

	/**
	 * Gets all the available types
	 * 
	 * @return a {@code Set} of all the possible {@code RelationshipType}s
	 */
	Set<T> allRelationshipTypes();
}
