package com.bac.application;

import com.bac.components.Relationship;

/**
 * Specialisation of the {@code EntityRelationshipFactory} to generate for
 * {@code PolicyEntity} types.
 * 
 * @author Simon Baird
 *
 */
public interface PolicyEntityRelationshipFactory extends EntityRelationshipFactory<PolicyEntity, Relationship<ApplicationRelationshipType>> {

}
