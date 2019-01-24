package com.yellowfeet.core.util.image;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Image implements AutoCloseable {

	
	public static final int CHANNELS = 4;

	public final int width;
	public final int height;
	
	private final ByteBuffer _data;
	
	public Image(String filename) {		
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {			
			IntBuffer x = stack.mallocInt(1);
			IntBuffer y = stack.mallocInt(1);
			IntBuffer channels_in_file = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(true);
			data = STBImage.stbi_load(filename, x, y, channels_in_file, CHANNELS);
			if(data == null) {
				throw new RuntimeException("Failed to load image: \"" + filename + "\"");
			}
			
			width = x.get(0);
			height = y.get(0);
		}
		
		_data = data;
	}
	
	public Image(ByteBuffer initdata) {		
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {			
			IntBuffer x = stack.mallocInt(1);
			IntBuffer y = stack.mallocInt(1);
			IntBuffer channels_in_file = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(true);
			data = STBImage.stbi_load_from_memory(initdata, x, y, channels_in_file, CHANNELS);
			
			if(data == null) {
				throw new RuntimeException("Failed to load image from memory at memory address <" + Long.toHexString(MemoryUtil.memAddress(initdata)) + ">");
			}
			
			width = x.get(0);
			height = y.get(0);
		}
		
		_data = data;
	}
	
	public void free() {
		STBImage.stbi_image_free(_data);
	}
	
	public ByteBuffer getBuffer() {
		return _data;
	}
	
	public ColorBuffer getColorBuffer() {
		return new ColorBuffer(_data);
	}

	@Override
	public void close() {
		free();
	}
}
