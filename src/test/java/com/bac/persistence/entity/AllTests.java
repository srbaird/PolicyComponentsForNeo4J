package com.bac.persistence.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		PersistenceDAOTestEntityCreate.class, 
		PersistenceDAOTestEntityDelete.class, 
		PersistenceDAOTestEntityRead.class,
		PersistenceDAOTestRelationshipCreate.class, 
		PersistenceDAOTestRelationshipDelete.class,
		PersistenceDAOTestRelationshipUpdate.class, 
		PersistenceDAOTestEntityUpdate.class,
		PolicyEntityBeanFactoryTestAll.class, 
		PolicyEntityRelationshipBeanFactoryTestAll.class,
		EntityComposerTestApplyContext.class
})
public class AllTests {

}
