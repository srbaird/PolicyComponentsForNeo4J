package com.bac.application.predicates;

import static com.bac.application.predicate.Where.and;
import static com.bac.application.predicate.Where.or;
import static com.bac.application.predicate.Where.where;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestMixedPredicates extends AbstractTestPredicate {

	@Test
	public void true_Context_With_AndPredicate_Containing_OrPredicate_Should_Return_True() {

		assertTrue(where(trueContext, and(falseContext, or(trueContext))).apply(instance));
	}

	@Test
	public void false_Context_With_OrPredicate_Containing_AndPredicate_Should_Return_True() {

		assertTrue(where(falseContext, or(trueContext, and(trueContext))).apply(instance));
	}

	@Test
	public void false_Context_Or_True_Predicate_Should_Return_True() {

		assertTrue(where(falseContext, or(trueContext)).apply(instance));
	}

	@Test
	public void true_Context_With_AndPredicate_Containing_False_OrPredicate_Should_Return_True() {

		assertFalse(where(trueContext, and(falseContext, or(falseContext))).apply(instance));
	}

	@Test
	public void true_Context_With_Multiple_AndPredicates_Containing_Valid_OrPredicates_Should_Return_True() {

		assertTrue(where(trueContext, and(trueContext, or(falseContext)), and(falseContext, or(trueContext)))
				.apply(instance));
	}

	@Test
	public void true_Context_With_Multiple_AndPredicates_Containing_Valid_And_Invalid_Predicates_Should_Return_False() {

		assertFalse(where(trueContext, and(trueContext, or(falseContext)), and(falseContext, or(falseContext)))
				.apply(instance));
	}

	@Test
	public void true_Context_With_Multiple_OrPredicates_Should_Return_True() {

		assertTrue(where(falseContext, or(falseContext, and(trueContext)), or(falseContext), or(trueContext))
				.apply(instance));
	}
}
