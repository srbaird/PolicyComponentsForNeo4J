package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationRelationshipTypes.ASSIGNEE_OF;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.bac.application.ApplicationPersistenceException;
import com.bac.application.PolicyEntity;
import com.bac.components.EntityRelationship;
/**
 * Policy entities have only one mutable attribute so testing is limited
 * 
 * @author Simon Baird
 *
 */
public class PersistenceDAOTestRelationshipDelete extends AbstractEntityTest {

	@Test(expected = NullPointerException.class)
	public void deleting_A_Null_Relationship_Should_Throw_An_Exception() {

		
		dao.deletePolicyRelationship(null);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void deleting_An_Relationship_With_No_Id_Should_Throw_Exception() {

		dao.deletePolicyRelationship(dao.newRelationship(ASSIGNEE_OF));
	}
	
	@Test(expected = ApplicationPersistenceException.class)
	public void deleting_An_Relationship_With_Unknown_Id_Should_Throw_Exception() {

		RelationshipImpl mockRelationship = mock(RelationshipImpl.class);
		when(mockRelationship.getRelationshipId()).thenReturn(1L);
		dao.deletePolicyRelationship(mockRelationship);
	}
	
	@Test
	public void deletins_A_Relationship_Should_Remove_It_From_The_Enttiy() {

		final PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		final PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		//
		final EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		final DefaultRelationshipImpl targetRelationship = resultEntityRelationship.getRelationship();

		dao.deletePolicyRelationship(targetRelationship);
		
		final PolicyEntityImpl updatedFromEntity = dao.readPolicyEntity(fromEntity.getEntityId());
		assertTrue(updatedFromEntity.getEntities().isEmpty());
	}

}
