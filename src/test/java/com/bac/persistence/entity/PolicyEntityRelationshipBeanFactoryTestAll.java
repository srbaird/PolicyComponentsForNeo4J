package com.bac.persistence.entity;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;

import com.bac.application.ApplicationRelationshipType;
import com.bac.application.PolicyEntity;
import com.bac.components.EntityRelationship;
import com.bac.components.Relationship;

public class PolicyEntityRelationshipBeanFactoryTestAll extends AbstractSpringTest {

	@Resource(name = "policyEntityRelationshipFactory")
	PolicyEntityRelationshipBeanFactory instance;

	@Test
	public void new_Entity_Relationship_Should_Not_Be_Null() {

		EntityRelationship<PolicyEntity, Relationship<ApplicationRelationshipType>> o = instance.newEntityRelationship();
		assertNotNull(o);
		assertTrue(o instanceof PolicyEntityRelationship);
		assertNull(o.getRelationship());
		assertNull(o.getEntity());
	}

}
