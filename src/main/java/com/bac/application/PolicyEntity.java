package com.bac.application;

import com.bac.components.Entity;

/**
 * A wrapper for an entity 'type'. Relationships between instances of this class
 * define the overall schema for an implementation of this application.
 * 
 * @author Simon Baird
 *
 */
public interface PolicyEntity extends Entity<PolicyEntity> {

	/**
	 * Get the wrapped {@code DataType}.
	 * 
	 * @return the {@code DataType} encapsulated by this instance.
	 */
	ApplicationDataType getPolicyDataType();

	/**
	 * Set the wrapped {@code DataType}.
	 * 
	 * @param dataType
	 *            the {@code DataType} encapsulated by this instance.
	 */
	void setPolicyDataType(ApplicationDataType dataType);
}
