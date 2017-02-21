package com.bac.application;

/**
 * Basic specification for a factory to generate concrete relationships from a
 * {@code RelationshipType}
 * 
 * @author Simon Baird
 *
 */
public interface RelationshipFactory<T> {

	T newRelationship(ApplicationRelationshipType relationshipType);
}
