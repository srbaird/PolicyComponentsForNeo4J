package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationDataTypes.IMAGE;
import static com.bac.application.impl.ApplicationDataTypes.TAG;
import static com.bac.application.impl.ApplicationDataTypes.TEXT;
import static com.bac.application.impl.ApplicationRelationshipTypes.ASSIGNEE_OF;
import static com.bac.application.impl.ApplicationRelationshipTypes.LINKS_TO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Simon Baird
 *
 */
public class PersistenceDAOTestRelationshipComplex extends AbstractEntityTest {

	@Test
	public void creating_Relationships_Should_Result_In_A_List_Of_Depth_One() {

		PolicyEntityImpl tagEntity = dao.newPolicyEntity();
		tagEntity.setPolicyDataType(TAG);
		tagEntity = dao.createPolicyEntity(tagEntity);
		
		PolicyEntityImpl imageEntity = dao.newPolicyEntity();
		imageEntity.setPolicyDataType(IMAGE);
		imageEntity = dao.createPolicyEntity(imageEntity);
		
		PolicyEntityImpl textEntity = dao.newPolicyEntity();
		textEntity.setPolicyDataType(TEXT);
		textEntity = dao.createPolicyEntity(textEntity);
		
		RelationshipImpl linkRelationship = dao.newRelationship(LINKS_TO);
		RelationshipImpl assignRelationship = dao.newRelationship(ASSIGNEE_OF);
		
		// Create {TEXT -> LINK -> IMAGE}
		dao.createPolicyRelationship(textEntity, linkRelationship, imageEntity);
		// Create {IMAGE -> ASSIGN -> TAG}
		dao.createPolicyRelationship(imageEntity, assignRelationship, tagEntity);
		
		// For {IMAGE -> ASSIGN -> TAG} the reverse relationship should not be represented
		PolicyEntityImpl readImageEntity = dao.readPolicyEntity(imageEntity.getEntityId());
		assertTrue(readImageEntity.getEntitiesList().get(0).getEntity().getEntities().isEmpty());
		
		// For {TEXT -> LINK -> IMAGE}, the {IMAGE -> ASSIGN -> TAG} should not be represented
		PolicyEntityImpl readTextEntity = dao.readPolicyEntity(textEntity.getEntityId());
		assertTrue(readTextEntity.getEntitiesList().get(0).getEntity().getEntities().isEmpty());
		
		// No reverse relationship should exist for TAG
		PolicyEntityImpl readTagEntity = dao.readPolicyEntity(tagEntity.getEntityId());
		assertTrue(readTagEntity.getEntitiesList().isEmpty());
	}
	
	@Test
	public void listed_Relationships_Should_Be_Root_Nodes_Only() {

		PolicyEntityImpl tagEntity = dao.newPolicyEntity();
		tagEntity.setPolicyDataType(TAG);
		tagEntity = dao.createPolicyEntity(tagEntity);
		
		PolicyEntityImpl imageEntity = dao.newPolicyEntity();
		imageEntity.setPolicyDataType(IMAGE);
		imageEntity = dao.createPolicyEntity(imageEntity);
		
		PolicyEntityImpl textEntity = dao.newPolicyEntity();
		textEntity.setPolicyDataType(TEXT);
		textEntity = dao.createPolicyEntity(textEntity);
		
		final int THREE_POLICIES = 3;
		assertEquals(THREE_POLICIES,  dao.listPolicies().size() );
		dao.listPolicies().forEach(p -> assertTrue(p.getEntities().isEmpty()));
	}
}
