package com.yellowfeet.core.graphics.basic;

import com.yellowfeet.core.math.Mathi;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class Color extends Object {
	
	public static final int MAX_VALUE = 255;
	
	public final int r,g,b,a;

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	
	@Override
	public Color clone() {
		return new Color(r, g, b, a);
	}
	
	@Override
	public String toString() {
		return String.valueOf(r) + ", " + String.valueOf(g) + ", " + String.valueOf(b) + ", " + String.valueOf(a);
	}
		
	private static int ClampValue(int v) {
		return Mathi.clamp(v, 0, 255);
	}
	
	public static Color Clamp(Color c) {
		return new Color(ClampValue(c.r), ClampValue(c.g), ClampValue(c.b), ClampValue(c.a));
	}
	
	public static Color Invert(Color c) {
		return new Color(~c.r, ~c.g, ~c.b, c.a);
	}
	
	public static final Color WHITE = new Color(1, 1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0, 1);
	public static final Color RED   = new Color(1, 0, 0, 1);
	public static final Color BLUE  = new Color(0, 0, 1, 1);
	public static final Color GREEN = new Color(0, 1, 0, 1);
	
}
