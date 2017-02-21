package com.bac.application;

import com.bac.components.Entity;

public interface DataEntity extends Entity<DataEntity> {

	String getName();

	void setName(String name);
}
