package com.bac.application;

import com.bac.components.EntityRelationship;

/**
 * Basic factory specification to return a EntityRelationshhip for a single type
 * of Entity
 * 
 * @author Simon Baird
 *
 */
public interface EntityRelationshipFactory<E,R> {
	
	EntityRelationship<E,R> newEntityRelationship();

}
