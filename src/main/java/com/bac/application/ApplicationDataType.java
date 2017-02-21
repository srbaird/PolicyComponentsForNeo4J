package com.bac.application;

import com.bac.components.DataType;

/**
 * Specialisation of the {@code DataType} interface that enables it to be
 * directly implemented by an enum
 * 
 * @author simon
 *
 */
public interface ApplicationDataType extends DataType {
	
	String name();

}
