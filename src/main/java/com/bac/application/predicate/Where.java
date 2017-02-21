package com.bac.application.predicate;

import java.util.Arrays;
import java.util.function.BinaryOperator;

import com.bac.application.impl.LateBindingContextDispatcher;
import com.bac.components.Context;
import com.bac.components.ContextAware;

/**
 * Represents a conditional predicate builder which allows nesting of
 * {@code Context} objects with both 'and' and 'or' logic. The class also
 * functions as the predicate builder and provides static methods to compose a
 * condition.
 * 
 * where(someContext).apply(someContextAwareElement)
 * 
 * This will apply the {@code ContextAware} object to the context and return a
 * boolean indicating whether the object is applicable for the context i.e. if
 * it should be included or not.
 * 
 * Conditions may be grouped and nested
 * 
 * where(someContext, and(someOtherContext)).apply(element)
 * 
 * will return true if the element is applicable to BOTH contexts
 * 
 * where(someContext, or(someOtherContext)).apply(element)
 * 
 * will return true if the element is applicable to EITHER contexts
 * 
 * {@code Context} dispatching is handled through the
 * {@code LateBindingContextDispatcher}. All {@code Entity},
 * {@code Relationship} and {@code EntityRelationships} are {@code ContextAware}
 * by default so may be applied to the predicate.
 * 
 * @author Simon Baird
 *
 */
public class Where {

	final Context context;
	final ConditionalWhere[] predicates;

	/**
	 * Java streams do not supply a reduce aggregate where the accumulator is
	 * not the same class as the stream source.
	 * 
	 * U Collection<T>.stream().reduce(U, (T,U) -> U);
	 * 
	 * However a signature exists by supplying a combiner which is intended for
	 * parallel streams and has no effect on non-parallel streams. For clarity
	 * the following binary operator is declared to make this obvious.
	 */
	final BinaryOperator<Boolean> NO_COMBINER = (acc, c) -> null;

	final LateBindingContextDispatcher dispatcher = new LateBindingContextDispatcher();

	/**
	 * Constructor is private to enforce builder
	 * 
	 * @param context
	 *            any class implementing {@code Context}
	 * @param predicates
	 *            a sequence of {@code ConditionalWhere} predicates
	 */
	private Where(Context context, ConditionalWhere... predicates) {

		this.context = context;
		this.predicates = predicates;
	}

	/**
	 * A grouping consisting of a {@code Context} an optional sequence of
	 * conditional predicates
	 * 
	 * @param context
	 *            any class implementing {@code Context}
	 * @param predicates
	 *            a sequence of {@code ConditionalWhere} predicates
	 * @return a composite Where predicate
	 */
	public static Where where(Context context, ConditionalWhere... predicates) {

		return new Where(context, predicates);
	}

	/**
	 * A conditional grouping where the result of evaluation is the current
	 * evaluation value OR the value of this grouping
	 * 
	 * @param context
	 *            any class implementing {@code Context}
	 * @param predicates
	 *            a sequence of {@code ConditionalWhere} predicates
	 * @return a {@code ConditionalWhere} predicate representing an OR
	 *         comparison
	 * 
	 */
	public static OrWhere or(Context context, ConditionalWhere... predicates) {

		return new OrWhere(context, predicates);
	}

	/**
	 * A conditional grouping where the result of evaluation is the current
	 * evaluation value AND the value of this grouping
	 * 
	 * @param context
	 *            any class implementing {@code Context}
	 * @param predicates
	 *            a sequence of {@code ConditionalWhere} predicates
	 * @return a {@code ConditionalWhere} predicate representing an AND
	 *         comparison
	 * 
	 */
	public static AndWhere and(Context context, ConditionalWhere... predicates) {

		return new AndWhere(context, predicates);
	}

	/**
	 * Evaluates the sequence of {@code Context} and any further predicates
	 * against the supplied {@code ContextAware} instance
	 * 
	 * @param instance
	 *            an instance of a {@code ContextAware} class
	 * @return true if this instance is TRUE for all contexts, false otherwise
	 */
	public boolean apply(ContextAware instance) {


		boolean identity = dispatcher.dispatch(instance, context);
		return predicates.length == 0 ? identity
				: Arrays.stream(predicates).reduce(identity, (acc, p) -> p.apply(instance, acc), NO_COMBINER);
	}

	/**
	 * Interface to restrict embedded predicates to conditional types.
	 * 
	 * @author Simon Baird
	 *
	 */
	public interface ConditionalWhere {

		/**
		 * Evaluates the sequence of {@code Context} and any further predicates
		 * against the supplied {@code ContextAware} instance together with an
		 * accumulated boolean status
		 * 
		 * @param instance
		 *            an instance of a {@code ContextAware} class
		 * @param accumulator
		 *            the status of all evaluations in a higher scope to this
		 *            predicate
		 * @return true if this instance is TRUE for all contexts, false
		 *         otherwise
		 */
		boolean apply(ContextAware instance, boolean accumulator);
	}

	/**
	 * Specialisation of the {@code Where} predicate which combines the higher
	 * scope evaluations with an OR operator
	 * 
	 * @author Simon Baird
	 *
	 */
	static public class OrWhere extends Where implements ConditionalWhere {

		private OrWhere(Context context, ConditionalWhere... predicates) {

			super(context, predicates);
		}

		public boolean apply(ContextAware instance, boolean accumulator) {

			boolean identity = dispatcher.dispatch(instance, context);

			return predicates.length == 0 ? accumulator || identity
					: accumulator || Arrays.stream(predicates).reduce(identity, (acc, p) -> p.apply(instance, acc),
							NO_COMBINER);
		}
	}

	/**
	 * Specialisation of the {@code Where} predicate which combines the higher
	 * scope evaluations with an AND operator
	 * 
	 * @author Simon Baird
	 *
	 */
	static public class AndWhere extends Where implements ConditionalWhere {

		private AndWhere(Context context, ConditionalWhere... predicates) {

			super(context, predicates);
		}

		public boolean apply(ContextAware instance, boolean accumulator) {

			boolean identity = dispatcher.dispatch(instance, context);
			return predicates.length == 0 ? accumulator && identity
					: accumulator && Arrays.stream(predicates).reduce(identity, (acc, p) -> p.apply(instance, acc),
							NO_COMBINER);
		}
	}
}
