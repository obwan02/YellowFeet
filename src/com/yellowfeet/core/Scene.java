package com.yellowfeet.core;

import com.yellowfeet.core.components.Resource;
import com.yellowfeet.core.components.ResourceLoader;
import com.yellowfeet.core.graphics.Camera2D;
import com.yellowfeet.core.graphics.ICamera;
import com.yellowfeet.core.graphics.IRenderer;
import com.yellowfeet.core.graphics.ISprite;
import com.yellowfeet.core.graphics.ShaderGenerator;
import com.yellowfeet.core.graphics.Sprite;
import com.yellowfeet.core.graphics.SpriteOrderer;
import com.yellowfeet.core.graphics.SpriteRenderer;
import com.yellowfeet.core.graphics.basic.Color;
import com.yellowfeet.core.math.Vector2;
import com.yellowfeet.extra.annotations.EngineMethod;
import com.yellowfeet.extra.annotations.NotRecommended;
import com.yellowfeet.extra.annotations.UserMethod;
import com.yellowfeet.extra.annotations.UserDefinedMethod;

public abstract class Scene {

	private SpriteOrderer _spriteOrderer;
	private IRenderer<ISprite> _spriteRenderer;
	private ResourceLoader _resLoader;
	
	protected final ICamera camera;
	
	public Scene() {
		camera = new Camera2D(new Sprite(new Vector2(16, 8), null, Color.BLACK));
	}
	
	//Because context management in OpenGL, the constructor can't be used
	@EngineMethod
	protected final void init() {
		_spriteOrderer = new SpriteOrderer();
		_spriteRenderer = new SpriteRenderer(camera, ShaderGenerator.GenerateDefault());
		_resLoader = new ResourceLoader();
	}
	
	@UserDefinedMethod
	protected abstract void load();
	@UserDefinedMethod
	protected abstract void update();
	@UserDefinedMethod
	protected abstract void finish();
	
	@UserMethod
	public final void loadResource(Resource res) {
		_resLoader.loadResouce(res);
	}
	
	@NotRecommended
	@EngineMethod
	public final ResourceLoader getResourceLoader() {
		return _resLoader;
	}
	
	@UserMethod
	public final void register(ISprite sprite) {
		_spriteOrderer.register(sprite);
	}
	
	@UserMethod
	public final void destroy(Sprite sprite) {
		_spriteOrderer.remove(sprite);
	}
	
	@NotRecommended
	@EngineMethod
	public final SpriteOrderer getOrderer() {
		return _spriteOrderer;
	}
	
	@NotRecommended
	@EngineMethod
	@SuppressWarnings("rawtypes")
	public final IRenderer[] getRenderers() {
		return new IRenderer[] { _spriteRenderer };
	}
	
	@UserMethod
	protected final void render() {
		_spriteOrderer.reorder();
		_spriteRenderer.begin();
		_spriteOrderer.submitAll(_spriteRenderer);
		_spriteRenderer.flush();
	}

	@EngineMethod
	public final void clean() {
		finish();
		_spriteRenderer.destroy();
		_spriteOrderer.removeAll();
		_resLoader.stop();
	}

}
