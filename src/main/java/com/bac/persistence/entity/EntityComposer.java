package com.bac.persistence.entity;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import com.bac.application.predicate.Where;

/**
 * Definition of a class to construct macro entities from persistence unit
 * output.
 * 
 * @author Simon Baird
 *
 * @param <E>
 *            the {@code Entity} type to create
 * @param <R>
 *            the {@code Relationship} type to create
 */
public interface EntityComposer<E, R> {

	/**
	 * Compose a macro entity by traversing the relationships
	 * 
	 * @param relationships
	 *            a stream of relationships leading from the root node
	 * @param rootNode
	 *            the persistence node of the entity to compose
	 * @return a populated macro entity of type T
	 */
	E composeEntity(Iterable<Relationship> relationships, Node rootNode);

	/**
	 * Convenience method to create a Relationship of the appropriate type from
	 * the neo4j Relationship node
	 * 
	 * @param relationship
	 *            neo4j Relationship to transform
	 */
	R composeRelationship(org.neo4j.graphdb.Relationship relationship);

	/**
	 * Apply the supplied {@code Entity} to the {@code Where} predicate and
	 * reduce the entity visibility to that required by the predicate
	 * 
	 * @param entity
	 *            a composed entity of type E
	 * @param predicates
	 *            a conditional predicate to apply to each of the entity
	 *            elements
	 * @return the entity parameter transformed to the predicate
	 */
	E applyContext(E entity, Where predicate);
}
