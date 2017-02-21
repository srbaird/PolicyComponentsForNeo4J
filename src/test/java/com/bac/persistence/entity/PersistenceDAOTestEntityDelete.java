package com.bac.persistence.entity;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.bac.application.ApplicationPersistenceException;

/**
 * Deleting a Policy entity should also remove all its Relationships but should
 * not cascade the delete
 * 
 * @author Simon Baird
 *
 */
public class PersistenceDAOTestEntityDelete extends AbstractEntityTest {

	/**
	 * Attempting to delete a null entity should throw and Exception
	 */
	@Test(expected = NullPointerException.class)
	public void deleting_A_Null_Entity_Should_Throw_An_Exception() {

		dao.deletePolicyEntity(null);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void deleting_An_Entity_With_No_Id_Should_Throw_Exception() {

		dao.deletePolicyEntity(dao.newPolicyEntity());
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void deleting_An_Entity_With_Unknown_Id_Should_Throw_Exception() {

		PolicyEntityImpl mockEntity = mock(PolicyEntityImpl.class);
		when(mockEntity.getEntityId()).thenReturn(1L);
		dao.deletePolicyEntity(mockEntity);
	}

	@Test
	public void a_Deleted_Entity_Should_Not_Be_Readable() {

		Long createdId;

		{
			PolicyEntityImpl policyEntity = dao.newPolicyEntity();
			policyEntity = dao.createPolicyEntity(policyEntity);
			createdId = policyEntity.getEntityId();
			dao.deletePolicyEntity(policyEntity);
		}
		// The deleted entity should no longer exist
		assertNull(dao.readPolicyEntity(createdId));
	}

	@Test
	public void a_Deleted_Entity_Should_Not_Appear_In_Policies_List() {

		{
			PolicyEntityImpl policyEntity = dao.newPolicyEntity();
			policyEntity = dao.createPolicyEntity(policyEntity);
			dao.deletePolicyEntity(policyEntity);
		}
		// The deleted entity should no longer exist
		assertTrue(dao.listPolicies().isEmpty());
	}

}
