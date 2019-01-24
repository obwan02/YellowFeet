package com.yellowfeet.core.math;

import static java.lang.Math.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Matrix3f {
	public float[] data;
	
	public static int INDEX(int x, int y) {
		return y * 3 + x;
	}
	
	public Matrix3f(float diagonal) {
		data = new float[9]; 
		data[INDEX(0, 0)] = diagonal;
		data[INDEX(1, 1)] = diagonal;
		data[INDEX(2, 2)] = diagonal;
	}
	
	//Faster than getOffHeapBuffer because of allocation on stack
	public FloatBuffer getStackBuffer(MemoryStack stack) {
		FloatBuffer b = stack.mallocFloat(9);
		b.put(data); b.rewind();
		return b;
	}
	
	//memory needs to be freed, and slower
	public FloatBuffer getOffHeapBuffer() {
		FloatBuffer b = MemoryUtil.memAllocFloat(9);
		b.put(data); b.rewind();
		return b;
	}
	
	
	public static Matrix3f rotation(float angle) {
		Matrix3f result = new Matrix3f(1);
		result.data[INDEX(0, 0)] = (float)  cos(angle);
		result.data[INDEX(1, 0)] = (float) -sin(angle);
		result.data[INDEX(0, 1)] = (float)  sin(angle);
		result.data[INDEX(1, 1)] = (float)  cos(angle);
	
		return result;
	}
	
	public static Matrix3f translation(Vector2 trans) {
		Matrix3f result = new Matrix3f(1);
		result.data[INDEX(2, 0)] = trans.x;
		result.data[INDEX(2, 1)] = trans.y;
		return result;
	}
	
	
	public static Matrix3f scale(Vector2 scale) {
		Matrix3f result = new Matrix3f(1);
		result.data[INDEX(0, 0)] = scale.x;
		result.data[INDEX(1, 1)] = scale.y;
		return result;
	}
	
	public static Vector2 multiply(Matrix3f a, Vector2 b) {
		Vector2 result = new Vector2();
		for(int i = 0; i < 2; i++) {
			float c = a.data[(i * 3)] * b.x + a.data[(i * 3)+1] * b.y + a.data[(i * 3)+2];
			if(i == 0) result.x = c; else result.y = c;
		}
		
		return result;
	}
	
	public static Matrix3f multiply(Matrix3f a, Matrix3f b) {
		Matrix3f mat = new Matrix3f(0);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {					
					mat.data[INDEX(j, i)] += a.data[INDEX(k, i)] * b.data[INDEX(j, k)];
				}
			}
		}
		
		return mat;
	}
	
}
