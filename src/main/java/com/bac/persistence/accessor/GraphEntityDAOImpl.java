
package com.bac.persistence.accessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationPersistenceException;
import com.bac.application.ApplicationRelationshipType;
import com.bac.application.PolicyEntityDAO;
import com.bac.application.PolicyEntityFactory;
import com.bac.application.RelationshipFactory;
import com.bac.application.impl.ApplicationDataTypes;
import com.bac.components.Relationship;
import com.bac.persistence.entity.EntityComposer;
import com.bac.persistence.entity.EntityImplUtilities;
import com.bac.persistence.entity.PolicyEntityImpl;
import com.bac.persistence.entity.RelationshipImpl;

/**
 * Neo4j implementation of an application specific data accessor.
 * 
 * @author Simon Baird
 */
public class GraphEntityDAOImpl implements PolicyEntityDAO<PolicyEntityImpl, RelationshipImpl> {

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	// TEMP
	// TODO: Move this to an application name space
	private final ApplicationDataType POLICY_DATATYPE = ApplicationDataTypes.POLICY;
	//
	// Neo4j dependencies
	//
	private GraphDatabaseService graphDb;
	//
	// Defines a Traversal Description specific to the Policy entities
	//
	private TraversalStrategy policyTraversalStrategy;
	//
	// Factory for creating Ne04j aware entities based on a DataType
	//
	private PolicyEntityFactory<PolicyEntityImpl> entityFactory;
	//
	// Builds macro entities based on a traversal path
	//
	private EntityComposer<PolicyEntityImpl, RelationshipImpl> entityComposer;
	//
	// Factory for creating Ne04j aware relationships based on a
	// RelationshipType
	//
	private RelationshipFactory<RelationshipImpl> relationshipFactory;
	//
	// Local definitions
	//
	private final String NULL_PARAMETER_MSG = "The supplied parameter is null";
	private final String UNKNOWN_ID_MSG = "The entity id was not found";
	//
	// logger
	//
	private static final Logger logger = LoggerFactory.getLogger(GraphEntityDAOImpl.class);

	@Autowired
	public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {

		if (graphDatabaseService == null) {
			logger.warn("Null Database service supplied ");
			if (graphDb != null) {
				logger.warn("Shutting down the existing database service ");
				graphDb.shutdown();
				graphDb = null;
			}
			return;
		}
		graphDb = graphDatabaseService;
		//
		registerShutdownHook(graphDb);
	}

	public GraphDatabaseService getGraphDb() {
		return graphDb;
	}

	// ========================================================================================
	// Setter methods
	// ========================================================================================
	@Autowired
	public void setPolicyEntityFactory(PolicyEntityFactory<PolicyEntityImpl> entityFactory) {

		this.entityFactory = entityFactory;
	}

	@Autowired
	public void setPolicyTraversalStrategy(TraversalStrategy policyTraversalStrategy) {

		this.policyTraversalStrategy = policyTraversalStrategy;
	}

	@Autowired
	public void setEntityComposer(EntityComposer<PolicyEntityImpl, RelationshipImpl> entityComposer) {

		this.entityComposer = entityComposer;
	}

	@Autowired
	public void setRelationshipFactory(RelationshipFactory<RelationshipImpl> relationshipFactory) {

		this.relationshipFactory = relationshipFactory;
	}

	// ========================================================================================
	//
	// Methods for the PolicyEntityDAO specification
	//
	// ========================================================================================
	@Override
	public PolicyEntityImpl newPolicyEntity() {

		return entityFactory.newPolicyEntity();
	}

	@Override
	public RelationshipImpl newRelationship(ApplicationRelationshipType relationshipType) {

		return relationshipFactory.newRelationship(relationshipType);
	}

	@Override
	/**
	 * Create the Node and read it back as an entity
	 */
	public PolicyEntityImpl createPolicyEntity(PolicyEntityImpl policyEntity) {

		Objects.requireNonNull(policyEntity, NULL_PARAMETER_MSG);

		PolicyEntityImpl returnPolicyEntity;
		try (Transaction tx = graphDb.beginTx()) {
			Node node = _createEntityWithinTransaction(policyEntity);
			returnPolicyEntity = _readPolicyComponentWithinTransaction(node);
			tx.success();
		}
		return returnPolicyEntity;
	}

