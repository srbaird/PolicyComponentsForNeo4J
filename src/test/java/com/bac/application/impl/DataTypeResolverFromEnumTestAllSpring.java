package com.bac.application.impl;

import static com.bac.application.impl.ApplicationDataTypes.POLICY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.EnumSet;

import javax.annotation.Resource;

import org.junit.Test;

import com.bac.application.ApplicationDataType;

public class DataTypeResolverFromEnumTestAllSpring extends AbstractSpringTest {

	@Resource(name = "dataTypeResolverFromEnum")
	private DataTypeResolverFromEnum instance;


	@Test
	public void resolver_Should_Return_A_DataType_For_A_Known_Type() {

		final ApplicationDataType expectedType = POLICY;
		assertEquals(expectedType, instance.getDataType(expectedType.name()));
	}

	@Test
	public void resolver_Should_Return_Null_For_An_Unknown_Type() {

		assertNull(instance.getDataType("Random string"));
	}

	@Test
	public void populated_Resolver_Should_Return_A_DataType_For_Each_Element() {

		EnumSet.allOf(ApplicationDataTypes.class).stream()
				.forEach(e -> assertEquals(e, instance.getDataType(e.name())));
	}

}
