package com.bac.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import com.bac.application.ApplicationRelationshipType;
import com.bac.components.Relationship;

/**
 * Base class for all persistence implementations of a Relationship
 * 
 * @author Simon Baird
 *
 */
public class RelationshipImpl implements Relationship<ApplicationRelationshipType> {

	private Long id;
	//
	private ApplicationRelationshipType relationshipType;

	@Override
	public Long getRelationshipId() {

		return id;
	}

	/**
	 * Access restricted
	 * 
	 * @param id
	 */
	void setRelationshipId(Long id) {

		this.id = id;
	}

	@Override
	public ApplicationRelationshipType getRelationshipType() {

		return relationshipType;
	}

	/**
	 * Access restricted
	 * 
	 * @param relationshipType
	 */
	void setRelationshipType(ApplicationRelationshipType relationshipType) {

		this.relationshipType = relationshipType;
	}

	/* protected methods */

	/**
	 * No properties to set at this level
	 * @return
	 */
	Map<String, Object> getProperties() {

		return new HashMap<>();
	}

	/**
	 * The Relationship type is handled separately
	 * 
	 * @param relationship
	 */
	void setRelationship(org.neo4j.graphdb.Relationship relationship) {

		if (relationship == null) {

			clearProperties();

		} else {

			setProperties(relationship);
		}
	}

	private void setProperties(org.neo4j.graphdb.Relationship relationship) {

		setRelationshipId(relationship.getId());
	}

	private void clearProperties() {

		setRelationshipId(null);
	}
}
