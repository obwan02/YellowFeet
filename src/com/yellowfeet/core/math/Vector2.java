package com.yellowfeet.core.math;

public class Vector2 {

	public float x;
	public float y;
	
	public static Vector2 V2(float x, float y) {
		return new Vector2(x, y);
	}
	
	public Vector2() {
		this.x = 0; this.y = 0;
	}
	
	public Vector2(float x, float y) {
		this.x = x; this.y = y;
	}
	
	public Vector2(float both) {
		this.x = both; this.y = both;
	}
	
	@Override
	public Vector2 clone() {
		return new Vector2(x, y);
	}
	
	@Override
	public String toString() {
		return "Vector2: " + String.valueOf(x) + ", " + String.valueOf(y);
	}
	
	
	//Normal methods (affect object)
	public Vector2 add(Vector2 b) {
		x += b.x; y += b.y; return this;
	}
	
	public Vector2 subtract(Vector2 b) {
		x -= b.x; y -= b.y; return this;
	}
	
	public Vector2 multiply(Vector2 b) {
		x *= b.x; y *= b.y; return this;
	}
	
	public Vector2 divide(Vector2 b) {
		x /= b.x; y /= b.y; return this;
	}
	
	//Normal float methods
	public Vector2 add(float a, float b) {
		x += a; y += b; return this;
	}
	
	public Vector2 subtract(float a, float b) {
		x -= a; y -= b; return this;
	}
	
	public Vector2 multiply(float a, float b) {
		x *= a; y *= b; return this;
	}
	
	public Vector2 divide(float a, float b) {
		x /= a; y /= b; return this;
	}
	
	
	//Static methods (don't affect the object)
	public static Vector2 add(Vector2 a, Vector2 b) {
		return a.clone().add(b);
	}
	
	public static Vector2 subtract(Vector2 a, Vector2 b) {
		return a.clone().subtract(b);
	}

	public static Vector2 multiply(Vector2 a, Vector2 b) {
		return a.clone().multiply(b);
	}
	
	public static Vector2 divide(Vector2 a, Vector2 b) {
		return a.clone().divide(b);
	}
	
	//Static float methods
	public static Vector2 add(Vector2 a, float b, float c) {
		return a.clone().add(b, c);
	}
	
	public static Vector2 subtract(Vector2 a, float b, float c) {
		return a.clone().subtract(b, c);
	}
	
	public static Vector2 multiply(Vector2 a, float b, float c) {
		return a.clone().multiply(b, c);
	}
	
	public static Vector2 divide(Vector2 a, float b, float c) {
		return a.clone().divide(b, c);
	}

	
	
	//Random methods
	public Vector2 scale(float s) {
		x *= s; y *= s;
		return this;
	}
	
	public Vector2 set(float x, float y) {
		this.x = x; this.y = y; return this;
	}
	
	public float size() {
		return Mathf.sqrt(x*x + y*y);
	}

	
	//Normalising functions
	public Vector2 normalise() {
		scale(1 / size()); return this;
	}
	
	public static Vector2 normalised(Vector2 a) {
		return a.clone().normalise();
	}
	
	
	//Vector methods
	public float dot(Vector2 b) {
		return x * b.x + y * b.y;
	}
	
	public float scalarProject(Vector2 dir) {
		return this.dot(dir) / dir.size();
	}
	
	public Vector2 project(Vector2 dir) {
		return Vector2.normalised(dir).scale(this.scalarProject(dir));
	}
	
	public Vector2 rotate(float rads) {
		float newrot = Mathf.atan(y / x) + rads;
		float size = size();
		x = size * Mathf.cos(newrot);
		y = size * Mathf.sin(newrot);
		return this;
	}
	
	public Vector2 lerp(Vector2 other, float i) {
		x = x + (other.x - x) * i;
		y = y + (other.y - y) * i;
		return this;
	}
	
	public static Vector2 Lerp(Vector2 a, Vector2 b, float i) {
		return a.clone().lerp(b, i);
	}
	
	public static float Distance(Vector2 a, Vector2 b) {
		return b.clone().subtract(a).size();
	}
	
	public static Vector2 tripleCrossProduct(Vector2 a, Vector2 b, Vector2 c) {
		//Triple cross product expansion (B * (C · A) - A * (C · B))
		return b.clone().scale(c.dot(a)).subtract(a.clone().scale(c.dot(b)));
	}
	
	
	public static enum BasicVector {
		ONE,
		NEGATIVE_ONE,
		TWO,
		NEGATIVE_TWO,
		
		UP,
		DOWN,
		LEFT,
		RIGHT,
		
		NORTH,
		EAST,
		SOUTH,
		WEST,
		
		NORTH_EAST,
		SOUTH_EAST,
		SOUTH_WEST,
		NORTH_WEST,
		
		RANDOM
	}
	
	public static Vector2 Get(BasicVector b) {
		if(b == null) return new Vector2(0);
		switch(b) {
		case ONE:
		case NORTH_EAST:
			return new Vector2(1, 1);
		case NEGATIVE_ONE:
		case SOUTH_WEST:
			return new Vector2(-1, -1);
		case SOUTH_EAST:
			return new Vector2(1, -1);
		case NORTH_WEST:
			return new Vector2(-1, 1);
			
		case TWO:
			return new Vector2(2, 2);
		case NEGATIVE_TWO:
			return new Vector2(-2, -2);
			
		case UP:
		case NORTH:
			return new Vector2(0, 1);
		case DOWN:
		case SOUTH:
			return new Vector2(0, -1);
		case LEFT:
		case WEST:
			return new Vector2(-1, 0);
		case RIGHT:
		case EAST:
			return new Vector2(1, 0);
		case RANDOM:
			return (new Vector2(0, 1)).rotate(Mathf.TWO_PI * Mathf.random());
		default:
			return new Vector2(0, 0);
		}
	}
}
