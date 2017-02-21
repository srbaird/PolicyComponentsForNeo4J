
package com.bac.application.impl;

import java.util.Map;

/**
 * Define a class that will return Spring Beans from any key type
 * 
 * @author Simon Baird
 * @param <K>
 *            the requied type of the Key
 * @param <T>
 *            the required type of Spring Bean
 */
public interface ApplicationBeanFactory<K, T> {

	T createBeanByType(K type);

	void setDefaultBeanName(String beanName);

	void setBeanTypeMap(Map<K, String> beanNames);
}
