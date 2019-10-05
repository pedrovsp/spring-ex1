package com.pedrovitorino.course.services.exceptions;

public class JWTAuthencticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JWTAuthencticationException(String msg) {
		super(msg);
	}
}
