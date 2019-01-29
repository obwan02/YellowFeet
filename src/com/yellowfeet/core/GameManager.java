package com.yellowfeet.core;

import org.lwjgl.glfw.GLFW;

import com.yellowfeet.core.graphics.texture.TextureLoader;

public final class GameManager {
	private GameManager() {}	
	
	private static Scene _loadedScene;
	
	protected static void update() {
		
		_loadedScene.update();
	}
	
	//Problematic for large scenes
	public static void LoadScene(Scene s) {
		
		//Clean up previous scene
		Clean();
		
		s.init();
		s.load(); 
		_loadedScene = s;
	}
	
	static void Clean() {
		if(_loadedScene != null) {
			TextureLoader.ClearAll();
			_loadedScene.clean();
		}
	}
	
	public static final void stop() {
		GLFW.glfwSetWindowShouldClose(Window.getWindowInstanceId(), true);
	}
}
