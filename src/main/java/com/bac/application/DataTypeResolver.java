package com.bac.application;

import com.bac.components.DataType;

/**
 * Specification for a class to generate {@code DataType} instances from a
 * string key
 * 
 * @author Simon Baird
 *
 */
public interface DataTypeResolver<T extends DataType> {

	/**
	 * Returns the {@code DataType} implementation which is mapped by the
	 * String.
	 * 
	 * @param dataTypeName
	 *            the String representation of the {@code DataType}
	 * 
	 * @return an implementation of the {@code DataType } interface or null if
	 *         it cannot be mapped
	 */
	T getDataType(String dataTypeName);
}
