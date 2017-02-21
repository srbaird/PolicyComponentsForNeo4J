package com.bac.application;

/**
 * Catch all {@code RuntimeException} to signal illegal persistence operation.
 * 
 * @author Simon Baird
 *
 */
public class ApplicationPersistenceException extends RuntimeException {

	private static final long serialVersionUID = 3312841492366329434L;

	public ApplicationPersistenceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationPersistenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ApplicationPersistenceException(String message, Throwable cause) {
		super(message, cause);

	}

	public ApplicationPersistenceException(String message) {
		super(message);

	}

	public ApplicationPersistenceException(Throwable cause) {
		super(cause);

	}
}
