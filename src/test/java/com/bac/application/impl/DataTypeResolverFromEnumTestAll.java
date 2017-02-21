package com.bac.application.impl;

import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;

import com.bac.application.ApplicationDataType;

public class DataTypeResolverFromEnumTestAll {

	private DataTypeResolverFromEnum instance;

	@Before
	public void setUp() throws Exception {

		instance = new DataTypeResolverFromEnum();
	}

	@Test
	public void empty_Resolver_Should_Return_Null_For_Known_DataType() {

		assertNull(instance.getDataType(DataTypesSet1.ADDRESS.name()));
	}

	@Test
	public void populated_Resolver_Should_Return_Null_For_Empty_String() {

		instance.include(DataTypesSet1.class);
		assertNull(instance.getDataType(""));
	}

	@Test
	public void populated_Resolver_Should_Return_Null_For_Null_String() {

		instance.include(DataTypesSet1.class);
		assertNull(instance.getDataType(null));
	}

	@Test(expected = NullPointerException.class)
	public void populating_Resolver_With_Null_Should_Throw_Exception() {

		instance.include(null);
	}

	@Test
	public void populated_Resolver_Should_Return_A_DataType_For_Each_Element() {

		instance.include(DataTypesSet1.class);
		EnumSet.allOf(DataTypesSet1.class).stream().forEach(e -> assertEquals(e, instance.getDataType(e.name())));
	}

	@Test
	public void populated_Resolver_Should_Return_A_DataType_For_Each_Element_For_All_Sets() {

		instance.include(DataTypesSet1.class);
		instance.include(DataTypesSet2.class);
		EnumSet.allOf(DataTypesSet1.class).stream().forEach(e -> assertEquals(e, instance.getDataType(e.name())));
		EnumSet.allOf(DataTypesSet2.class).stream().forEach(e -> assertEquals(e, instance.getDataType(e.name())));
	}

	@Test
	public void populated_Resolver_Should_Return_A_DataType_For_Each_Element_When_Set_Repeatedly_Included() {

		instance.include(DataTypesSet1.class);
		instance.include(DataTypesSet1.class);
		EnumSet.allOf(DataTypesSet1.class).stream().forEach(e -> assertEquals(e, instance.getDataType(e.name())));
	}

	enum DataTypesSet1 implements ApplicationDataType {

		PERSON, ADDRESS, PHONE_NUMBER, EMAIL;
	}

	enum DataTypesSet2 implements ApplicationDataType {

		IMAGE, TEXT, DOCUMENT, SECTION;
	}

}
