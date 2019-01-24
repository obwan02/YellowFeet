package com.yellowfeet.core.graphics;

import java.nio.ByteBuffer;

public interface IRenderable {
	public void appendToBuffer(ByteBuffer b, int texId);
	public String getTextureName();
}
