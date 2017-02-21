package com.bac.application.impl;

import static com.bac.application.impl.ApplicationRelationshipTypes.ASSIGNEE_OF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.EnumSet;

import javax.annotation.Resource;

import org.junit.Test;

import com.bac.application.ApplicationRelationshipType;

public class RelationshipTypeResolverFromEnumTestSpring extends AbstractSpringTest{

	@Resource(name = "relationshipTypeResolverFromEnum")
	private RelationshipTypeResolverFromEnum instance;

	@Test
	public void resolver_Should_Return_A_RelationshipType_For_A_Known_Type() {

		final ApplicationRelationshipType expectedType = ASSIGNEE_OF;
		assertEquals(expectedType, instance.getRelationshipType(expectedType.name()));
	}

	@Test
	public void resolver_Should_Return_Null_For_An_Unknown_Type() {

		assertNull(instance.getRelationshipType("Random string"));
	}

	@Test
	public void populated_Resolver_Should_Return_A_RelationshipType_For_Each_Element() {

		EnumSet.allOf(ApplicationRelationshipTypes.class).stream()
				.forEach(e -> assertEquals(e, instance.getRelationshipType(e.name())));
	}

	@Test
	public void populated_Resolver_Should_Return_All_Types() {

		instance.include(ApplicationRelationshipTypes.class);
		assertEquals(EnumSet.allOf(ApplicationRelationshipTypes.class), instance.allRelationshipTypes());
	}

}
