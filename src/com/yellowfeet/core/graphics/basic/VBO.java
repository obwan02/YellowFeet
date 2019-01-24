package com.yellowfeet.core.graphics.basic;

import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

//Inherits most properties from GLBuffer
public class VBO extends GLBuffer {
	
	//@param('type') the type of buffer to create. e.g. GL_STREAM_DRAW
	public VBO(int bytesize, int type) {
		super(bytesize, type, GL_ARRAY_BUFFER);
	}
	
	public ByteBuffer map() {
		bind();
		ByteBuffer result = glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE);
		unbind();
		return result;
	}
	
	//Should always mainly be used
	public ByteBuffer remap(ByteBuffer old) {
		bind();
		ByteBuffer result = glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE, _size, old);
		unbind();
		return result;
	}
	
	public void unmap() {
		bind();
		if(!glUnmapBuffer(GL_ARRAY_BUFFER)) {
			throw new RuntimeException("Failed to unmap buffer.");
		}
		unbind();
	}
	
	public void bufferSubData(FloatBuffer floatBuffer, long offset) {
		bind();
		glBufferSubData(GL_ARRAY_BUFFER, offset, floatBuffer);
		unbind();
	}
}
