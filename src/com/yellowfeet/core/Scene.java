package com.yellowfeet.core;

import com.yellowfeet.core.graphics.Camera2D;
import com.yellowfeet.core.graphics.ICamera;
import com.yellowfeet.core.graphics.IRenderer;
import com.yellowfeet.core.graphics.ShaderGenerator;
import com.yellowfeet.core.graphics.Sprite;
import com.yellowfeet.core.graphics.SpriteOrderer;
import com.yellowfeet.core.graphics.SpriteRenderer;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.math.Vector2;

public abstract class Scene {

	private SpriteOrderer _orderer;
	private IRenderer _renderer;
	
	protected final ICamera camera;
	
	public Scene() {
		camera = new Camera2D(new Sprite(new Vector2(16, 8), null, Color.Get(Color.BLACK)));
	}
	
	public final void init() {
		_orderer = new SpriteOrderer();
		_renderer = new SpriteRenderer(camera, ShaderGenerator.GenerateDefault());
	}
	
	protected abstract void load();
	protected abstract void update();
	
	public final void register(Sprite sprite) {
		_orderer.register(sprite);
	}
	
	public final void destroy(Sprite sprite) {
		_orderer.remove(sprite);
	}
	
	public final SpriteOrderer getOrderer() {
		return _orderer;
	}
	
	public final IRenderer getRenderer() {
		return _renderer;
	}
	
	protected final void render() {
		_orderer.reorder();
		_renderer.begin();
		_orderer.submitAll(_renderer);
		_renderer.flush();
	}

}
