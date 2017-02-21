package com.bac.persistence.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationPersistenceException;
import com.bac.application.ApplicationRelationshipType;
import com.bac.application.DataTypeResolver;
import com.bac.application.PolicyEntity;
import com.bac.application.PolicyEntityFactory;
import com.bac.application.RelationshipFactory;
import com.bac.application.RelationshipTypeResolver;
import com.bac.application.impl.ApplicationDataTypes;
import com.bac.application.predicate.Where;
import com.bac.components.DataType;
import com.bac.components.EntityRelationship;
import com.bac.components.Relationship;
import com.bac.components.RelationshipType;

/**
 * Is responsible for composing entity relationships into macro entities
 * 
 * @author Simon Baird
 *
 */
public class PolicyEntityComposer implements EntityComposer<PolicyEntityImpl, RelationshipImpl> {

	// TEMP
	// TODO: Move this to an application name space
	private final DataType POLICY_DATATYPE = ApplicationDataTypes.POLICY;
	//
	// Persistence dependencies
	//
	private PolicyEntityFactory<PolicyEntityImpl> entityFactory;

	private RelationshipFactory<RelationshipImpl> relationshipFactory;

	private PolicyEntityRelationshipBeanFactory entityRelationshipFactory;

	private DataTypeResolver<ApplicationDataType> dataTypeResolver;

	private RelationshipTypeResolver<ApplicationRelationshipType> relationshipTypeResolver;
	//
	// Class fields
	//
	private final String ILLEGAL_RELATIONSHIP_MSG = "Policy entity is in relationship with unsupported entity type";
	private final String ILLEGAL_DATATYPE_MSG = "No DataType was found for the Policy entity";
	//
	private static final Logger logger = LoggerFactory.getLogger(PolicyEntityComposer.class);

	// ========================================================================================
	// Setter methods
	// ========================================================================================
	@Resource(name = "policyEntityFactory") // Can't Autowire as DAO also
											// implements Factory interface
	public void setPolicyEntityFactory(PolicyEntityFactory<PolicyEntityImpl> entityFactory) {

		this.entityFactory = entityFactory;
	}

	@Autowired
	public void setRelationshipFactory(RelationshipFactory<RelationshipImpl> relationshipFactory) {
		this.relationshipFactory = relationshipFactory;
	}

	@Autowired
	public void setEntityRelationshipFactory(PolicyEntityRelationshipBeanFactory entityRelationshipFactory) {
		this.entityRelationshipFactory = entityRelationshipFactory;
	}

	@Autowired
	public void setRelationshipTypeResolver(
			RelationshipTypeResolver<ApplicationRelationshipType> relationshipTypeResolver) {
		this.relationshipTypeResolver = relationshipTypeResolver;
	}

	@Autowired
	public void setDataTypeResolver(DataTypeResolver<ApplicationDataType> dataTypeResolver) {
		this.dataTypeResolver = dataTypeResolver;
	}
	// ========================================================================================
	//
	// Methods for the EntityComposer specification
	//
	// ========================================================================================

	@Override
	public PolicyEntityImpl composeEntity(Iterable<org.neo4j.graphdb.Relationship> relationships, Node rootNode) {

		final PolicyEntityImpl policyEntity = createPolicyEntity(rootNode);
		//
		// Transform relationships into EntityRelationships
		//
		for (org.neo4j.graphdb.Relationship nextRelationship : relationships) {

			Node targetNode = nextRelationship.getEndNode();
			//
			// is destination node another Policy?
			//
			if (getDataTypeFromNode(targetNode) != POLICY_DATATYPE) {
				throw new ApplicationPersistenceException(ILLEGAL_RELATIONSHIP_MSG);
			}

			// Create the target entity
			final PolicyEntityImpl targetEntity = createPolicyEntity(targetNode);

			// Build a Relationship
			final Relationship<ApplicationRelationshipType> targetRelationship = composeRelationship(nextRelationship);

			// Combine them into an EntityRelationship
			PolicyEntityRelationship entityRelationship = entityRelationshipFactory.newEntityRelationship();
			entityRelationship.setEntity(targetEntity);
			entityRelationship.setRelationship(targetRelationship);

			policyEntity.addEntity(entityRelationship);
		}
		return policyEntity;
	}

	/* protected methods */

	PolicyEntityImpl createPolicyEntity(Node node) {

		PolicyEntityImpl returnComponent = entityFactory.newPolicyEntity();
		returnComponent.setNode(node, dataTypeResolver);
		return returnComponent;
	}

	public RelationshipImpl composeRelationship(org.neo4j.graphdb.Relationship relationship) {

		final ApplicationRelationshipType relationshipType = relationshipTypeResolver
				.getRelationshipType(relationship.getType().name());

		RelationshipImpl returnRelationship = relationshipFactory.newRelationship(relationshipType);
		returnRelationship.setRelationship(relationship);
		return returnRelationship;
	}

	/*
	 * Scan the labels for one that fits as a DataType. If there is not exactly
	 * one data type then throw an exception
	 */
	private DataType getDataTypeFromNode(Node node) {

		Set<DataType> dataTypeLabels = new HashSet<>();
		for (Label label : node.getLabels()) {
			String labelName = label.name();
			DataType labelDataType = dataTypeResolver.getDataType(labelName);
			if (labelDataType != null) {
				dataTypeLabels.add(labelDataType);
			}
		}

		if (dataTypeLabels.size() != 1) {
			logger.error("Found {} data type labels on node '{}'", dataTypeLabels.size(), node);
			throw new ApplicationPersistenceException(ILLEGAL_DATATYPE_MSG);
		}
		return dataTypeLabels.stream().findFirst().get();
	}

	@Override
	public PolicyEntityImpl applyContext(PolicyEntityImpl entity, Where predicate) {

		if (predicate == null) {
			return entity;
		}
		
		if (!predicate.apply(entity)) {

			return null;
		}

		
		return recursiveContextFilter(entity, predicate);
	}

	//
	// Filter out the relationships that are not represented by the context
	// predicate
	//
	private PolicyEntityImpl recursiveContextFilter(PolicyEntityImpl entity, Where predicate) {
		
		if (!entity.getEntities().isEmpty()) {

			// Filter the list of entity relationships

			List<EntityRelationship<PolicyEntity, ? extends Relationship<? extends RelationshipType>>> contextEntities = entity
					.getEntities().stream()
					.filter(e -> predicate.apply(e.getEntity()) && predicate.apply(e.getRelationship()))
					.collect(Collectors.toList());

			entity.clearEntities();

			contextEntities.stream().forEach(e -> entity.addEntity((PolicyEntityRelationship) e));

			for (EntityRelationship<PolicyEntity, ? extends Relationship<? extends RelationshipType>> er : entity.getEntities()) {
				PolicyEntityImpl nextEntity = (PolicyEntityImpl) er.getEntity();
				recursiveContextFilter(nextEntity, predicate);
			}
			// Recurse over the list of entities
//			entity.getEntities().stream()
//					.forEach(er -> recursiveContextFilter((PolicyEntityImpl) er.getEntity(), predicate));
		}


		return entity;
	}
}
