
package com.bac.application.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Shared test resources
 *
 * @author Simon Baird
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public abstract class AbstractSpringTest {

	//
	@Autowired
	ApplicationContext appContext;

	@Test
	public void application_Context_Should_Not_Be_Null() {

		assertNotNull(appContext);
	}

}
