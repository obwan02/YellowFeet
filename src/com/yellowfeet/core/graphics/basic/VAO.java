package com.yellowfeet.core.graphics.basic;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import com.yellowfeet.core.debug.Debug;

public class VAO {
	
	private final int _id;
	
	public VAO() {
		_id = glGenVertexArrays(); Debug.Assert(_id > -1);
	}
	
	public void bind() {
		glBindVertexArray(_id);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public int getId() {
		return _id;
	}
	
	public void specifyAttribPointer(VBO buffer, VertexAttrib attrib, boolean enable) {
		bind();
		buffer.bind();
		glVertexAttribPointer(attrib.location, attrib.size, attrib.type, attrib.normalised, attrib.stride, attrib.offset);
		if(enable) glEnableVertexAttribArray(attrib.location);
		buffer.unbind();
		unbind();
	}
	
	public void enableAttrib(VertexAttrib va) {
		bind();
		glEnableVertexAttribArray(va.location);
		unbind();
	}
	
	public void disableAttrib(VertexAttrib va) {
		bind();
		glDisableVertexAttribArray(va.location);
		unbind();
	}
	
	public void delete() {
		glDeleteVertexArrays(_id);
	}
}
