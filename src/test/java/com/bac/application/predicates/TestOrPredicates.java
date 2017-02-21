package com.bac.application.predicates;

import static com.bac.application.predicate.Where.or;
import static com.bac.application.predicate.Where.where;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestOrPredicates extends AbstractTestPredicate {

	@Test
	public void true_Context_Or_True_Predicate_Should_Return_True() {

		assertTrue(where(trueContext, or(trueContext)).apply(instance));
	}

	@Test
	public void true_Context_Or_False_Predicate_Should_Return_True() {

		assertTrue(where(trueContext, or(falseContext)).apply(instance));
	}

	@Test
	public void false_Context_Or_True_Predicate_Should_Return_True() {

		assertTrue(where(falseContext, or(trueContext)).apply(instance));
	}

	@Test
	public void false_Context_Or_False_Predicate_Should_Return_False() {

		assertFalse(where(falseContext, or(falseContext)).apply(instance));
	}

	@Test
	public void single_True_Context_With_All_False_OrPredicates_Should_Return_True() {

		assertTrue(where(trueContext, or(falseContext), or(falseContext), or(falseContext), or(falseContext))
				.apply(instance));
	}

	@Test
	public void single_True_OrPredicate_Should_Return_True() {

		assertTrue(where(falseContext, or(falseContext), or(falseContext), or(trueContext), or(falseContext))
				.apply(instance));
	}

	@Test
	public void false_Context_And_Nested_False_OrPredicates_Should_Return_False() {

		assertFalse(where(falseContext, or(falseContext, or(falseContext))).apply(instance));
	}

	@Test
	public void false_Context_And_Multiple_Nested_False_OrPredicates_Should_Return_False() {

		assertFalse(where(falseContext, or(falseContext, or(falseContext, or(falseContext)))).apply(instance));
	}

	@Test
	public void multiple_Nested_False_OrPredicates_Containing_Some_True_Context_Should_Return_True() {

		assertTrue(where(falseContext, or(falseContext, or(trueContext, or(falseContext)))).apply(instance));
	}

	@Test
	public void _multiple_Nested_False_OrPredicates_Containing_Some_True_Context_Should_Return_True() {

		// assertTrue(where(falseContext, where(falseContext)).apply(instance));
	}



}
