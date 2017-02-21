package com.bac.application.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;

import com.bac.application.ApplicationRelationshipType;

public class RelationshipTypeResolverFromEnumTestAll {

	private RelationshipTypeResolverFromEnum instance;

	@Before
	public void setUp() throws Exception {

		instance = new RelationshipTypeResolverFromEnum();
	}

	@Test
	public void empty_Resolver_Should_Return_Null_For_Known_RelationshipType() {

		assertNull(instance.getRelationshipType(RelationshipTypesSet1.COMPOSITION.name()));
	}

	@Test
	public void populated_Resolver_Should_Return_Null_For_Empty_String() {

		instance.include(RelationshipTypesSet1.class);
		assertNull(instance.getRelationshipType(""));
	}

	@Test
	public void populated_Resolver_Should_Return_Null_For_Null_String() {

		instance.include(RelationshipTypesSet1.class);
		assertNull(instance.getRelationshipType(null));
	}

	@Test(expected = NullPointerException.class)
	public void populating_Resolver_With_Null_Should_Throw_Exception() {

		instance.include(null);
	}

	@Test
	public void populated_Resolver_Should_Return_A_RelationshipType_For_Each_Element() {

		instance.include(RelationshipTypesSet1.class);
		EnumSet.allOf(RelationshipTypesSet1.class).stream()
				.forEach(e -> assertEquals(e, instance.getRelationshipType(e.name())));
	}

	@Test
	public void populated_Resolver_Should_Return_A_RelationshipType_For_Each_Element_For_All_Sets() {

		instance.include(RelationshipTypesSet1.class);
		instance.include(RelationshipTypesSet2.class);
		EnumSet.allOf(RelationshipTypesSet1.class).stream()
				.forEach(e -> assertEquals(e, instance.getRelationshipType(e.name())));
		EnumSet.allOf(RelationshipTypesSet2.class).stream()
				.forEach(e -> assertEquals(e, instance.getRelationshipType(e.name())));
	}

	@Test
	public void populated_Resolver_Should_Return_A_RelationshipType_For_Each_Element_When_Set_Repeatedly_Included() {

		instance.include(RelationshipTypesSet1.class);
		instance.include(RelationshipTypesSet1.class);
		EnumSet.allOf(RelationshipTypesSet1.class).stream()
				.forEach(e -> assertEquals(e, instance.getRelationshipType(e.name())));
	}

	@Test
	public void empty_Resolver_Should_Return_Empty_Set_For_allRelationshipTypes() {

		assertTrue(instance.allRelationshipTypes().isEmpty());
	}

	@Test
	public void populated_Resolver_Should_Return_All_Types() {

		instance.include(RelationshipTypesSet1.class);		
		assertEquals(EnumSet.allOf(RelationshipTypesSet1.class), instance.allRelationshipTypes());
	}
	
	@Test
	public void populated_Resolver_Should_Not_Return_Duplicates_When_Populated_Repeatedly_From_The_Same_Set() {

		instance.include(RelationshipTypesSet1.class);		
		instance.include(RelationshipTypesSet1.class);		
		instance.include(RelationshipTypesSet1.class);		
		instance.include(RelationshipTypesSet1.class);		
		assertEquals(EnumSet.allOf(RelationshipTypesSet1.class), instance.allRelationshipTypes());
	}


	@Test
	public void populated_Resolver_Should_Return_Types_From_All_Sets() {

		instance.include(RelationshipTypesSet1.class);		
		instance.include(RelationshipTypesSet2.class);		
		assertTrue(instance.allRelationshipTypes().containsAll(EnumSet.allOf(RelationshipTypesSet1.class)));
		assertTrue(instance.allRelationshipTypes().containsAll(EnumSet.allOf(RelationshipTypesSet2.class)));
	}
	
	enum RelationshipTypesSet1 implements ApplicationRelationshipType {

		COMPOSITION, AGGREGATION, ASSIGNMENT, LINK;
	}

	enum RelationshipTypesSet2 implements ApplicationRelationshipType {

		KNOWS, LIKES, HATES, LOVES;
	}

}
