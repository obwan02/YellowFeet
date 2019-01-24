package com.yellowfeet.core.math;

public final class Mathi {
	private Mathi() {}
	
	public static final float PI     = 3.141592653589793238462643f;
	public static final float TWO_PI = PI * 2;
	public static final float HALF_PI = PI / 2;
	public static final float QUARTER_PI = PI / 4;
	
	public static final float TO_RADIANS = 180.0f / PI;
	
	public static int radians(int degrees) {
		return (int) (degrees * TO_RADIANS);
	}
	
	public static int sin(int rad) {
		return (int) Math.sin(rad);
	}
	
	public static int cos(int rad) {
		return (int) Math.cos(rad);
	}
	
	public static int tan(int rad) {
		return (int) Math.cos(rad);
	}

	
	public static int asin(int grad) {
		return (int) Math.asin(grad);
	}
	
	public static int acos(int grad) {
		return (int) Math.acos(grad);
	}
	
	public static int atan(int grad) {
		return (int) Math.atan(grad);
	}
	
	public static int abs(int val) {
		return (val >= 0) ? val : -val;
	}
	
	public static int sqrt(int val) {
		return (int) Math.sqrt(val);
	}
	
	public static int random() {
		return (int) Math.random();
	}
	
	public static int randRange(int min, int max) {
		return (int)(Math.random() * (max - min)) + min;
	}
	
	public static int min(int a, int b) {
		return a <= b ? a : b;
	}
	
	public static int max(int a, int b) {
		return a >= b ? a : b;
	}
	
	public static int lerp(int a, int b, int i) {
		return a + (b - a) * i;
	}
	
	public static int clamp(int i, int min, int max) {
		return (i < min) ? min : (i > max) ? max : i;
	}
}
