package com.bac.application.impl;

import com.bac.application.ApplicationRelationshipType;

/**
 * Simple implementation of {@code RelationshipType}
 * 
 * @author Simon Baird
 *
 */
public enum ApplicationRelationshipTypes implements ApplicationRelationshipType {

	ASSIGNEE_OF, LINKS_TO, PARENT_OF, OWNER_OF, UNRESOLVED;
}
