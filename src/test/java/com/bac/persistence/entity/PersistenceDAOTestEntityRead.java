package com.bac.persistence.entity;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PersistenceDAOTestEntityRead extends AbstractEntityTest {

	@Test
	public void read_From_Empty_Database_Should_Always_Return_Null() {

		assertNull(dao.readPolicyEntity(1L));
	}

	@Test
	public void listing_Policies_From_Empty_Database_Should_Return_An_Empty_List() {

		assertTrue(dao.listPolicies().isEmpty());
	}

}
