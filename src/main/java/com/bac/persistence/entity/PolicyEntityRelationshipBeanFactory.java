package com.bac.persistence.entity;

import com.bac.application.PolicyEntityRelationshipFactory;
import com.bac.application.impl.AbstractBeanFactory;
import com.bac.components.Entity;

public class PolicyEntityRelationshipBeanFactory
		extends AbstractBeanFactory<Class<? extends Entity<?>>, PolicyEntityRelationship>
		implements PolicyEntityRelationshipFactory {

	private static final long serialVersionUID = -6534577744867893854L;

	@Override
	public PolicyEntityRelationship newEntityRelationship() {

		return createBeanByType(PolicyEntityImpl.class);
	}

}
