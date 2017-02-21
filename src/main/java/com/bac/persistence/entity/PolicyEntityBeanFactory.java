package com.bac.persistence.entity;

import com.bac.application.ApplicationDataType;
import com.bac.application.PolicyEntityFactory;
import com.bac.application.impl.AbstractBeanFactory;
import com.bac.application.impl.ApplicationDataTypes;
import com.bac.components.DataType;

public class PolicyEntityBeanFactory extends AbstractBeanFactory<DataType, PolicyEntityImpl>
		implements PolicyEntityFactory<PolicyEntityImpl> {
	

	private static final long serialVersionUID = 7323886030886804993L;
	
	// TEMP
	// TODO: Move this to an application namespace
	private final ApplicationDataType POLICY_DATATYPE = ApplicationDataTypes.POLICY;

	@Override
	public PolicyEntityImpl newPolicyEntity() {

		PolicyEntityImpl newPolicyEntity = createBeanByType(POLICY_DATATYPE);
		newPolicyEntity.setDataType(POLICY_DATATYPE);
		return newPolicyEntity;
	}

}
