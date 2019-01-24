package com.yellowfeet.core;

import org.lwjgl.glfw.GLFW;

import com.yellowfeet.core.graphics.texture.TextureLoader;

public final class GameManager {
	private GameManager() {}	
	
	private static Scene _loadedScene;
	
	protected static void update() {
		_loadedScene.update();
	}
	
	public static void LoadScene(Scene s) {
		
		//Clean up previous scene
		if(_loadedScene != null) {			
			TextureLoader.ClearAll();
			_loadedScene.getRenderer().destroy();
			_loadedScene.getOrderer().removeAll();
		}
		
		s.init();
		s.load();         
		_loadedScene = s;
	}
	
	protected static void Clean() {
		if(_loadedScene != null) {
			TextureLoader.ClearAll();
			_loadedScene.getRenderer().destroy();
			_loadedScene.getOrderer().removeAll();
		}
	}
	
	public static final void stop() {
		GLFW.glfwSetWindowShouldClose(Window.getWindowInstanceId(), true);
	}
}
