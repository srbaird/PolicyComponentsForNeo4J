
package com.bac.persistence.accessor;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bac.persistence.accessor.GraphEntityDAOImpl;

/**
 * Shared test resources
 *
 * @author Simon Baird
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public abstract class AbstractEntityTest {

	//
	@Autowired
	ApplicationContext appContext;
	//
	// The class under test
	//
	@Resource(name = "persistenceDAO")
	GraphEntityDAOImpl instance;

	Calendar calendar;
	//
	// logger
	static Logger logger = LoggerFactory.getLogger(AbstractEntityTest.class);

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() throws IOException {

	}

	//
	// Set of dates for Context testing
	//
	Date getYesterday() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date getToday() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date getTomorrow() {

		calendar.setTime(new Date(System.currentTimeMillis()));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return dateOnly(new Date(calendar.getTimeInMillis()));
	}

	Date dateOnly(Date date) {
		//
		// Strip the time component from the date
		//
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // Remove the time component ...
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTimeInMillis());
	}

	@Test
	public void application_Context_Should_Not_Be_Null() {

		assertNotNull(appContext);
		assertNotNull(instance);
	}

}
