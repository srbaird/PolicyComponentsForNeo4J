package com.bac.application.impl;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.bac.application.ApplicationRelationshipType;
import com.bac.application.RelationshipTypeResolver;

/**
 * Basic implementation to resolve a String to a {@code RelationshipType}. It
 * assumes that all RelationshipType are defined in Enum classes and provides a
 * method to accumulate these definitions into a single source.
 * 
 * @author Simon Baird
 *
 */
public class RelationshipTypeResolverFromEnum implements RelationshipTypeResolver<ApplicationRelationshipType> {

	private final Map<String, ApplicationRelationshipType> allDataTypes = new HashMap<>();

	@Override
	public ApplicationRelationshipType getRelationshipType(String relationshipTypeName) {

		return relationshipTypeName == null ? null : allDataTypes.get(relationshipTypeName.toUpperCase());
	}

	/**
	 * Consolidates the contents of an Enum class into a single collection. Each
	 * element is mapped by its name() method. Duplicates, therefore, will be
	 * lost.
	 * 
	 * @param eType
	 *            an Enum class which implements the
	 *            {@code RelationshipType interface}
	 */
	public <E extends Enum<E> & ApplicationRelationshipType> void include(Class<E> eType) {

		allDataTypes.putAll(mapStringToRelationshipType(eType));
	}

	/*
	 * Map each element by the upper case value of its name() method.
	 */
	private <E extends Enum<E> & ApplicationRelationshipType> Map<String, ApplicationRelationshipType> mapStringToRelationshipType(
			Class<E> eType) {

		return EnumSet.allOf(eType).stream().collect(Collectors.toMap(e -> e.name().toUpperCase(), e -> e));
	}


	@Override
	public Set<ApplicationRelationshipType> allRelationshipTypes() {

		return new HashSet<>(allDataTypes.values());
	}
}
