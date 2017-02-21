
package com.bac.application;

import java.util.List;

import com.bac.components.Relationship;

/**
 * A minimal set of operations to create a entity of a single type.
 * 
 * @author Simon Baird
 */
public interface PolicyEntityDAO<T extends PolicyEntity, R extends Relationship<?>> extends PolicyEntityFactory<T>, RelationshipFactory<R> {

	T createPolicyEntity(T policyEntity);

	void deletePolicyEntity(T policyEntity);

	T readPolicyEntity(Long id);

	T readPolicyEntity(T policyEntity);

	T updatePolicyEntity(T policyEntity);

	List<T> listPolicies();

	// Map<DataType, Map<DataType, RelationshipType>> mapPolicies();

	T createPolicyRelationship(T fromEntity, R relationship, T toEntity);

	void deletePolicyRelationship(R relationship);

	R updatePolicyRelationship(R relationship);
}
