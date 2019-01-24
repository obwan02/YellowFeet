package com.yellowfeet.core.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Matrix4f {
	
	
	
	/*
	 * 		ROW-MAJOR ORDER
	 * 
	 * Needs transposing before being passed to OpenGL
	 */
	
	public static int INDEX(int x, int y) {
		return y * 4 + x;
	}
		
	public float[] data;
	
	public Matrix4f(float diagonal) {
		data = new float[16];
		setDiagonal(diagonal);
	}
	
	public void setDiagonal(float diag) {
		data[0] = diag;
		data[5] = diag;
		data[10] = diag;
		data[15] = diag;
	}
	
	//Faster than getOffHeapBuffer because of allocation on stack
	public FloatBuffer getStackBuffer(MemoryStack stack) {
		FloatBuffer b = stack.mallocFloat(16);
		b.put(data); b.rewind();
		return b;
	}
	
	//memory needs to be freed, and slower
	public FloatBuffer getOffHeapBuffer() {
		FloatBuffer b = MemoryUtil.memAllocFloat(16);
		b.put(data); b.rewind();
		return b;
	}
	
	public static Matrix4f orthographic(float left, float right, float top, float bottom, float near, float far) {
		Matrix4f result = new Matrix4f(1);
		result.data[0] = 2 / (right - left);
		result.data[3] = -(right + left) / (right - left);
		
		result.data[5] = 2 / (top - bottom);
		result.data[7] = -(top + bottom) / (top - bottom);
		
		result.data[10] = -2 / (far - near);
		result.data[11] = -(far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f translation(Vector3 trans) {
		Matrix4f result = new Matrix4f(1);
		result.data[INDEX(3, 0)] = trans.x;
		result.data[INDEX(3, 1)] = trans.y;
		result.data[INDEX(3, 2)] = trans.z;
		return result;
	}
	
	public static Matrix4f rotationZ(float angle) {
		Matrix4f result = new Matrix4f(1);
		result.data[INDEX(0, 0)] = (float)  cos(angle);
		result.data[INDEX(1, 0)] = (float) -sin(angle);
		result.data[INDEX(0, 1)] = (float)  sin(angle);
		result.data[INDEX(1, 1)] = (float)  cos(angle);
	
		return result;
	}
	
	public static Matrix4f scale(Vector3 scale) {
		Matrix4f result = new Matrix4f(1);
		result.data[INDEX(0, 0)] = scale.x;
		result.data[INDEX(1, 1)] = scale.y;
		result.data[INDEX(2, 2)] = scale.z;
		
		return result;
	}
	
	
	public static Vector3 multiply(Matrix4f a, Vector3 b) {
		float[] data = new float[3];
		for(int i = 0; i < 3; i++) {
			data[i] = a.data[(i * 4)] * b.x + a.data[(i * 4)+1] * b.y + b.z * a.data[(i * 4)+2] + a.data[(i * 4)+3];
		}
		
		return new Vector3(data[0], data[1], data[2]);
	}
	
	public static Matrix4f multiply(Matrix4f a, Matrix4f b) {
		Matrix4f mat = new Matrix4f(0);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 4; k++) {					
					mat.data[INDEX(j, i)] += a.data[INDEX(k, i)] * b.data[INDEX(j, k)];
				}
			}
		}
		
		return mat;
	}
	
}
