package com.bac.application.impl;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.bac.application.ApplicationDataType;
import com.bac.application.DataTypeResolver;

/**
 * Basic implementation to resolve a String to a {@code DataType}. It assumes
 * that all DataTypes are defined in Enum classes and provides a method to
 * accumulate these definitions into a single source.
 * 
 * @author Simon Baird
 *
 */
public class DataTypeResolverFromEnum implements DataTypeResolver<ApplicationDataType> {

	private final Map<String, ApplicationDataType> allDataTypes = new HashMap<>();

	@Override
	public ApplicationDataType getDataType(String dataType) {

		return dataType == null ? null : allDataTypes.get(dataType.toUpperCase());
	}

	/**
	 * Consolidates the contents of an Enum class into a single collection. Each
	 * element is mapped by its name() method. Duplicates, therefore, will be lost.
	 * 
	 * @param eType
	 *            an Enum class which implements the {@code DataType interface}
	 */
	public <E extends Enum<E> & ApplicationDataType> void include(Class<E> eType) {

		allDataTypes.putAll(mapStringToDataType(eType));
	}

	/*
	 * Map each element by the upper case value of its name() method.
	 */
	private <E extends Enum<E> & ApplicationDataType> Map<String, ApplicationDataType> mapStringToDataType(Class<E> eType) {

		return EnumSet.allOf(eType).stream().collect(Collectors.toMap(e -> e.name().toUpperCase(), e -> e));
	}

}
