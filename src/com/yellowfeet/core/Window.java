package com.yellowfeet.core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;

public class Window {
	
	private final int _width, _height;
	private static final int TARGET_FPS = 60;
	private static Window _instance = null;
	
	private long _glfwWindowId;
	private volatile boolean _running; //for safety

	private static float _deltaTime;
	
	private Window(String name, int width, int height, boolean fullscreen, Scene startScene) {
		if(_instance != null) throw new RuntimeException("Only one instance is allowed.");
		_deltaTime = 1 / TARGET_FPS;
		_width = width; _height = height;
		
		if(!glfwInit()) {
			throw new RuntimeException("Failed to initialise GLFW.");
		}

		byte fullscreenInt = (byte) (fullscreen ? 0xFF : 0x00);
		_glfwWindowId = glfwCreateWindow(_width, _height, name, glfwGetPrimaryMonitor() & fullscreenInt, 0);
		glfwSetWindowSize(_glfwWindowId, _width, _height);
		glfwMakeContextCurrent(_glfwWindowId);
		GL.createCapabilities(true);
		GLUtil.setupDebugMessageCallback(System.err);
		
		
		printInfo();
		
		openGLInit();
		_instance = this;
		GameManager.LoadScene(startScene);
		run();
	}
	
	private void openGLInit() {
		GL30.glClearColor(0.5f, 0.1f, 0.2f, 1.0f);
		
		//GL30.glDepthMask(false);
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		GL30.glDepthFunc(GL30.GL_LESS);
		
		GL30.glEnable(GL30.GL_BLEND);
		GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	protected void printInfo() {
		System.out.println(GL30.glGetString(GL30.GL_VERSION));
		System.out.println("Max Tex Units: " + GL30.glGetInteger(GL30.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS));
		System.out.println("Max Frag Tex Units: " + GL30.glGetInteger(GL30.GL_MAX_TEXTURE_IMAGE_UNITS));
		System.out.println("Max Array Texture Layers: " + GL30.glGetInteger(GL30.GL_MAX_ARRAY_TEXTURE_LAYERS));
		System.out.println("Max Texture Size: " + GL30.glGetInteger(GL30.GL_MAX_TEXTURE_SIZE));
		
		System.out.println();
	}
	
	/*
	 * Automatic time management
	 * Vsync update method
	 */
	public void run() {
		if(_running) return;
		_running = true;
		glfwSwapInterval(1); //Set the vsync level to 1
		
		double prevCountTime = glfwGetTime(), 
			   prevUpdateTime = glfwGetTime();
		int frames = 0;
		
		while(!glfwWindowShouldClose(_glfwWindowId)) {
			
			
			//Do update stuff
			{
				frames++;
				
				//Timey wimey wobbley bobbely
				double delta = glfwGetTime() - prevUpdateTime;
				_deltaTime = (float) delta;

				//Game management stuff
				glfwPollEvents();
				GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
				GameManager.update();
				prevUpdateTime = glfwGetTime();
				glfwSwapBuffers(_glfwWindowId); //waits if vsync is on (which is usually is)
				
			}
			
			if(glfwGetTime() - prevCountTime >= 1) {
				System.out.println(frames);
				frames = 0;
				prevCountTime = glfwGetTime();
			}
		}
		_running = false;
		//For safety
		GameManager.Clean();
		
		glfwDestroyWindow(_glfwWindowId);
		glfwTerminate();
	}
	
	public static boolean getKey(int key) {
		return glfwGetKey(_instance._glfwWindowId, key) == GLFW_PRESS;
	}
	
	protected static long getWindowInstanceId() {
		return _instance._glfwWindowId;
	}
	
	//Gets the time between this frame and the last.
	public static float getDeltaTime() {
		return _deltaTime;
	}
	
	public static boolean isRunning() {
		return _instance._running;
	}
	
	//Starts a new Window (which controls everything) 
	public static void Start(String title, int width, int height, Scene startscene, boolean fullscreen) {
		new Window(title, width, height, fullscreen, startscene);
	}
}
