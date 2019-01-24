package com.yellowfeet.core.math;

public final class Mathf {

	private Mathf() {}
	
	public static final float PI     = 3.141592653589793238462643f;
	public static final float TWO_PI = PI * 2;
	public static final float HALF_PI = PI / 2;
	public static final float QUARTER_PI = PI / 4;
	
	public static final float TO_RADIANS = 180.0f / PI;
	
	public static float radians(float degrees) {
		return degrees * TO_RADIANS;
	}
	
	public static float sin(float rad) {
		return (float) Math.sin(rad);
	}
	
	public static float cos(float rad) {
		return (float) Math.cos(rad);
	}
	
	public static float tan(float rad) {
		return (float) Math.cos(rad);
	}

	
	public static float asin(float grad) {
		return (float) Math.asin(grad);
	}
	
	public static float acos(float grad) {
		return (float) Math.acos(grad);
	}
	
	public static float atan(float grad) {
		return (float) Math.atan(grad);
	}
	
	public static float abs(float val) {
		return (val >= 0) ? val : -val;
	}
	
	public static float sqrt(float val) {
		return (float) Math.sqrt(val);
	}
	
	public static float random() {
		return (float) Math.random();
	}
	
	public static float randRange(float min, float max) {
		return (random() * (max - min)) + min;
	}
	
	public static float min(float a, float b) {
		return a <= b ? a : b;
	}
	
	public static float max(float a, float b) {
		return a >= b ? a : b;
	}
	
	public static float lerp(float a, float b, float i) {
		return a + (b - a) * i;
	}
	
	public static float clamp(float i, float min, float max) {
		return (i < min) ? min : (i > max) ? max : i;
	}
}
