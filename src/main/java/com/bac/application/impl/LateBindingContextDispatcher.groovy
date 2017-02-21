package com.bac.application.impl;

import com.bac.components.Context;
import com.bac.components.ContextAware;
import com.bac.application.ContextDispatcher;
/**
 * Uses the late binding facilities of Groovy to defer method binding until run time. 
 * {@code ContextAware} implementations have a default method  which will be invoked if
 * no appropriate method is found in preference.
 * 
 * @author Simon Baird
 */
class LateBindingContextDispatcher implements ContextDispatcher, Serializable {

	public boolean dispatch(ContextAware recipient, Context context) {
		return recipient.accept(context);
	}
}

