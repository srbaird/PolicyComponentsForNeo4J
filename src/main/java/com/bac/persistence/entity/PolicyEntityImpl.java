package com.bac.persistence.entity;

import java.util.Map;

import org.neo4j.graphdb.Node;

import com.bac.application.ApplicationDataType;
import com.bac.application.DataTypeResolver;
import com.bac.application.PolicyEntity;

/**
 * Persistence specific implementation of the PolicyEntity. This type acts as a
 * wrapper over another {@code DataType} and has only a single attribute.
 * 
 * @author Simon Baird
 *
 */
public class PolicyEntityImpl extends AbstractEntityImpl<PolicyEntity> implements PolicyEntity {

	//
	public static final String POLICY_DATATYPE_PROPERTY_NAME = "POLICY_DATATYPE";
	private ApplicationDataType policyDataType;

	@Override
	public ApplicationDataType getPolicyDataType() {

		return policyDataType;
	}

	@Override
	public void setPolicyDataType(ApplicationDataType dataType) {

		policyDataType = dataType;
	}

	/* Protected methods */
	@Override
	Map<String, Object> getProperties() {

		Map<String, Object> properties = super.getProperties();
		properties.put(POLICY_DATATYPE_PROPERTY_NAME, getPolicyDataType() == null ? null : getPolicyDataType().name());
		return properties;
	}

	void setNode(Node node, DataTypeResolver<ApplicationDataType> resolver) {

		// Pass up the chain
		super.setNode(node, resolver);

		if (node != null && node.hasProperty(POLICY_DATATYPE_PROPERTY_NAME)) {
			
			setPolicyDataType(resolver.getDataType((String) node.getProperty(POLICY_DATATYPE_PROPERTY_NAME)));

		} else {
			
			setPolicyDataType(null);
		}
	}
}
