package com.bac.components;

/**
 * Encapsulates the relationship from one {@code Entity} to another. Each Entity
 * is composed of a {@code Collection } of EntityRelationships.
 * 
 * @author simon
 *
 * @param <T>
 *            the Entity class
 */
public interface EntityRelationship<E, R>  {

	E getEntity();

	R getRelationship();

}
