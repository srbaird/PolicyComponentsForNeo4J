
package com.bac.persistence.accessor;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;

import com.bac.application.ApplicationRelationshipType;

/**
 * Default implementation of the {@code TraversalStrategy} interface.
 * @author Simopn Baird
 */
public abstract class AbstractTraversalStrategy implements TraversalStrategy { 

    TraversalDescription description;
    //

    final String NULL_GRAPHDB_MSG = "Supplied database instance is null";


    @Override
    public TraversalDescription getTraversalDescription(GraphDatabaseService graphDb) {
        
        if (description == null) {
            description = createTraversalDescription(graphDb);
        }
        return description;
    }

    @Override
    public TraversalDescription getTraversalDescription(GraphDatabaseService graphDb, int level) {

        return getTraversalDescription(graphDb).evaluator(Evaluators.toDepth(level));
    }

    /**
     * Compose a set of {@code RelationshipType}s into the {@code TraversalDescription}
     * 
     * @param description the {@code TraversalDescription} to decorate
     * @param relationships a set of {@code ApplicationRelationshipType}s to be included in the traversal
     * @param direction the {@code Direction} associated with the 
     * @return
     */
    TraversalDescription buildRelationships(TraversalDescription description, Set<? extends ApplicationRelationshipType> relationships, Direction direction) {

        for (ApplicationRelationshipType relationship : relationships) {
            RelationshipType relationshipType = DynamicRelationshipType.withName(relationship.name());
            description = description.relationships(relationshipType, direction);
        }
        return description;
    }

    abstract TraversalDescription createTraversalDescription(GraphDatabaseService graphDb);

}
