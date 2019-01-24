package com.yellowfeet.core.util;

public final class Trigger {

	private boolean _triggered;
	public Trigger() { _triggered = false; };
	
	public void trigger() { _triggered = true; }
	public boolean get() { return _triggered; }
	//public void reset() { _triggered = false; }
}
