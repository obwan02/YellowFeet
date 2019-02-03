package com.yellowfeet.core.graphics.basic;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.ByteBuffer;

import com.yellowfeet.core.debug.Debug;

public abstract class GLBuffer {
	/*
	 * Core class for all buffers - the main classes being
	 * VBO (Vertex Buffer Object) and IBO (Index Buffer Object)
	 * 
	 * This class is for constant size buffers only
	 */
	
	protected final int _size, _type;
	protected final int _id;
	private final int _buffertype;
	
	protected GLBuffer(int bytesize, int type, int buffertype) {
		_buffertype = buffertype;
		_id = glGenBuffers(); Debug.Assert(_id > -1);
		glBindBuffer(_buffertype, _id); //bind
		glBufferData(_buffertype, bytesize, type);
		_size = bytesize;
		_type = type;
		glBindBuffer(_buffertype, 0); //unbind
	}
	
	public void bind() {
		glBindBuffer(_buffertype, _id);
	}
	
	public void unbind() {
		glBindBuffer(_buffertype, 0);
	}
	
	public int getId() {
		return _id;
	}
	
	public int getByteSize() {
		return _size;
	}
	
	public void bufferSubData(ByteBuffer data, int offset) {
		Debug.Assert(offset > 0);
		
		bind();
		glBufferSubData(_buffertype, offset, data);
		unbind();
	}
	
	public void orphan() {
		bind();
		glBufferData(_buffertype, _size, _type);
		unbind();
	}
	
	public void delete() {
		glDeleteBuffers(_id);
	}
}
