package com.yellowfeet.main;

import com.yellowfeet.core.Scene;
import com.yellowfeet.core.graphics.AnimatedSprite;
import com.yellowfeet.core.graphics.Sprite;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.graphics.texture.TextureConfig;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.util.Counter;
import com.yellowfeet.core.util.async.DynamicTasks;
import com.yellowfeet.core.util.image.Image;

public class MainScene extends Scene {

	private Sprite sp;
	private AnimatedSprite asp;
	private Counter counter;
	
	@Override
	protected void load() {
		loadResource(DynamicTasks.TextureDynamicTask(() -> new Image("res/background.jpg"), null, "background"));
		loadResource(DynamicTasks.TextureDynamicTask(() -> new Image("res/awesomeface.png"), null, "awesomeface"));
		loadResource(DynamicTasks.TextureDynamicTask(() -> new Image("res/awesomeface_noback.png"), null, "awesomeface_nb"));
		loadResource(DynamicTasks.TextureDynamicTask(() -> new Image("res/random_tile.png"), TextureConfig.PIXEL_CONFIG, "awesomeface"));
		camera.setBackground(new Sprite(new Vector2(16, 9), "background"));
	
		sp = new Sprite(new Vector2(2), "awesomefacenb");
		register(sp);
	}

	@Override
	protected void update() {
		render();
	}
	
	@Override
	protected void finish() {
		
	}

}
