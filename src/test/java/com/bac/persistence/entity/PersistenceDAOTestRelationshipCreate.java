package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationRelationshipTypes.ASSIGNEE_OF;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.bac.application.ApplicationPersistenceException;
import com.bac.application.ApplicationRelationshipType;
import com.bac.application.PolicyEntity;
import com.bac.components.EntityRelationship;
import com.bac.components.Relationship;
import com.bac.components.RelationshipType;

/**
 * 
 * @author Simon Baird
 *
 */
public class PersistenceDAOTestRelationshipCreate extends AbstractEntityTest {

	@Test(expected = NullPointerException.class)
	public void creating_Relationship_With_Null_From_Entity_Should_Throw_An_Exception() {

		PolicyEntityImpl mockEntity = mock(PolicyEntityImpl.class);
		RelationshipImpl mockRelationship = mock(RelationshipImpl.class);
		dao.createPolicyRelationship(null, mockRelationship, mockEntity);
	}

	@Test(expected = NullPointerException.class)
	public void creating_Relationship_With_Null_Relationship_Should_Throw_An_Exception() {

		PolicyEntityImpl mockEntity = mock(PolicyEntityImpl.class);
		dao.createPolicyRelationship(mockEntity, null, mockEntity);
	}

	@Test(expected = NullPointerException.class)
	public void creating_Relationship_With_Null_To_Entity_Should_Throw_An_Exception() {

		PolicyEntityImpl mockEntity = mock(PolicyEntityImpl.class);
		RelationshipImpl mockRelationship = mock(RelationshipImpl.class);
		dao.createPolicyRelationship(mockEntity, mockRelationship, null);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void creating_Relationship_Where_From_Entity_Has_Null_Id_Should_Throw_An_Exception() {

		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		dao.createPolicyRelationship(dao.newPolicyEntity(), relationship, toEntity);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void creating_Relationship_Where_To_Entity_Has_Null_Id_Should_Throw_An_Exception() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		dao.createPolicyRelationship(fromEntity, relationship, dao.newPolicyEntity());
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void creating_Relationship_Where_From_Entity_Has_Unknown_Id_Should_Throw_An_Exception() {

		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl fromEntity = mock(PolicyEntityImpl.class);
		when(fromEntity.getEntityId()).thenReturn(toEntity.getEntityId() - 1);
		dao.createPolicyRelationship(fromEntity, relationship, toEntity);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void creating_Relationship_Where_To_Entity_Has_Unknown_Id_Should_Throw_An_Exception() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = mock(PolicyEntityImpl.class);
		when(toEntity.getEntityId()).thenReturn(fromEntity.getEntityId() - 1);
		dao.createPolicyRelationship(fromEntity, relationship, toEntity);
	}

	@Test
	public void creating_Relationship_Should_Return_Non_Null_Entity() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertNotNull(dao.createPolicyRelationship(fromEntity, relationship, toEntity));
	}

	@Test
	public void creating_Relationship_Should_Return_The_From_Entity() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertEquals(fromEntity, dao.createPolicyRelationship(fromEntity, relationship, toEntity));
	}

	@Test
	public void from_Entity_Should_Have_A_Relationship_With_To_Entity() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		final int SINGLE_RELATIONSHIP = 1;
		assertEquals(SINGLE_RELATIONSHIP, resultEntity.getEntities().size());
		EntityRelationship<PolicyEntity, ?> resultEntityRelationship = resultEntity.getEntities().get(0);
		assertEquals(toEntity, resultEntityRelationship.getEntity());
	}

	@Test
	public void from_Entity_Should_Have_A_Relationship_Of_The_Correct_Type() {

		ApplicationRelationshipType expectedRelationshipType = ASSIGNEE_OF;
		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(expectedRelationshipType);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		final int SINGLE_RELATIONSHIP = 1;
		assertEquals(SINGLE_RELATIONSHIP, resultEntity.getEntities().size());
		EntityRelationship<PolicyEntity, ? extends Relationship<? extends RelationshipType>> resultEntityRelationship = resultEntity.getEntities().get(0);
		assertEquals(expectedRelationshipType, resultEntityRelationship.getRelationship().getRelationshipType());
	}
	
	@Test
	public void from_Entity_Should_Have_A_Relationship_With_A_Null_Description() {

		ApplicationRelationshipType expectedRelationshipType = ASSIGNEE_OF;
		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(expectedRelationshipType);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		final int SINGLE_RELATIONSHIP = 1;
		assertEquals(SINGLE_RELATIONSHIP, resultEntity.getEntities().size());
		EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		assertNull( resultEntityRelationship.getRelationship().getDescription());
	}

	@Test
	public void from_Entity_Should_Have_A_Relationship_With_A_Populated_Description() {

		final ApplicationRelationshipType expectedRelationshipType = ASSIGNEE_OF;
		final String expectedDescription = "Description of relationship";
		
		final PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final DefaultRelationshipImpl relationship = (DefaultRelationshipImpl) dao.newRelationship(expectedRelationshipType);
		relationship.setDescription(expectedDescription);
		final PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		final int SINGLE_RELATIONSHIP = 1;
		assertEquals(SINGLE_RELATIONSHIP, resultEntity.getEntities().size());
		EntityRelationship<PolicyEntity, DefaultRelationshipImpl>  resultEntityRelationship =  (EntityRelationship<PolicyEntity, DefaultRelationshipImpl>) resultEntity.getEntities().get(0);
		assertEquals(expectedDescription, resultEntityRelationship.getRelationship().getDescription());
	}
	
	@Test
	public void to_Entity_Should_Have_No_Relationships() {

		PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		PolicyEntityImpl resultEntity = dao.createPolicyRelationship(fromEntity, relationship, toEntity);
		EntityRelationship<PolicyEntity, ?> resultEntityRelationship = resultEntity.getEntities().get(0);
		assertTrue(resultEntityRelationship.getEntity().getEntities().isEmpty());
	}
}
