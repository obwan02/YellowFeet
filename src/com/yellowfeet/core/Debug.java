package com.yellowfeet.core;

public final class Debug {

	private Debug() {}
	
	public static void Assert(boolean s) {
		if(!s) throw new RuntimeException("Debug assertion failed.");
	}
	
	public static void Assert(boolean s, String msg) {
		if(!s) throw new RuntimeException(msg);
	}
	
	public static <T extends Exception> void ThrowException(T exception) throws T {
		throw exception;
	}
	
	public static <T extends Error> void ThrowError(T error) {
		throw error;
	}
}
