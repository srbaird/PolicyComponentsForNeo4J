package com.bac.application.predicates;

import static com.bac.application.predicate.Where.and;
import static com.bac.application.predicate.Where.or;
import static com.bac.application.predicate.Where.where;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestComplexPredicates extends AbstractTestPredicate {

	/**
	 * Testing A AND B OR C groupings gives different results depending on
	 * parentheses.
	 * 
	 * (A AND B) OR C is equivalent to C OR (A AND B) and is true if C is true
	 * or A & B are true. 
	 * 
	 * A AND (B OR C) is true if A & B are true or A & C are true
	 */
	@Test
	public void test_For_A_and_B_or_C_Groupings() {

		//
		//	C OR (A AND B)
		//
		// C = true, A & B = false
		assertTrue(where(trueContext, or(falseContext, and(falseContext))).apply(instance));
		
		// C = true, A & B = true
		assertTrue(where(trueContext, or(trueContext, and(trueContext))).apply(instance));
		
		// C = true, A = true, B = false
		assertTrue(where(trueContext, or(trueContext, and(falseContext))).apply(instance));
		
		// C = true, A = false, B = true
		assertTrue(where(trueContext, or(falseContext, and(trueContext))).apply(instance));
		
		// C = false, A & B = true
		assertTrue(where(falseContext, or(trueContext, and(trueContext))).apply(instance));
		
		// C = false, A = false,  B = true
		assertFalse(where(falseContext, or(falseContext, and(trueContext))).apply(instance));
		
		// C = false, A = true,  B = false
		assertFalse(where(falseContext, or(trueContext, and(falseContext))).apply(instance));
		
		// C = false, A = false,  B = false
		assertFalse(where(falseContext, or(falseContext, and(falseContext))).apply(instance));
		
		//
		//	A AND (B OR C)
		//
		// A = true, B = true, C = true
		assertTrue(where(trueContext, and(trueContext, or(trueContext))).apply(instance));

		// A = true, B = true, C = false
		assertTrue(where(trueContext, and(trueContext, or(falseContext))).apply(instance));

		// A = true, B = false, C = true
		assertTrue(where(trueContext, and(falseContext, or(trueContext))).apply(instance));
		
		// A = true, B = false, C = false
		assertFalse(where(trueContext, and(falseContext, or(falseContext))).apply(instance));

		// A = false, B = true, C = true
		assertFalse(where(falseContext, and(trueContext, or(trueContext))).apply(instance));

		// A = false, B = false, C = true
		assertFalse(where(falseContext, and(falseContext, or(trueContext))).apply(instance));

		// A = false, B = true, C = false
		assertFalse(where(falseContext, and(trueContext, or(falseContext))).apply(instance));

		// A = false, B = false, C = false
		assertFalse(where(falseContext, and(falseContext, or(falseContext))).apply(instance));

	}
}
