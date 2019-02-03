package com.yellowfeet.core.util;

import com.yellowfeet.core.debug.SignedError;
import com.yellowfeet.core.debug.UnsignedError;

public class WrapCounter {
	private int i;
	private int min, max;
	
	public WrapCounter(int min, int max, int initial) {
		i = (initial < min) ? min : (initial > max) ? max : initial;
		this.min = min;
		this.max = max;
	}
	
	public WrapCounter(int min, int max) {
		i = min;
		this.min = min;
		this.max = max;
	}
	
	public WrapCounter(int max) {
		i = 0;
	}
	
	private int wrapMore() {
		i = (i % (max + 1)) + min;
		return i;
	}
	
	private int wrapLess() {
		i = (i < min) ? (max - min + i) : i;
		return i;
	}
	
	private int wrap() {
		i = (i < min) ? (max - min + i) : (min - max + i);
		return i;
	}
	
	public int set(int i) {
		this.i = i;
		return wrap();
	}
	
	public int increment(int c) {
		if(c < 0) throw new UnsignedError();
		i += c;
		return wrapMore();
	}
	
	public int increment() {
		i += 1;
		return wrapMore();
	}
	
	public int decrement(int c) {
		if(c > 0) throw new SignedError();
		i -= c;
		return wrapLess();
	}
	
	public int decrement() {
		i -= 1;
		return wrapLess();
	}
}
