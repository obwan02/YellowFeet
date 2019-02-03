package com.yellowfeet.core.graphics;

import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.math.Matrix4f;
import com.yellowfeet.core.math.Transform;
import com.yellowfeet.core.math.Vector2;

public class Camera2D implements ICamera {
	
	public static final float WIDTH = 16;
	public static final float HEIGHT = 9;
	
	private Sprite _backgroundSprite;
	
	public Transform transform;
	
	public Camera2D(Sprite backgroundSprite) {
		transform = new Transform();
		_backgroundSprite = backgroundSprite.clone();
		_backgroundSprite.resize(new Vector2(WIDTH, HEIGHT));
	}
	
	public Camera2D(Color background) {
		transform = new Transform();
		_backgroundSprite = new Sprite(new Vector2(WIDTH, HEIGHT), null, background.clone());
	}
	
	public Camera2D(String texture) {
		transform = new Transform();
		_backgroundSprite = new Sprite(new Vector2(WIDTH, HEIGHT), texture, Color.WHITE);
	}
	
	@Override
	public Matrix4f getProjectionMatrix() {
		return Matrix4f.orthographic(-WIDTH/2, WIDTH/2, HEIGHT/2, -HEIGHT/2, 1000, -1000);
	}
	
	//Make sure Z = 0
	@Override
	public Matrix4f getWorldMatrix() {
		transform.position.z = 0;
		transform.reloadMatrix();
		return transform.getTransformMatrix_temp();
	}

	@Override
	public Sprite getBackground() {
		_backgroundSprite.transform.rotation = -transform.rotation;
		_backgroundSprite.transform.scale = transform.scale;
		_backgroundSprite.transform.position = transform.position;
		_backgroundSprite.transform.position.z = 999;
		return _backgroundSprite;
	}

	@Override
	public void setBackground(Sprite sp) {
		_backgroundSprite = sp.clone();
	}
	
}
