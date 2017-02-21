package com.bac.persistence.entity;

import static com.bac.application.impl.ApplicationDataTypes.POLICY;
import static com.bac.application.impl.ApplicationRelationshipTypes.ASSIGNEE_OF;
import static com.bac.application.predicate.Where.and;
import static com.bac.application.predicate.Where.or;
import static com.bac.application.predicate.Where.where;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationRelationshipType;
import com.bac.components.Context;

public class EntityComposerTestApplyContext extends AbstractEntityTest {

	final Context trueContext = new TrueContext();
	final Context falseContext = new FalseContext();

	@Test
	public void predicate_With_True_Context_Should_Not_Affect_Relationship() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);
		setRelationshipFactory(instance);

		final PolicyEntityImpl resultEntity = getPolicyEntity();

		assertFalse(resultEntity.getEntities().isEmpty());

		final PolicyEntityImpl contextEntity = instance.applyContext(resultEntity, where(trueContext));

		assertFalse(contextEntity.getEntities().isEmpty());

	}

	@Test
	public void predicate_With_True_Or_False_Context_Should_Not_Affect_Relationship() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setRelationshipFactory(instance);

		final PolicyEntityImpl resultEntity = getPolicyEntity();

		assertFalse(resultEntity.getEntities().isEmpty());

		final PolicyEntityImpl contextEntity = instance.applyContext(resultEntity,
				where(trueContext, or(falseContext)));

		assertFalse(contextEntity.getEntities().isEmpty());
	}

	@Test
	public void predicate_With_False_Context_Should_Remove_Relationship() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setRelationshipFactory(instance);

		final PolicyEntityImpl resultEntity = getPolicyEntity();
		assertFalse(resultEntity.getEntities().isEmpty());

		final PolicyEntityImpl contextEntity = instance.applyContext(resultEntity, where(falseContext));

		assertTrue(contextEntity.getEntities().isEmpty());
	}

	@Test
	public void predicate_With_True_And_False_Context_Should_Remove_Relationship() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setRelationshipFactory(instance);

		final PolicyEntityImpl resultEntity = getPolicyEntity();
		assertFalse(resultEntity.getEntities().isEmpty());

		final PolicyEntityImpl contextEntity = instance.applyContext(resultEntity,
				where(trueContext, and(falseContext)));

		assertTrue(contextEntity.getEntities().isEmpty());
	}

	@Test
	public void predicate_With_True_Context_Should_Not_Affect_Entity() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setEntityFactory(instance);

		final PolicyEntityImpl resultEntity = dao.createPolicyEntity(dao.newPolicyEntity());

		assertNotNull(instance.applyContext(resultEntity, where(trueContext)));
	}

	@Test
	public void predicate_With_True_Context_Should_Not_Affect_Macro_Entity() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setEntityFactory(instance);

		final PolicyEntityImpl resultEntity = dao.createPolicyEntity(dao.newPolicyEntity());

		assertNotNull(instance.applyContext(resultEntity, where(trueContext)));
	}

	@Test
	public void predicate_With_True_Context_Should_Not_Return_Null_Entity() {

		PolicyEntityComposer instance = (PolicyEntityComposer) appContext.getBean("policyEntityComposer");
		dao.setEntityComposer(instance);

		setEntityFactory(instance);

		final PolicyEntityImpl resultEntity = getPolicyEntity();

		assertNotNull(instance.applyContext(resultEntity, where(trueContext)));
	}

	// ********************************************************************************************************
	//
	// Environment set up methods
	//
	// ********************************************************************************************************

	/**
	 * The mock entity factory will return a new instance of a ContextAwareEntity
	 * @param instance
	 */
	private void setEntityFactory(PolicyEntityComposer instance) {

		PolicyEntityBeanFactory factory = mock(PolicyEntityBeanFactory.class, new Answer<ContextAwareEntity>() {
			@Override
			public ContextAwareEntity answer(InvocationOnMock invocation) throws Throwable {

				if (invocation.getMethod().getName().equals("newPolicyEntity")) {
					return new ContextAwareEntity();
				} else {
					return null;
				}
			}
		});
		// The composer will now get a ContextAwareEntity from the factory
		instance.setPolicyEntityFactory(factory);
	}

	/**
	 * The mock relationship factory will return a new instance of a ContextAwareRelationship
	 * 
	 * @param instance
	 */
	private void setRelationshipFactory(PolicyEntityComposer instance) {

		RelationshipBeanFactory factory = mock(RelationshipBeanFactory.class, new Answer<ContextAwareRelationship>() {
			@Override
			public ContextAwareRelationship answer(InvocationOnMock invocation) throws Throwable {

				if (invocation.getMethod().getName().equals("newRelationship")) {
					return new ContextAwareRelationship(ASSIGNEE_OF);
				} else {
					return null;
				}
			}
		});

		instance.setRelationshipFactory(factory);
	}

	private PolicyEntityImpl getPolicyEntity() {

		final PolicyEntityImpl fromEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		final RelationshipImpl relationship = dao.newRelationship(ASSIGNEE_OF);
		final PolicyEntityImpl toEntity = dao.createPolicyEntity(dao.newPolicyEntity());
		return dao.createPolicyRelationship(fromEntity, relationship, toEntity);
	}

	/**
	 * Relationship which is sensitive to the test Contexts.
	 * 
	 * @author simon
	 *
	 */
	private final class ContextAwareRelationship extends DefaultRelationshipImpl {

		private final ApplicationRelationshipType overrideRelationshipType;

		public ContextAwareRelationship(ApplicationRelationshipType overrideRelationshipType) {
			super();
			this.overrideRelationshipType = overrideRelationshipType;
		}

		@Override
		public ApplicationRelationshipType getRelationshipType() {

			return overrideRelationshipType;
		}

		@SuppressWarnings("unused")
		boolean accept(FalseContext context) {
			return false;
		}

		@SuppressWarnings("unused")
		boolean accept(TrueContext context) {

			return true;
		}
	}

	/**
	 * Entity which is sensitive to the test Contexts.
	 * 
	 * @author simon
	 *
	 */
	private final class ContextAwareEntity extends PolicyEntityImpl {

		@Override
		public ApplicationDataType getDataType() {

			return POLICY;
		}

		@SuppressWarnings("unused")
		boolean accept(FalseContext context) {

			return false;
		}

		@SuppressWarnings("unused")
		boolean accept(TrueContext context) {

			return true;
		}
	}

	private final class FalseContext implements Context {
	}

	private final class TrueContext implements Context {
	}
}
