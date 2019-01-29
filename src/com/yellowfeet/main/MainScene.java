package com.yellowfeet.main;

import com.yellowfeet.core.Scene;
import com.yellowfeet.core.graphics.AnimatedSprite;
import com.yellowfeet.core.graphics.Sprite;
import com.yellowfeet.core.graphics.texture.Texture;
import com.yellowfeet.core.graphics.texture.TextureLoader;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.core.util.Counter;
import com.yellowfeet.core.util.image.Image;

public class MainScene extends Scene {

	private Sprite sp;
	private AnimatedSprite asp;
	private Counter counter;
	
	@Override
	protected void load() {
		
		try(Image i = new Image("res/background.jpg")) {
			TextureLoader.LoadTexture(new Texture(i, TextureLoader.DEFAULT_CONFIG), "random");
		}
		
		try(Image i = new Image("res/awesomeface.png")) {
			
		}
		
		try(Image i = new Image("res/awesomeface_noback.png")) {
			
		}
		
		
		camera.setBackground(new Sprite(new Vector2(16, 9), "random"));
	}

	@Override
	protected void update() {
		//counter.increase(1);
		//if(counter.get() > 60) asp.advance();
		render();
	}
	
	@Override
	protected void finish() {
		
	}

}
