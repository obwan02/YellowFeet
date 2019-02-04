package com.yellowfeet.core;

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
import com.yellowfeet.core.util.async.IDynamicTask;
import com.yellowfeet.extra.annotations.Internal;
import com.yellowfeet.extra.annotations.NotRecommended;
import com.yellowfeet.extra.annotations.User;
import com.yellowfeet.extra.annotations.UserDefined;

public abstract class Scene {

	private SpriteOrderer _spriteOrderer;
	private SpriteRenderer _spriteRenderer;
	private ResourceLoader _resLoader;
	
	protected final ICamera camera;
	
	public Scene() {
		camera = new Camera2D(new Sprite(new Vector2(16, 8), null, Color.BLACK));
	}
	
	//Because context management in OpenGL, the constructor can't be used
	@Internal
	protected final void init() {
		_spriteOrderer = new SpriteOrderer();
		_spriteRenderer = new SpriteRenderer(camera, ShaderGenerator.GenerateDefault());
		_resLoader = new ResourceLoader();
	}
	
	@UserDefined
	protected abstract void load();
	@UserDefined
	protected abstract void update();
	@UserDefined
	protected abstract void finish();
	
	@User
	public final void loadResource(IDynamicTask res) {
		_resLoader.load(res);
	}
	
	@NotRecommended
	public final ResourceLoader getResourceLoader() {
		return _resLoader;
	}
	
	@User
	public final void register(ISprite sprite) {
		_spriteOrderer.register(sprite);
	}
	
	@User
	public final void destroy(ISprite sprite) {
		_spriteOrderer.remove(sprite);
	}
	
	@NotRecommended
	public final SpriteOrderer getOrderer() {
		return _spriteOrderer;
	}
	
	@NotRecommended
	@SuppressWarnings("rawtypes")
	public final IRenderer[] getRenderers() {
		return new IRenderer[] { _spriteRenderer };
	}
	
	@User
	protected final void render() {
		//_spriteOrderer.reorder();
		_spriteRenderer.begin();
		for(ISprite i : _spriteOrderer.get()) {
			_spriteRenderer.submit(i);
		}
		_spriteRenderer.flush();
	}

	@Internal
	public final void clean() {
		finish();
		_spriteRenderer.destroy();
		_spriteOrderer.removeAll();
		_resLoader.finish();
	}

}
