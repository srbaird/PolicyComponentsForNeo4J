
package com.bac.persistence.entity;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bac.persistence.accessor.GraphEntityDAOImpl;

/**
 * Shared test resources
 *
 * @author Simon Baird
 */

public abstract class AbstractEntityTest extends AbstractSpringTest {


	@Resource(name = "persistenceDAO")
	GraphEntityDAOImpl dao;

	@Resource(name = "graphDatabaseService")
	private GraphDatabaseService graphDb;

	@After
	/**
	 * Manual intervention to clear down database at end of test.
	 * 
	 * Code lifted from org.springframework.data.neo4j.support.node.Neo4jHelper.
	 * Slight changes made as getAllNodes() is now deprecated and moved to
	 * GlobalGraphOperations.
	 */
	public void clearDB() {

		try (Transaction tx = graphDb.beginTx()) {

			for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
				for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
					rel.delete();
				}
			}

			for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {

				node.delete();
			}
			IndexManager indexManager = graphDb.index();
			for (String ix : indexManager.nodeIndexNames()) {
				indexManager.forNodes(ix).delete();
			}
			for (String ix : indexManager.relationshipIndexNames()) {
				indexManager.forRelationships(ix).delete();
			}
			tx.success();
		}

	}

	Calendar calendar;
	//
	// logger
	static Logger logger = LoggerFactory.getLogger(AbstractEntityTest.class);


	//
	// Set of dates for Context testing
	//
	Date getYesterday() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date getToday() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date getTomorrow() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date dateOnly(Date date) {
		//
		// Strip the time component from the date
		//
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // Remove the time component ...
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTimeInMillis());
	}
}
