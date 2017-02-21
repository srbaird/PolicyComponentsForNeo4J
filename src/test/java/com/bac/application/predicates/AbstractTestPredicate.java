package com.bac.application.predicates;

import com.bac.components.Context;
import com.bac.components.ContextAware;

public class AbstractTestPredicate {

	protected final ContextAware instance = new ContextAware() {

		@SuppressWarnings("unused")
		boolean accept(FalseContext context) {

			return false;
		}

		@SuppressWarnings("unused")
		boolean accept(TrueContext context) {

			return true;
		}
	};
	
	protected Context trueContext = new TrueContext();
	protected Context falseContext = new FalseContext();

	public AbstractTestPredicate() {
		super();
	}

	/*
	 * Simple definitions of Contexts to return determinitive results
	 */
	final class FalseContext implements Context {
	}

	final class TrueContext implements Context {
	}

}