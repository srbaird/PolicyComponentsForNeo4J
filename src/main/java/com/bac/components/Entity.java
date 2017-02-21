package com.bac.components;

import java.util.Collection;

/**
 * 
 * The base interface for all entities and represents a single unique
 * identifiable node of data together with a collection of relationships to
 * other entities.
 * 
 * It is made {@code ContextAware} by default so that any {@code Context} may be
 * applied to it.
 * 
 * @author Simon Baird
 *
 * @param <T>
 *            the Entity type as a paramemter
 */
public interface Entity<T extends Entity<T>> extends ContextAware {

	/**
	 * Each Entity should be able to be uniquely resolved. The type of
	 * identifier is left to the implementation. Examples might be a {@code Long} for
	 * stand alone applications or a UUID for distributed systems.
	 * 
	 * @return an object representing a unique identifying device.
	 */
	Object getEntityId();

	/**
	 * 
	 * @return {@code DataType} implementation that categorises the Entity
	 */
	DataType getDataType();

	/**
	 * 
	 * @return
	 */
	Collection<EntityRelationship<T, ? extends Relationship<? extends RelationshipType>>> getEntities();

}
