package com.bac.application.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		DataTypeResolverFromEnumTestAll.class, 
		DataTypeResolverFromEnumTestAllSpring.class,
		RelationshipTypeResolverFromEnumTestAll.class, 
		RelationshipTypeResolverFromEnumTestSpring.class 
})
public class AllTests {

}
