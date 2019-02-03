package com.yellowfeet.core.debug;

public class UnsignedError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = -956521269370870452L;

	public UnsignedError(String message) {
		super(message);
	}
	
	public UnsignedError() {
		super("The value must be 0 or positive");
	}
	
}