	private Node _createEntityWithinTransaction(PolicyEntityImpl policyEntity) {

		Node node = graphDb.createNode();
		Label label = DynamicLabel.label(policyEntity.getDataType().name());
		node.addLabel(label);
		for (Entry<String, Object> entry : EntityImplUtilities.getProperties(policyEntity).entrySet()) {
			if (entry.getValue() != null) {
				node.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return node;
	}

	@Override
	public void deletePolicyEntity(PolicyEntityImpl policyEntity) {

		validateEntity(policyEntity);

		try (Transaction tx = graphDb.beginTx()) {
			_deleteEntityComponentWithinTransaction(policyEntity);
			tx.success();
		} catch (NotFoundException e) {
			// Convert to standard Application exception for consistency
			throw new ApplicationPersistenceException(UNKNOWN_ID_MSG);
		}
	}

	private void _deleteEntityComponentWithinTransaction(PolicyEntityImpl policyEntity) {

		Node node = graphDb.getNodeById(policyEntity.getEntityId());
		// Delete the relationships
		// for (org.neo4j.graphdb.Relationship relationship :
		// node.getRelationships()) {
		// relationship.delete();
		// }
		// Delete the node
		node.delete();
	}

	@Override
	public PolicyEntityImpl readPolicyEntity(PolicyEntityImpl policyEntity) {

		Objects.requireNonNull(policyEntity, NULL_PARAMETER_MSG);

		return readPolicyEntity(policyEntity.getEntityId());
	}

	@Override
	public PolicyEntityImpl readPolicyEntity(Long id) {

		Objects.requireNonNull(id, NULL_PARAMETER_MSG);

		PolicyEntityImpl returnComponent = null;
		Node node;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.getNodeById(id);
			returnComponent = _readPolicyComponentWithinTransaction(node);
			tx.success();
		} catch (NotFoundException e) {
			// Trap the not found exception as it's not unusual
			logger.trace("Policy entity id '{}' not found.", id);
		}
		return returnComponent;
	}

	private PolicyEntityImpl _readPolicyComponentWithinTransaction(Node policyNode) {

		TraversalDescription traversalDescription = policyTraversalStrategy.getTraversalDescription(graphDb, 1);
		return entityComposer.composeEntity(traversalDescription.traverse(policyNode).relationships(), policyNode);
	}

	@Override
	public PolicyEntityImpl updatePolicyEntity(PolicyEntityImpl policyEntity) {

		validateEntity(policyEntity);

		PolicyEntityImpl returnPolicyEntity = null;
		try (Transaction tx = graphDb.beginTx()) {
			Node node = _updateEntityComponentWithinTransaction(policyEntity);
			returnPolicyEntity = _readPolicyComponentWithinTransaction(node);
			tx.success();
		} catch (NotFoundException e) {
			// Convert to standard Application exception for consistency
			throw new ApplicationPersistenceException(UNKNOWN_ID_MSG);
		}
		return returnPolicyEntity;
	}

	private Node _updateEntityComponentWithinTransaction(PolicyEntityImpl policyEntity) {

		Node node = graphDb.getNodeById(policyEntity.getEntityId());
		// remove all the existing properties
		for (String key : node.getPropertyKeys()) {
			node.removeProperty(key);
		}
		// Rebuild properties from Entity

		for (Entry<String, Object> entry : EntityImplUtilities.getProperties(policyEntity).entrySet()) {
			if (entry.getValue() != null) {
				node.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return node;
	}

	@Override
	public List<PolicyEntityImpl> listPolicies() {

		List<PolicyEntityImpl> returnList;
		try (Transaction tx = graphDb.beginTx()) {
			returnList = _listPoliciesWithinTransaction();
			tx.success();
		}
		return returnList;
	}

	private synchronized List<PolicyEntityImpl> _listPoliciesWithinTransaction() {

		List<PolicyEntityImpl> returnList = new ArrayList<>();
		GlobalGraphOperations operations = GlobalGraphOperations.at(graphDb);
		// Get the Entity Class nodes
		Label label = DynamicLabel.label(POLICY_DATATYPE.name());
		ResourceIterator<Node> allNodeIterator = operations.getAllNodesWithLabel(label).iterator();
		TraversalDescription traversalDescription = policyTraversalStrategy.getTraversalDescription(graphDb, 1);

		while (allNodeIterator.hasNext()) {
			Node policyNode = allNodeIterator.next();
			PolicyEntityImpl componentStructure = entityComposer
					.composeEntity(traversalDescription.traverse(policyNode).relationships(), policyNode);
			returnList.add(componentStructure);
		}
		return returnList;
	}

	@Override
	public PolicyEntityImpl createPolicyRelationship(PolicyEntityImpl fromEntity, RelationshipImpl relationship,
			PolicyEntityImpl toEntity) {

		validateEntity(fromEntity);
		validateEntity(toEntity);
		Objects.requireNonNull(relationship, NULL_PARAMETER_MSG);

		PolicyEntityImpl returnPolicyEntity = null;
		
		try (Transaction tx = graphDb.beginTx()) {
			Node node =_createPolicyRelationshipWithinTransaction(fromEntity, relationship, toEntity);
			returnPolicyEntity = _readPolicyComponentWithinTransaction(node);
			tx.success();
		} catch (NotFoundException e) {
			// Convert to standard Application exception for consistency
			throw new ApplicationPersistenceException(e.getMessage());
		}

		return returnPolicyEntity;
	}

	private Node _createPolicyRelationshipWithinTransaction(PolicyEntityImpl fromEntity, RelationshipImpl relationship,
			PolicyEntityImpl toEntity) {
		
		Node fromNode = graphDb.getNodeById(fromEntity.getEntityId());
		Node toNode = graphDb.getNodeById(toEntity.getEntityId());
		
		DynamicRelationshipType type = DynamicRelationshipType.withName(relationship.getRelationshipType().name());
		org.neo4j.graphdb.Relationship relationshipNode = fromNode.createRelationshipTo(toNode, type);
		for (Entry<String, Object> entry : EntityImplUtilities.getProperties(relationship).entrySet()) {
			if (entry.getValue() != null) {
				relationshipNode.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return fromNode;
	}

	@Override
	public void deletePolicyRelationship( RelationshipImpl relationship) {

		validateRelationship(relationship);
		try (Transaction tx = graphDb.beginTx()) {
			_deletePolicyRelationshipWithinTransaction(relationship);
			tx.success();
		} catch (NotFoundException e) {
			// Convert to standard Application exception for consistency
			throw new ApplicationPersistenceException(e.getMessage());
		}
	}

	private void _deletePolicyRelationshipWithinTransaction(RelationshipImpl relationship) {
		
		graphDb.getRelationshipById(relationship.getRelationshipId()).delete();		
	}
	
	@Override
	public RelationshipImpl updatePolicyRelationship(RelationshipImpl relationship) {
		
		validateRelationship(relationship);
		
		RelationshipImpl returnRelationship = null;
		try (Transaction tx = graphDb.beginTx()) {
			org.neo4j.graphdb.Relationship targetRelationship = _updatePolicyRelationshipWithinTransaction(relationship);
			returnRelationship = entityComposer.composeRelationship(targetRelationship);
			tx.success();
		} catch (NotFoundException e) {
			// Convert to standard Application exception for consistency
			throw new ApplicationPersistenceException(e.getMessage());
		}
		return returnRelationship;
	}

	private org.neo4j.graphdb.Relationship _updatePolicyRelationshipWithinTransaction(RelationshipImpl relationship) {
		
		org.neo4j.graphdb.Relationship targetRelationship = graphDb.getRelationshipById(relationship.getRelationshipId());
		
		for (String key : targetRelationship.getPropertyKeys()) {
			targetRelationship.removeProperty(key);
		}
		
		for (Entry<String, Object> entry : EntityImplUtilities.getProperties(relationship).entrySet()) {
			if (entry.getValue() != null) {
				targetRelationship.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return targetRelationship;
	}
	
	private void validateEntity(PolicyEntityImpl entity) {

		Objects.requireNonNull(entity, NULL_PARAMETER_MSG);
		if (entity.getEntityId() == null) {
			throw new ApplicationPersistenceException(UNKNOWN_ID_MSG);
		}
	}


	private void validateRelationship(Relationship<?> relationship) {

		Objects.requireNonNull(relationship, NULL_PARAMETER_MSG);
		if (relationship.getRelationshipId() == null) {
			throw new ApplicationPersistenceException(UNKNOWN_ID_MSG);
		}
	}


}
