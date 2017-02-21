package com.bac.components;

/**
 * Simplest form of a relationship to an Entity
 * 
 * @author simon
 *
 */
public interface Relationship<T extends RelationshipType> extends ContextAware {

	/**
	 * Each Relationship should be able to be uniquely resolved. The type of
	 * identifier is left to the implementation. Examples might be a {@code Long} for
	 * stand alone applications or a UUID for distributed systems.
	 * 
	 * @return an object representing a unique identifying device.
	 */
	Object getRelationshipId();
	
	/**
	 * 
	 * @return {@code RelationshipType} implementation that categorises the Relationship
	 */
	T getRelationshipType();
}
