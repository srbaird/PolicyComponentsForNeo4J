
package com.bac.persistence.accessor;

import static org.neo4j.graphdb.traversal.Uniqueness.NONE;

import java.util.Objects;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.springframework.beans.factory.annotation.Autowired;

import com.bac.application.ApplicationRelationshipType;
import com.bac.application.RelationshipTypeResolver;

/**
 * The entity policy traversal accepts all outgoing relationship types.
 * 
 * @author Simon Baird
 */
public final class PolicyRelationshipTraversalStrategy extends AbstractTraversalStrategy {

	private RelationshipTypeResolver<ApplicationRelationshipType> relationshipTypeResolver;
	final String NULL_RELATIONSHIP_RESOLVER = "No RelationshipType resolver has been supplied";

	@Autowired
	public void setRelationshipTypeResolver(
			RelationshipTypeResolver<ApplicationRelationshipType> relationshipTypeResolver) {
		this.relationshipTypeResolver = relationshipTypeResolver;
	}

	@Override
	TraversalDescription createTraversalDescription(GraphDatabaseService graphDb) {

		Objects.requireNonNull(relationshipTypeResolver, NULL_RELATIONSHIP_RESOLVER);
		Objects.requireNonNull(graphDb, NULL_GRAPHDB_MSG);

		TraversalDescription newDescription = graphDb.traversalDescription().depthFirst();
		//
		// Policy types may be represented in all the relationship types so add
		// them all into the description
		//
		newDescription = buildRelationships(newDescription, relationshipTypeResolver.allRelationshipTypes(),
				Direction.OUTGOING);

		return newDescription.uniqueness(NONE);
	}
}
