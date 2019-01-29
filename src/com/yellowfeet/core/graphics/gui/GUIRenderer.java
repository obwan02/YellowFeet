package com.yellowfeet.core.graphics.gui;

import com.yellowfeet.core.graphics.ICamera;
import com.yellowfeet.core.graphics.ShaderGenerator;
import com.yellowfeet.core.graphics.SpriteRenderer;

public class GUIRenderer extends SpriteRenderer {

	public GUIRenderer(ICamera camera) {
		super(camera, ShaderGenerator.GenerateShader("GUIShader"));
	}


}
