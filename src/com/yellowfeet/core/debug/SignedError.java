package com.yellowfeet.core.debug;

public class SignedError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8205872363591267431L;

	public SignedError(String message) {
		super(message);
	}
	
	public SignedError() {
		super("The value must be 0 or positive");
	}
	
}
