package com.bac.application;

import java.util.Date;

import com.bac.components.Context;

/**
 * Defines a class that will determine whether a given data successfully falls
 * within a given range
 * 
 * @author simon
 *
 */
public interface DateRangeContext extends Context {

	boolean accept(Date date);

}
