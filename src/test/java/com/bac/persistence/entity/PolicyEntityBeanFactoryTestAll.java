package com.bac.persistence.entity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bac.application.impl.ApplicationDataTypes;
import com.bac.components.DataType;

public class PolicyEntityBeanFactoryTestAll extends AbstractEntityTest {

	@Autowired
	PolicyEntityBeanFactory instance;

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
