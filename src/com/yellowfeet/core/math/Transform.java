package com.yellowfeet.core.math;

public class Transform {
	
	public Vector3 position;
	public float   rotation;
	public float scale;
	
	private Matrix4f _temp;
	
	public Transform(Vector3 position, float scale, float angle) {
		this.position = position; this.scale = scale; this.rotation = angle;
	}
	
	public Transform(Vector3 position, float angle) {
		this(position, 1, 0);
	}
	
	public Transform(Vector3 position) {
		this(position, 0f);
	}
	
	public Transform() {
		this(new Vector3(0));
	}
	
	public Vector3 getPosition() {
		return position.clone();
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public void move(Vector2 add) {
		position.add(add);
	}
	
	public void move(Vector3 add) {
		position.add(add);
	}
	
	public void move(float dx, float dy) {
		position.add(dx, dy);
	}
	
	public void reloadMatrix() {
		//TODO: Make proper transformation matrix generator
		_temp = Matrix4f.multiply(Matrix4f.translation(position), Matrix4f.multiply(Matrix4f.rotationZ(rotation), Matrix4f.scale(new Vector3(scale))));
	}
	
	public Matrix4f getTransformMatrix_temp() {
		return _temp;
	}
	
	//Applies the matrix to a Vector2
	public Vector3 apply(Vector3 v) {
		return Matrix4f.multiply(_temp, v);
	}
	
	//Applies the matrix to a Vector2
	public Vector2 apply(Vector2 v) {
		return Matrix4f.multiply(_temp, new Vector3(v, 1.0f));
	}
	
	//Extra functions
	public float rotate(float rad) {
		rotation = rotation + rad;
		return rotation;
	}
}
