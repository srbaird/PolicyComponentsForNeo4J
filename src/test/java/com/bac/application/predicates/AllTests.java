package com.bac.application.predicates;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestAndPredicates.class, 
	TestMixedPredicates.class, 
	TestOrPredicates.class , 
	TestComplexPredicates.class 
})
public class AllTests {

}
