package com.bac.persistence.accessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.bac.application.impl.ApplicationDataTypes;
import com.bac.components.DataType;
import com.bac.persistence.entity.PolicyEntityImpl;
import com.bac.persistence.entity.PolicyEntityRelationship;

public class PersistenceDAOTestFactoryMethods extends AbstractEntityTest {

	@Test
	public void new_Policy_Entity_Should_Create_A_PolicyEntityImpl () {
		
		assertTrue(instance.newPolicyEntity() instanceof PolicyEntityImpl);
	}

	@Test
	public void new_Policy_Entity_Should_HAve_A_POLICY_Datatype () {
		
		final DataType expectedDataType = ApplicationDataTypes.POLICY;
		final PolicyEntityImpl result = instance.newPolicyEntity();
		
		assertEquals(expectedDataType, result.getDataType());
	}
	
	@Test
	public void new_Policy_Entity_Should_Have_A_null_PolicyDatatype () {
		
		final PolicyEntityImpl result = instance.newPolicyEntity();		
		assertNull(result.getPolicyDataType());
	}
	
	@Test
	public void new_Policy_Entity_Should_Have_No_Assignments () {
		
		final PolicyEntityImpl result = instance.newPolicyEntity();		
		assertTrue(result.getEntities().isEmpty());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void new_Policy_Entity_Should_Have_Immutable_Assignments_List () {
		
		final PolicyEntityImpl result = instance.newPolicyEntity();	
		result.getEntities().add(mock(PolicyEntityRelationship.class));
	}
}
