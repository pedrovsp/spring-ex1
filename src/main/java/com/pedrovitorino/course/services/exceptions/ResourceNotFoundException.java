package com.pedrovitorino.course.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUDI = 1l;
	
	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}
