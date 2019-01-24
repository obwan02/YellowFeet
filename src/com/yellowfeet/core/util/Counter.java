package com.yellowfeet.core.util;

public class Counter {
	
	private int _count;
	private final int _max;
	
	public Counter(int max) {
		_count = 0;
		_max = max > 0 ? max : Integer.MAX_VALUE;
	}
	
	public boolean increase(int i) {
		_count += i;
		return !(_count >= _max);
	}
	
	public boolean checkFit(int incr) {
		return !(_count + incr >= _max);
	}
	
	public void reset() {
		_count = 0;
	}
	
	public int get() {
		return _count;
	}
}
