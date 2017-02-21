
package com.bac.application.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Generalised class to register Spring Beans by name and to manufacture
 * instances by Key
 * 
 * @author Simon Baird
 * @param <K>
 *            the requied type of the Key
 * @param <T>
 *            the required type of Spring Bean
 */
public abstract class AbstractBeanFactory<K, T> implements ApplicationBeanFactory<K, T>, Serializable {


	private static final long serialVersionUID = 8695571555755592522L;
	
	@Autowired
	transient protected ApplicationContext appContext;
	//
	protected Map<K, String> beanTypeMap = new HashMap<>();
	//
	protected String defaultBeanName;
	//
	protected final String NULL_DEFAULT_BEAN_NAME = " Unable to generate a component for '%s'";
	protected final String UNSUPPORTED_OPERATION_FORMAT = " Method '%s' is not supported";
	//
	protected static Logger logger = LoggerFactory.getLogger(AbstractBeanFactory.class);

	@Override
	public void setBeanTypeMap(Map<K, String> beanNames) {
		beanTypeMap = beanNames;
	}

	@Override
	public void setDefaultBeanName(String beanName) {
		defaultBeanName = beanName;
	}


	protected ApplicationContext getApplicationContext() {

		return appContext;
	} 

	@Override
	public T createBeanByType(K type) {

		String beanName = defaultBeanName;
		if (beanTypeMap != null && beanTypeMap.containsKey(type)) {
			beanName = beanTypeMap.get(type);
		}
		if (beanName == null) {
			throw new IllegalStateException(
					String.format(NULL_DEFAULT_BEAN_NAME, type == null ? "Not Supplied" : type));
		}
		T bean = createBean(beanName);
		return bean;
	}

	public T createBean(String beanName) {

		@SuppressWarnings("unchecked")
		T bean = (T) getApplicationContext().getBean(beanName);
		return bean;
	}
}
