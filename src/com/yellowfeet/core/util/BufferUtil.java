package com.yellowfeet.core.util;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;

import com.yellowfeet.core.debug.Debug;

public final class BufferUtil {
	private BufferUtil() {}
	
	
	
	public static ByteBuffer getSubBuffer(ByteBuffer b, int offset, int length) {
		if(offset + length > b.capacity()) Debug.ThrowException(new RuntimeException("Index out of bounds."));
		long address = MemoryUtil.memAddress(b, offset);
		return MemoryUtil.memByteBuffer(address, length);
	}
}
