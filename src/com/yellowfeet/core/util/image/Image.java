package com.yellowfeet.core.util.image;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.yellowfeet.core.components.Resource;

public class Image implements Resource {


	/*
	 * All public methods that have offsets and sizes are not 
	 * the byte offset, but rather the 4 byte offset (the offset for each pixel)
	 * 
	 * This class guarantees that all Images will have 4 channels, and excepts image data
	 * that is put into this class to have 4 channels
	 */
	
	public static final int CHANNELS = 4;

	public final int width;
	public final int height;
	
	private final ByteBuffer _data;
	
	//Loads an image from the ByteBuffer as if it were a file
	public Image(String filename) {		
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {			
			IntBuffer x = stack.mallocInt(1);
			IntBuffer y = stack.mallocInt(1);
			IntBuffer channels_in_file = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(true);
			data = STBImage.stbi_load(filename, x, y, channels_in_file, CHANNELS);
			
			if(data == null) {
				throw new RuntimeException("Failed to load image file: <" + filename + ">");
			}
			
			width = x.get(0);
			height = y.get(0);
			
		}
		
		_data = data;
	}
	
	//Loads an image from the ByteBuffer as if it were a file
	public Image(ByteBuffer memorydata) {		
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {			
			IntBuffer x = stack.mallocInt(1);
			IntBuffer y = stack.mallocInt(1);
			IntBuffer channels_in_file = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(true);
			data = STBImage.stbi_load_from_memory(memorydata, x, y, channels_in_file, CHANNELS);
			
			if(data == null) {
				throw new RuntimeException("Failed to load image from memory at memory address <" + Long.toHexString(MemoryUtil.memAddress(memorydata)) + ">");
			}
			
			width = x.get(0);
			height = y.get(0);
			
		}
		
		_data = data;
	}
	
	/*
	 * @param("allocNew") determines weather the image is going to reference @param("data")
	 * or if it is going to copy. Only used privately in the methods `wrap` and `make`. (wrap wraps the buffer,
	 * whereas make copies the data (make a new image)).
	 */
	private Image(ByteBuffer data, int width, int height, boolean allocNew) {
		if(allocNew) {
			_data = MemoryUtil.memAlloc(data.capacity());
			data.rewind();
			MemoryUtil.memCopy(data, _data);
		} else {
			_data = data;
		}
		
		this.width = width;
		this.height = height;
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
	
	public Image makeSubImage(int x, int y, int width, int height) {
		
		/* Conversions (y does not need to be converted to bytes, because all
		 * y coords are multiplied with the width.
		 */
		y = (this.height - height - y);
		width *= 4; //for byte offset
		x *= 4;
		int thisWidth = this.width * 4;

		ByteBuffer newBuffer = MemoryUtil.memAlloc(width * height);
			
		for(int i = 0; i < height; i++) {
			long getAddress = MemoryUtil.memAddress(_data, x + ((i + y) * thisWidth));
			long putAddress = MemoryUtil.memAddress(newBuffer, i * width);
			
			MemoryUtil.memCopy(getAddress, putAddress, width);
		}
		
		return wrap(newBuffer, width / 4, height);
	}
	
	public static Image make(ByteBuffer data, int width, int height) {
		return new Image(data, width, height, true);
	}
	
	/*
	 * With manual wrapping the image can be freed, however it is better to
	 * discard the Image, and free the buffer itself.
	 */
	public static Image wrap(ByteBuffer data, int width, int height) {
		return new Image(data, width, height, false);
	}
}
