package com.bac.persistence.entity;

import java.util.Map;

import com.bac.application.DefaultRelationship;

/**
 * Default persistence specific implementation of the application
 * {@code DefaultRelationship}. Only a description is added to the abstract case
 * 
 * @author Simon Baird
 *
 */
public class DefaultRelationshipImpl extends RelationshipImpl implements DefaultRelationship {

	//
	public static final String DESCRIPTION_PROPERTY_NAME = "DESCRIPTION";
	private String description;

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public void setDescription(String description) {

		this.description = description;
	}

	/* Protected methods */

	@Override
	Map<String, Object> getProperties() {

		Map<String, Object> properties = super.getProperties();
		properties.put(DESCRIPTION_PROPERTY_NAME, getDescription());
		return properties;
	}

	void setRelationship(org.neo4j.graphdb.Relationship relationship) {

		// Pass up the chain
		super.setRelationship(relationship);

		if (relationship != null && relationship.hasProperty(DESCRIPTION_PROPERTY_NAME)) {

			setDescription((String) relationship.getProperty(DESCRIPTION_PROPERTY_NAME));

		} else {

			setDescription(null);
		}
	}
}
