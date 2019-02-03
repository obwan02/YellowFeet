package com.yellowfeet.core;

import org.lwjgl.glfw.GLFW;

import com.yellowfeet.core.graphics.texture.TextureLoader;
import com.yellowfeet.extra.annotations.Internal;
import com.yellowfeet.extra.annotations.User;

public final class GameManager {
	private GameManager() {}	
	
	private static Scene _loadedScene;
	
	
	//Probably going to make an internal update method in Scene to handle resource loading
	@Internal
	protected static void update() {
		_loadedScene.getResourceLoader().complete();
		_loadedScene.update();
	}
	
	@User
	public static void LoadScene(Scene s) {
		
		//Clean up previous scene
		Clean();
		
		s.init();
		s.load(); 
		_loadedScene = s;
	}
	
	@Internal
	static void Clean() {
		if(_loadedScene != null) {
			TextureLoader.ClearAll();
			_loadedScene.clean();
		}
	}
	
	@User
	public static final void stop() {
		GLFW.glfwSetWindowShouldClose(Window.getWindowInstanceId(), true);
	}
}
