package com.yellowfeet.core.graphics;

import java.io.File;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import com.yellowfeet.core.util.FileUtil;
import static org.lwjgl.opengl.GL30.*;

public class ShaderPart {
	
	private int _id;
	
	public ShaderPart(File file, int type) {
		String source = FileUtil.read(file);
		_id = glCreateShader(type);
		glShaderSource(_id, source);
		glCompileShader(_id);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {			
			IntBuffer buffer = stack.mallocInt(1);
			glGetShaderiv(_id, GL_COMPILE_STATUS, buffer);
			
			if(buffer.get(0) == GL_FALSE) {
				String log = glGetShaderInfoLog(_id);
				throw new RuntimeException("Shader compile error: \n" + log);
			}
		}
	}
	
	protected int getId() {
		return _id;
	}
	
}
