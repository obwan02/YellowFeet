package com.yellowfeet.core.graphics.basic;


import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL30.GL_SHORT;
import static org.lwjgl.opengl.GL30.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30.glBufferSubData;

public class IBO extends GLBuffer {
	
	public static final int GL_TYPE = GL_SHORT;
	public static final int BYTE_SIZE = 2;
	
	public IBO(int bytesize, int type) {
		super(bytesize, type, GL_ELEMENT_ARRAY_BUFFER);
	}
	
	public void bufferSubData(ShortBuffer data, int offset) {
		bind();
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset, data);
		unbind();
	}
	
}
