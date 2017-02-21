package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationDataTypes.DATE;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationPersistenceException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Policy entities have only one mutable attribute so testing is limited
 * 
 * @author Simon Baird
 *
 */
public class PersistenceDAOTestEntityUpdate extends AbstractEntityTest {

	@Test(expected = NullPointerException.class)
	public void updating_A_Null_Entity_Should_Throw_An_Exception() {

		dao.updatePolicyEntity(null);
	}

	@Test(expected = ApplicationPersistenceException.class)
	public void updating_An_Entity_With_No_Id_Should_Throw_Exception() {

		dao.updatePolicyEntity(dao.newPolicyEntity());
	}
	
	@Test(expected = ApplicationPersistenceException.class)
	public void updating_An_Entity_With_Unknown_Id_Should_Throw_Exception() {

		PolicyEntityImpl mockEntity = mock(PolicyEntityImpl.class);
		when(mockEntity.getEntityId()).thenReturn(1L);
		dao.updatePolicyEntity(mockEntity);
	}


	@Test
	public void updating_Should_Return_Updated_Entity() {

		final PolicyEntityImpl policyEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertNotNull(dao.updatePolicyEntity(policyEntity));
	}

	@Test
	public void updating_A_Policy_Should_Not_Affect_The_Immutable_Attributes() {

		final PolicyEntityImpl createdPolicyEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		createdPolicyEntity.setPolicyDataType(DATE);
		final PolicyEntityImpl updatedPolicyEntity = dao.updatePolicyEntity(createdPolicyEntity);
		assertEquals(createdPolicyEntity.getEntityId(), updatedPolicyEntity.getEntityId());
		assertEquals(createdPolicyEntity.getDataType(), updatedPolicyEntity.getDataType());
		assertEquals(createdPolicyEntity.getPolicyDataType(), updatedPolicyEntity.getPolicyDataType());
	}

	@Test
	public void updating_An_Entity_Should_Persist_Changes() {

		Long createdId;
		final ApplicationDataType expectedDataType = DATE;

		{
			PolicyEntityImpl policyEntity = dao.newPolicyEntity();
			policyEntity = dao.createPolicyEntity(policyEntity);
			createdId = policyEntity.getEntityId();
			assertNull(policyEntity.getPolicyDataType());
			policyEntity.setPolicyDataType(expectedDataType);
			dao.updatePolicyEntity(policyEntity);
		}

		// When read back the updates should have been persisted
		final PolicyEntityImpl updatedEntity = dao.readPolicyEntity(createdId);
		assertEquals(expectedDataType, updatedEntity.getPolicyDataType());

	}
}
