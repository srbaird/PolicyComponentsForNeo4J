package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationDataTypes.DATE;
import static com.bac.application.impl.ApplicationDataTypes.POLICY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bac.application.ApplicationDataType;

public class PersistenceDAOTestEntityCreate extends AbstractEntityTest {


	
	@Test
	public void created_Policy_Should_Have_An_Id() {

		final PolicyEntityImpl createdEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertNotNull(createdEntity);
	}

	@Test
	public void created_Policy_Should_Have_An_POLICY_Data_Type() {

		final ApplicationDataType expectedDataType = POLICY;
		final PolicyEntityImpl createdEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertEquals(expectedDataType, createdEntity.getDataType());
	}

	@Test
	public void created_Policy_Should_Have_An_Null_Policy_Data_Type() {

		final PolicyEntityImpl createdEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertNull(createdEntity.getPolicyDataType());
	}

	@Test
	public void created_Policy_Should_Have_No_Relationships() {

		final PolicyEntityImpl createdEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		assertTrue(createdEntity.getEntities().isEmpty());
	}

	@Test
	public void created_Policy_Should_Be_Able_To_Be_Read_Back() {

		Long createdId;
		final ApplicationDataType expectedDataType = DATE;

		{
			final PolicyEntityImpl policyEntity = dao.newPolicyEntity();
			policyEntity.setPolicyDataType(expectedDataType);
			createdId = dao.createPolicyEntity(policyEntity).getEntityId();
		}

		final PolicyEntityImpl createdEntity = dao.readPolicyEntity(createdId);

		assertNotNull(createdEntity);
		assertEquals(expectedDataType, createdEntity.getPolicyDataType());
	}

	@Test
	public void created_Policy_Should_Appear_In_Policy_List() {

		final PolicyEntityImpl createdEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final int SINGLE_POLICY = 1;
		assertEquals(SINGLE_POLICY, dao.listPolicies().size());
		assertTrue(dao.listPolicies().contains(createdEntity));
	}
	
	@Test
	public void multiple_Created_Policies_Should_Appear_In_Policy_List() {

		final int LIMIT = 3;
		final List<PolicyEntityImpl> createdPolicies = new ArrayList<>();
		for (int i = 0; i < LIMIT; i++) {
			createdPolicies.add(dao.createPolicyEntity(dao.newPolicyEntity()));
		}

		assertEquals(LIMIT, dao.listPolicies().size());
		assertTrue(dao.listPolicies().containsAll(createdPolicies));
	}
}
