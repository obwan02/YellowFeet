package com.yellowfeet.core.graphics.basic;

import com.yellowfeet.core.math.Mathi;

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
	
	/*
	 * Final constants are used instead of enums, because
	 * Of the extra writing required when accessing an internal enum.
	 * e.g. Color.BasicColor.RED (enum, slow) and Color.RED (static final, fast)
	 * 
	 * The colours start from 0xF0.
	 */
	public static final int WHITE = 0xF0;
	public static final int BLACK = 0xF1;
	public static final int RED   = 0xF2;
	public static final int BLUE  = 0xF3;
	public static final int GREEN = 0xF4;
	
	//@param constant is one of the predefined constants in the Color class.
	public static Color Get(int constant) {
		switch(constant) {
		case WHITE:
			return new Color(1, 1, 1, 1);
		case BLACK:
			return new Color(0, 0, 0, 1);
		case RED:
			return new Color(1, 0, 0, 1);
		case BLUE:
			return new Color(0, 0, 1, 1);
		case GREEN:
			return new Color(0, 1, 0, 1);
			default:
				throw new RuntimeException("Invalid color identifier.");
		}
	}
}
