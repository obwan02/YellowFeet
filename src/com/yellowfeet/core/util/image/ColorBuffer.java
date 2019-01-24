package com.yellowfeet.core.util.image;

import java.nio.ByteBuffer;

import com.yellowfeet.core.Debug;
import com.yellowfeet.core.graphics.basic.Color;

public class ColorBuffer {

	private final ByteBuffer bb;
	
	public ColorBuffer(ByteBuffer backingBuffer) {
		Debug.Assert(backingBuffer.capacity() % 4 == 0, "Invalid byte buffer.");
		bb = backingBuffer;
	}
	
	public int size() {
		return bb.capacity() / 4;
	}
	
	/*Modifying a Color returned from this method won't modify underlying buffer
	* Use set instead
	* 
	* The color that is returned is the index multiplied by 4.
	*/
	public Color get(int i) {
		int r = (bb.get(i * 4 + 0) & 0xff); 
		int g = (bb.get(i * 4 + 1) & 0xff);
		int b = (bb.get(i * 4 + 2) & 0xff);
		int a = (bb.get(i * 4 + 3) & 0xff);
		
		return new Color(r, g, b, a);
	}
	
	public void set(int i, Color c) {
		bb.put(i * 4 + 0, (byte)(c.r));
		bb.put(i * 4 + 1, (byte)(c.g));
		bb.put(i * 4 + 2, (byte)(c.b));
		bb.put(i * 4 + 3, (byte)(c.a));
	}
}
