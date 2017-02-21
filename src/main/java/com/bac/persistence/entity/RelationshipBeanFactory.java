package com.bac.persistence.entity;

import com.bac.application.ApplicationRelationshipType;
import com.bac.application.RelationshipFactory;
import com.bac.application.impl.AbstractBeanFactory;

public class RelationshipBeanFactory extends AbstractBeanFactory<ApplicationRelationshipType, RelationshipImpl>
		implements RelationshipFactory<RelationshipImpl> {
	
	private static final long serialVersionUID = 7841521766725936498L;

	@Override
	public RelationshipImpl newRelationship(ApplicationRelationshipType relationshipType) {

		RelationshipImpl newRelationship = createBeanByType(relationshipType);
		newRelationship.setRelationshipType(relationshipType);
		return newRelationship;
	}
}
