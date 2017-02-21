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
public class PersistenceDAOTestRelationshipUpdate extends AbstractEntityTest {

	@Test(expected = NullPointerException.class)
	public void updating_A_Null_Relationship_Should_Throw_An_Exception() {

		
		dao.updatePolicyRelationship(null);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void updating_An_Relationship_With_No_Id_Should_Throw_Exception() {

		dao.updatePolicyRelationship(dao.newRelationship(ASSIGNEE_OF));
	}
	
	@Test(expected = ApplicationPersistenceException.class)
	public void updating_An_Relationship_With_Unknown_Id_Should_Throw_Exception() {

		RelationshipImpl mockRelationship = mock(RelationshipImpl.class);
		when(mockRelationship.getRelationshipId()).thenReturn(1L);
		dao.updatePolicyRelationship(mockRelationship);
	}
	
	@Test
	public void updated_Relationship_Should_Reflect_Changes() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		//
		EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		DefaultRelationshipImpl targetRelationship = resultEntityRelationship.getRelationship();

		String newDescription = "New relationship description";
		targetRelationship.setDescription(newDescription);
		
		DefaultRelationshipImpl updatedRelationship = (DefaultRelationshipImpl) dao.updatePolicyRelationship(targetRelationship);
		assertEquals(newDescription, updatedRelationship.getDescription());
	}
	
	@Test
	public void updated_Relationship_Attribute_Can_Be_Removed() {

		String description = "Relationship description";
		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		DefaultRelationshipImpl relationship = (DefaultRelationshipImpl) dao.newRelationship(ASSIGNEE_OF);
		relationship.setDescription(description);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		//
		EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		DefaultRelationshipImpl createdRelationship = resultEntityRelationship.getRelationship();
		assertEquals(description, createdRelationship.getDescription());

		String newDescription = null;
		createdRelationship.setDescription(newDescription);
		
		DefaultRelationshipImpl updatedRelationship = (DefaultRelationshipImpl) dao.updatePolicyRelationship(createdRelationship);
		assertEquals(newDescription, updatedRelationship.getDescription());
	}
	
	@Test
	public void updated_Relationship_Should_Be_Reflected_When_From_Entity_Is_Read() {

		final PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		final PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		//
		final EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		final DefaultRelationshipImpl targetRelationship = resultEntityRelationship.getRelationship();

		String newDescription = "New relationship description";
		targetRelationship.setDescription(newDescription);
		dao.updatePolicyRelationship(targetRelationship);
		
		final PolicyEntityImpl updatedFromEntity = dao.readPolicyEntity(fromEntity.getEntityId());
		final EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  updatedEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) updatedFromEntity.getEntities().get(0);

		assertEquals(newDescription, updatedEntityRelationship.getRelationship().getDescription());
	}
	
}
