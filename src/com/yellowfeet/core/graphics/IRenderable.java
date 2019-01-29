package com.yellowfeet.core.graphics;

import java.nio.ByteBuffer;

import com.yellowfeet.core.math.Transform;

public interface IRenderable {
	public void appendToBuffer(ByteBuffer b, int texId);
	public String getTextureName();
	public Transform getTransform();
}
