package com.yellowfeet.core.graphics.basic;

public class VertexAttrib {
	
	public final int location, size, type, stride, offset;
	public final boolean normalised;
	public final String name;
	
	public VertexAttrib(String name, int location, int size, int type, boolean normalised, int stride, int offset) {
		this.location = location;
		this.size = size;
		this.type = type;
		this.normalised = normalised;
		this.stride = stride;
		this.offset = offset;
		this.name = name;
	}
	
}
