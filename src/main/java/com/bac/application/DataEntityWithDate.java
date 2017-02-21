package com.bac.application;

import java.util.Date;

public interface DataEntityWithDate extends DataEntity {

	Date getValueDate();
	
	void setDate(Date date);
}
