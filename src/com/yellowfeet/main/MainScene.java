package com.yellowfeet.main;

import com.yellowfeet.core.Scene;
import com.yellowfeet.core.graphics.Sprite;
import com.yellowfeet.core.graphics.texture.TextureLoader;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.util.Counter;
import com.yellowfeet.core.util.image.Image;

public class MainScene extends Scene {

	private Sprite sp;
	private Counter counter;
	
	@Override
	protected void load() {
		try (Image i = new Image("res/awesomeface_noback.png")) {
			TextureLoader.LoadTexture(i, "awesomeface");
		}
		
		try (Image i = new Image("res/default.png")) {
			TextureLoader.LoadTexture(i, "random");
		}
		
		counter = new Counter(-1);
		sp = new Sprite(new Vector2(1, 1), "awesomeface");
		register(sp);
	}

	@Override
	protected void update() {
		render();
	}

}
