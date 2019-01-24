package com.yellowfeet.main;

import com.yellowfeet.core.Window;

public class Main {

	public static void main(String[] args) {
		MainScene mainScene = new MainScene();
		Window.Start("Yeet", 1280, 720, mainScene, false);
	}
}
