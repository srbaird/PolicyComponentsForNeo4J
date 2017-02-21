package com.bac.application;

import com.bac.components.RelationshipType;

/**
 * Specialisation of the {@code RelationshipType} interface that enables it to be
 * directly implemented by an Enum
 * 
 * @author simon
 *
 */
public interface ApplicationRelationshipType extends RelationshipType {
	
	String name();

}
