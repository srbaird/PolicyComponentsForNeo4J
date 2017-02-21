package com.bac.application.predicates;

import static com.bac.application.predicate.Where.and;
import static com.bac.application.predicate.Where.where;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestAndPredicates extends AbstractTestPredicate {

	@Test
	public void true_Context_And_True_Predicate_Should_Return_True() {

		assertTrue(where(trueContext, and(trueContext)).apply(instance));
	}

	@Test
	public void true_Context_And_False_Predicate_Should_Return_False() {

		assertFalse(where(trueContext, and(falseContext)).apply(instance));
	}

	@Test
	public void false_Context_And_True_Predicate_Should_Return_False() {

		assertFalse(where(falseContext, and(trueContext)).apply(instance));
	}

	@Test
	public void false_Context_And_False_Predicate_Should_Return_False() {

		assertFalse(where(falseContext, and(falseContext)).apply(instance));
	}

	@Test
	public void single_False_Context_With_All_True_AndPredicates_Should_Return_False() {

		assertFalse(where(trueContext, and(trueContext), and(trueContext), and(falseContext), and(trueContext))
				.apply(instance));
	}

	@Test
	public void true_Context_And_Nested_True_AndPredicates_Should_Return_True() {

		assertTrue(where(trueContext, and(trueContext, and(trueContext))).apply(instance));
	}

	@Test
	public void multiple_Nested_True_AndPredicates_Should_Return_True() {

		assertTrue(where(trueContext, and(trueContext, and(trueContext, and(trueContext)))).apply(instance));
	}

	@Test
	public void multiple_Nested_AndPredicates_Containing_Single_False_Context_Should_Return_False() {

		assertFalse(where(trueContext, and(trueContext, and(falseContext, and(trueContext)))).apply(instance));
	}
}
