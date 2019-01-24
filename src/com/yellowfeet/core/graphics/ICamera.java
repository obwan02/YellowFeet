package com.yellowfeet.core.graphics;

import com.yellowfeet.core.math.Matrix4f;

public interface ICamera {

	public Matrix4f getProjectionMatrix();
	public Matrix4f getWorldMatrix();
	public Sprite   getBackground();
	public void     setBackground(Sprite sp);
	
}
