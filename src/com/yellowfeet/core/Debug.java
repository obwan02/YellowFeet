package com.yellowfeet.core;

public final class Debug {

	private Debug() {}
	
	public static void Assert(boolean s) {
		if(!s) throw new RuntimeException("Debug assertion failed.");
	}
	
	public static void Assert(boolean s, CharSequence msg) {
		if(!s) throw new RuntimeException("" + msg);
	}
	
	public static <T extends Exception> void ThrowException(T exception) throws T {
		throw exception;
	}
	
	public static <T extends Error> void ThrowError(T error) {
		throw error;
	}
	
	public static void Warn(CharSequence msg) {
		System.out.println("WARNING: " + msg);
	}
	
	public static void WarnAssert(boolean s, CharSequence msg) {
		if(!s) Warn(msg);
	}
}
