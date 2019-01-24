package com.yellowfeet.core.math;

public class Vector3 extends Vector2 {

	public float z;
	
	public static Vector3 V3(float x, float y, float z) {
		return new Vector3(x, y, z);
	}
	
	public Vector3(float x, float y, float z) {
		super(x, y); this.z = z;
	}
	
	public Vector3(float all) {
		super(all); this.z = all;
	}
	
	public Vector3(Vector2 base, float z) {
		super(base.x, base.y); this.z = z;
	}
	
	public Vector3() {
		this(0);
	}

	@Override
	public Vector3 clone() {
		return new Vector3(x, y, z);
	}
	
	@Override
	public String toString() {
		return "Vector3: " + String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(z);
	}
	
	
	//Normal methods (affect object)
	public Vector3 add(Vector3 b) {
		x += b.x; y += b.y; z += b.z; return this;
	}
	
	public Vector3 subtract(Vector3 b) {
		x -= b.x; y -= b.y; z += b.z; return this;
	}
	
	public Vector3 multiply(Vector3 b) {
		x *= b.x; y *= b.y; z += b.z; return this;
	}
	
	public Vector3 divide(Vector3 b) {
		x /= b.x; y /= b.y; z += b.z; return this;
	}
	
	
	//Static methods (don't affect the object)
	public static Vector3 add(Vector3 a, Vector3 b) {
		return a.clone().add(b);
	}
	
	public static Vector3 subtract(Vector3 a, Vector3 b) {
		return a.clone().subtract(b);
	}

	public static Vector3 multiply(Vector3 a, Vector3 b) {
		return a.clone().multiply(b);
	}
	
	public static Vector3 divide(Vector3 a, Vector3 b) {
		return a.clone().divide(b);
	}
	
	@Override
	public Vector3 scale(float s) {
		x *= s; y *= s; z *= s;
		return this;
	}
	
	@Override
	public float size() {
		return Mathf.sqrt(x*x + y*y + z*z);
	}
	
	@Override
	public Vector3 normalise() {
		return scale(1 / size());
	}
	
	public Vector3 set(float x, float y, float z) {
		super.set(x, y); this.z = z;
		return this;
	}
}
