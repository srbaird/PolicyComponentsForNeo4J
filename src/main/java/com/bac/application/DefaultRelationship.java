package com.bac.application;

import com.bac.components.Relationship;

/**
 * Default application level relationship.
 * 
 * @author simon
 *
 */
public interface DefaultRelationship extends Relationship<ApplicationRelationshipType> {

	/**
	 * A non-key relationship
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * 
	 * @param description
	 */
	void setDescription(String description);
}
