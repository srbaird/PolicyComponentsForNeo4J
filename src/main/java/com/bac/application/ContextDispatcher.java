
package com.bac.application;

import com.bac.components.Context;
import com.bac.components.ContextAware;

/**
 * Use
 * @author Simon Baird
 */
public interface ContextDispatcher {

    boolean dispatch(ContextAware recipient, Context context);
}
