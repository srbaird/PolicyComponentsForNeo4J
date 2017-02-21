package com.bac.persistence.entity;

import java.util.Collection;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationRelationshipType;
import com.bac.application.PolicyEntity;
import com.bac.components.DataType;
import com.bac.components.EntityRelationship;
import com.bac.components.Relationship;
import com.bac.components.RelationshipType;

/**
 * Example implementation of a Relationship class that acts as a wrapper over an
 * Entity implementation. This enables a persistence unit to be decoupled from
 * the relationship
 * 
 * @author simon
 *
 */
public class PolicyEntityRelationship implements EntityRelationship<PolicyEntity, Relationship<ApplicationRelationshipType>>, PolicyEntity {

	private PolicyEntityImpl entity;
	private Relationship<ApplicationRelationshipType> relationship;


	@Override
	public Relationship<ApplicationRelationshipType> getRelationship() {

		return relationship;
	}

	@Override
	public PolicyEntityImpl getEntity() {

		return entity;
	}

	@Override
	public Object getEntityId() {

		return entity.getEntityId();
	}

	@Override
	public DataType getDataType() {

		return entity.getDataType();
	}

	@Override
	public ApplicationDataType getPolicyDataType() {

		return entity.getPolicyDataType();
	}

	@Override
	public void setPolicyDataType(ApplicationDataType dataType) {

		entity.setPolicyDataType(dataType);
	}

	@Override
	public Collection<EntityRelationship<PolicyEntity, ? extends Relationship<? extends RelationshipType>>> getEntities() {

		return entity.getEntities();
	}



	/* Protected methods */

	void setEntity(PolicyEntityImpl entity) {
		this.entity = entity;
	}

	void setRelationship(Relationship<ApplicationRelationshipType> relationship) {
		this.relationship = relationship;
	}
}
