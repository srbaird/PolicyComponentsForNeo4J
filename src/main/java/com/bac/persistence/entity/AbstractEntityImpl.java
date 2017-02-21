package com.bac.persistence.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;

import com.bac.application.ApplicationDataType;
import com.bac.application.ApplicationRelationshipType;
import com.bac.application.DataTypeResolver;
import com.bac.components.Entity;
import com.bac.components.EntityRelationship;
import com.bac.components.Relationship;
import com.bac.components.RelationshipType;

public class AbstractEntityImpl<T extends Entity<T>> implements Entity<T> {

	private Long id;
	//
	public static final String DATATYPE_PROPERTY_NAME = "DATATYPE";
	private ApplicationDataType dataType;

	List<EntityRelationship<T, Relationship<ApplicationRelationshipType>>> entities;

	public AbstractEntityImpl() {
		super();
	}

	@Override
	public Long getEntityId() {

		return id;
	}

	@Override
	public ApplicationDataType getDataType() {

		return dataType;
	}

	@Override
	public List<EntityRelationship<T, ? extends Relationship<? extends RelationshipType>>> getEntities() {

		return Collections.unmodifiableList(getEntitiesList());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntityImpl<T> other = (AbstractEntityImpl<T>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	Map<String, Object> getProperties() {

		Map<String, Object> properties = new HashMap<>();
		properties.put(DATATYPE_PROPERTY_NAME, getDataType() == null ? null : getDataType().name());
		return properties;
	}

	/* restricted access methods */

	private void setEntityId(Long id) {

		this.id = id;
	}

	void setDataType(ApplicationDataType dataType) {

		this.dataType = dataType;
	}

	void setNode(Node node, DataTypeResolver<ApplicationDataType> resolver) {

		if (node == null) {

			clearProperties();

		} else {

			setProperties(node, resolver);
		}
	}

	private void setProperties(Node node, DataTypeResolver<ApplicationDataType> resolver) {

		setEntityId(node.getId());

		if (node.hasProperty(DATATYPE_PROPERTY_NAME)) {
			setDataType(resolver.getDataType((String) node.getProperty(DATATYPE_PROPERTY_NAME)));
		} else {
			setDataType(null);
		}

	}

	private void clearProperties() {

		setDataType(null);
		setEntityId(null);
	}

	void addEntity(EntityRelationship<T, Relationship<ApplicationRelationshipType>> entity) {

		getEntitiesList().add(entity);

	}

	void clearEntities() {
		
		getEntitiesList().clear();
	}

	protected List<EntityRelationship<T, Relationship<ApplicationRelationshipType>>> getEntitiesList() {

		if (entities == null) {
			entities = new ArrayList<>();
		}
		return entities;
	}

}