
package com.bac.persistence.accessor;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.traversal.TraversalDescription;

/**
 * Implementation of the strategy pattern to allow Neo4j
 * {@code TraversalDescription}s to be injected into the persistence accessor.
 *
 * @author Simon Baird
 */
public interface TraversalStrategy {

	/**
	 * Get the {@code TraversalDescription} specific to this implementation.
	 * 
	 * @param graphDb
	 *            an instance of a {@code GraphDatabaseService}
	 * @return a populated {@code TraversalDescription}
	 */
	TraversalDescription getTraversalDescription(GraphDatabaseService graphDb);

	/**
	 * Get the {@code TraversalDescription} specific to this implementation. The
	 * level is used to control the evaluation depth of the path.
	 * 
	 * @param graphDb
	 *            an instance of a {@code GraphDatabaseService}
	 * @param level
	 *            the evaluation depth supplied to the {@code Evaluators} class
	 * @return a populated {@code TraversalDescription}
	 */
	TraversalDescription getTraversalDescription(GraphDatabaseService graphDb, int level);
}
