package com.democrance.policy.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
	}
}
