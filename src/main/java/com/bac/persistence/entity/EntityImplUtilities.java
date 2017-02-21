package com.bac.persistence.entity;

import java.util.Collections;
import java.util.Map;

/**
 * Allow read only access to some Entity protected methods
 * 
 * @author Simon Baird
 *
 */
public class EntityImplUtilities {

	public static Map<String, Object> getProperties(AbstractEntityImpl<?> entity) {

		return Collections.unmodifiableMap(entity.getProperties());
	}
	
	public static Map<String, Object> getProperties(RelationshipImpl relationship) {

		return Collections.unmodifiableMap(relationship.getProperties());
	}

}
