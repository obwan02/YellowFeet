package com.yellowfeet.core.graphics;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.system.MemoryStack;

import com.yellowfeet.core.math.Matrix3f;
import com.yellowfeet.core.math.Matrix4f;

public class Shader {

	public static boolean SHADER_MISSING_UNIFORM_WARNING = true;
	
	private final int _id;
	private HashMap<String, Integer> _uniformIds;
	
	public Shader(ShaderPart[] arr) {
		_uniformIds = new HashMap<>();
		_id = glCreateProgram();
		
		for(ShaderPart part : arr) {
			int id = part.getId();
			glAttachShader(_id, id);
		}
		
		glLinkProgram(_id);
	}
	
	public void bind() {
		glUseProgram(_id);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public int getId() {
		return _id;
	}
	
	private int getUniformLocation(String name) {
		Integer result = _uniformIds.get(name);
		if(result == null) {
			result = glGetUniformLocation(_id, name);
			_uniformIds.put(name, result);
			
			if(result == -1) {
				if(SHADER_MISSING_UNIFORM_WARNING)
					System.out.println("Uniform variable not found: " + name);
			} 
			
			return result;
		} 
		
		return result;
	}
	
	public void setUniformMat4f(String name, Matrix4f mat) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer matBuffer = mat.getStackBuffer(stack);
			
			//In case of null values, Integer must be used
			Integer id = getUniformLocation(name);
			
			bind();
			glUniformMatrix4fv(id, true, matBuffer);
			unbind();
		}
	}
	
	public void setUniformInt(String name, Integer val) {
		Integer id = getUniformLocation(name);
		
		bind();
		glUniform1i(id, val);
		unbind();
	}

	public void setUniformMat3f(String name, Matrix3f mat) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer matBuffer = mat.getStackBuffer(stack);
			
			//In case of null values, Integer must be used
			Integer id = getUniformLocation(name);
			
			bind();
			glUniformMatrix3fv(id, true, matBuffer);
			unbind();
		}
	}
	
	public void setUniformFloat(String name, float f) {
		Integer id = getUniformLocation(name);
		
		bind();
		glUniform1f(id, f);
		unbind();
	}
}
